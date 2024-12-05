package com.shop.auth.service;

import com.shop.auth.client.CustomerClient;
import com.shop.auth.controller.dto.AuthCreateUserRequest;
import com.shop.auth.controller.dto.AuthLoginRequest;
import com.shop.auth.controller.dto.AuthResponse;
import com.shop.auth.controller.dto.ClientDTO;
import com.shop.auth.http.response.ClientByUserResponse;
import com.shop.auth.persistance.entity.RoleEntity;
import com.shop.auth.persistance.entity.UserEntity;
import com.shop.auth.persistance.repository.RoleRepository;
import com.shop.auth.persistance.repository.UserRepository;
import com.shop.auth.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userEntity.getRoles().stream().flatMap(role -> role.getPermissionList().stream()).forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(), userEntity.isAccountNoExpired(), userEntity.isCredentialNoExpired(), userEntity.isAccountNoLocked(), authorityList);
    }

    public String getIdUser(String username){
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("EL usuario " + username + " no existe"));
        return userEntity.getId();
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

        String accesToken = jwtUtils.createToken(authentication, userSaved.getId());
        AuthResponse authResponse = new AuthResponse(userSaved.getId() ,username, "User created successfully", accesToken, true);
        return authResponse;

    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();
        String idUser = getIdUser(username);
        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication, idUser);
        AuthResponse authResponse = new AuthResponse(null, username, "User logged successfully", accessToken, true);
        return authResponse;
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
