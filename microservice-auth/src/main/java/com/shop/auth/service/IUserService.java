package com.shop.auth.service;

import com.shop.auth.http.response.ClientByUserResponse;

public interface IUserService {
    ClientByUserResponse findClientByIdUser(String idUser);
}
