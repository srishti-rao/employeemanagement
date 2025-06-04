package com.wowfinstack.backend.service.impl;

import com.wowfinstack.backend.config.JwtUtil;
import com.wowfinstack.backend.dto.auth.*;
import com.wowfinstack.backend.exception.DuplicateResourceException;
import com.wowfinstack.backend.exception.ResourceNotFoundException;
import com.wowfinstack.backend.repository.UserRepository;
import com.wowfinstack.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.wowfinstack.backend.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Override
    public UserRegisterResponse userRegister(UserRegisterRequest request) {
       if(userRepository.findByUsername(request.getUsername()).isPresent()){
           throw new DuplicateResourceException("Username already exists");
       }

       User user = new User();
       user.setUsername(request.getUsername());
       user.setPassword(passwordEncoder.encode(request.getPassword()));
       userRepository.save(user);

       return new UserRegisterResponse("User registered successfully");
    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest request) {
        authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String accessToken = jwtUtil.generateAccessToken(request.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());
        UserLoginResponse response = new UserLoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUsername(request.getUsername());
        response.setMessage("Successfully logged in");

        return response;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Username not found :" + request.getUsername()));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
