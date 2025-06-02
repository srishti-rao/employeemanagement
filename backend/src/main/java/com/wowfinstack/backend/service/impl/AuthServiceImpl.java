package com.wowfinstack.backend.service.impl;

import com.wowfinstack.backend.config.JwtUtil;
import com.wowfinstack.backend.dto.*;
import com.wowfinstack.backend.repository.EmployeeRepository;
import com.wowfinstack.backend.repository.UserRepository;
import com.wowfinstack.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.wowfinstack.backend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserRegisterResponse userRegister(UserRegisterRequest request) {
        Optional<User> existingUserOpt = userRepository.findByUsername(request.getUsername());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            return new UserRegisterResponse(
                    "User already exists",
                    existingUser.getUserId(),
                    existingUser.getUsername()
            );
        }


        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        return new UserRegisterResponse(
                "User registered successfully",
                savedUser.getUserId(),
                savedUser.getUsername());

    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String accessToken = jwtUtil.generateAccessToken(request.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());
        UserLoginResponse tokens = new UserLoginResponse();
        tokens.setAccessToken(accessToken);
        tokens.setRefreshToken(refreshToken);
        tokens.setUsername(request.getUsername());
        tokens.setMessage("Successfully logged in");
        return tokens;
    }
}
