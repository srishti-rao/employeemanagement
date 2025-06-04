package com.wowfinstack.backend.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String message;
    private String username;
    private String accessToken;
    private String refreshToken;
}
