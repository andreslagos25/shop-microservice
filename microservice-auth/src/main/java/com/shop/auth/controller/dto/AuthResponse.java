package com.shop.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;

@JsonPropertyOrder({"idUser","username", "message", "status", "jwt"})
public record AuthResponse(
        String idUser,
        String username,
        String message,
        String jwt,
        Boolean status ){

}
