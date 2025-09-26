package com.shop.auth.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shop.auth.client.CustomerClient;
import com.shop.auth.controller.dto.AuthCreateUserRequest;
import com.shop.auth.controller.dto.AuthLoginRequest;
import com.shop.auth.controller.dto.AuthResponse;
import com.shop.auth.controller.dto.ClientDTO;
import com.shop.auth.http.response.ClientByUserResponse;
import com.shop.auth.persistance.entity.RefreshTokenEntity;
import com.shop.auth.persistance.entity.RoleEntity;
import com.shop.auth.persistance.entity.UserEntity;
import com.shop.auth.persistance.repository.RefreshTokenRepository;
import com.shop.auth.persistance.repository.RoleRepository;
import com.shop.auth.persistance.repository.UserRepository;
import com.shop.auth.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService, IUserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userEntity.getRoles().stream().flatMap(role -> role.getPermissionList().stream()).forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(), userEntity.isAccountNoExpired(), userEntity.isCredentialNoExpired(), userEntity.isAccountNoLocked(), authorityList);
    }


    public UserEntity getUserEntity(String username){
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("EL usuario " + username + " no existe"));
        return userEntity;
    }

    public AuthResponse createUser(AuthCreateUserRequest createRoleRequest){
        String username = createRoleRequest.username();
        String password = createRoleRequest.password();
        List<String> roleRequest = createRoleRequest.roleRequest().roleListName();

        Set<RoleEntity> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        if(roleEntityList.isEmpty()){
            throw new IllegalArgumentException("The roles specified does not exist. ");
        }
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntityList)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();
        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userSaved.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
                );
        userSaved.getRoles().stream().flatMap(
                role -> role.getPermissionList().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accesToken = jwtUtils.createToken(authentication, userSaved);
        AuthResponse authResponse = new AuthResponse(userSaved.getId() ,username, "User created successfully", accesToken, true);
        return authResponse;

    }

    public ResponseEntity<AuthResponse> loginUser(AuthLoginRequest authLoginRequest, String refreshToken){
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();
        UserEntity user = getUserEntity(username);
        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate access token
        String accessToken = jwtUtils.createToken(authentication, user);

        // Validate refresh token if we have it
        RefreshTokenEntity validTokenEntity = null;
        if(refreshToken != null && !refreshToken.isBlank()){
            Optional<RefreshTokenEntity> maybeToken = refreshTokenRepository.findByToken(refreshToken);
            // If token exists in db
            if(maybeToken.isPresent()){
                RefreshTokenEntity existingToken = maybeToken.get();

                // Define if the token is expired or revoked
                Boolean dbExpired = existingToken.getExpiryDate() != null && existingToken.getExpiryDate().isBefore(Instant.now());
                Boolean revoked = Boolean.TRUE.equals(existingToken.isRevoked());
                if(revoked || dbExpired){
                    // Mark it revoked for security
                    existingToken.setRevoked(true);
                    refreshTokenRepository.save(existingToken);
                }else{
                    try{
                        jwtUtils.validateRefreshToken(refreshToken);
                        // If the token validation is OK
                        validTokenEntity = existingToken;
                    }catch (JWTVerificationException ex){
                        // If is not valid we revoke from DB
                        existingToken.setRevoked(true);
                        refreshTokenRepository.save(existingToken);
                        validTokenEntity = null;
                    }
                }
            }
        }

        // So if the token is not valid then we create one
        String refreshToReturn = null;
        if(validTokenEntity == null){
            String newRefresh = jwtUtils.createRefreshToken(user);
            // Create refresh token with claims
            RefreshTokenEntity newTokenEntity = RefreshTokenEntity.builder()
                    .user(user)
                    .token(newRefresh)
                    .revoked(false)
                    .createdAt(Instant.now())
                    .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                    .build();
            // Save the refresh token in DB
            refreshTokenRepository.save(newTokenEntity);
            // Set the refresh token to return
            refreshToReturn = newRefresh;
        } else{
            // If the token already exists, just return the same token
            refreshToReturn = validTokenEntity.getToken();
        }
        // Put the token into a cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToReturn)
                .httpOnly(true).secure(true).path("/").maxAge(Duration.ofDays(7)).build();
        // And return a response too
        AuthResponse authResponse = new AuthResponse(null, username, "User logged successfully", accessToken, true);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
    }

    public ResponseEntity<AuthResponse> accessTokenWithRefresh(String refreshToken){
        try{
            DecodedJWT token = jwtUtils.validateRefreshToken(refreshToken);
            String userId = token.getSubject();
            Optional<UserEntity> maybeUser = userRepository.findUserById(userId);
            if(maybeUser.isPresent()){
                UserEntity user = maybeUser.get();
                ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach(role ->
                        authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
                );
                user.getRoles().stream().flatMap(
                                role -> role.getPermissionList().stream())
                        .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                String accessToken = jwtUtils.createToken(authentication, user);
                AuthResponse authResponse = new AuthResponse(null, user.getUsername(), "New access Token generated", accessToken, true);
                return ResponseEntity.ok().body(authResponse);
            }else{
                throw new BadCredentialsException("The user does not exist");
            }

        }catch (JWTVerificationException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public ClientByUserResponse findClientByIdUser(String idUser) {
        // Consultar el usuario
        UserEntity user = userRepository.findUserById(idUser).orElseThrow(() -> new RuntimeException("User doesn't exist"));

        // Obtener el cliente
        ClientDTO clientDTO = customerClient.findClientByUser(idUser);
        return ClientByUserResponse.builder()
                .username(user.getUsername())
                .clientDTO(clientDTO)
                .build();
    }
}
