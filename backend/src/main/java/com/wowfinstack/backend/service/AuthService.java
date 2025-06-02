package com.wowfinstack.backend.service;

import com.wowfinstack.backend.dto.*;

public interface AuthService {
    UserRegisterResponse userRegister(UserRegisterRequest request);
    UserLoginResponse userLogin(UserLoginRequest request);
}
