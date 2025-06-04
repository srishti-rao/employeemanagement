package com.wowfinstack.backend.service;

import com.wowfinstack.backend.dto.auth.*;

public interface AuthService {
    UserRegisterResponse userRegister(UserRegisterRequest request);
    UserLoginResponse userLogin(UserLoginRequest request);
    void resetPassword(ResetPasswordRequest request);
}
