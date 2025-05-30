package com.wowfinstack.backend.controller;

import com.wowfinstack.backend.dto.*;
import com.wowfinstack.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public RegisterResponse userRegister(@RequestBody UserRegisterRequest request) {
        return authService.userRegister(request);
    }

    @PostMapping("/login")
    public UserLoginResponse userLogin(@RequestBody UserLoginRequest request) {
        return authService.userLogin(request);
    }

    // try logout
}
