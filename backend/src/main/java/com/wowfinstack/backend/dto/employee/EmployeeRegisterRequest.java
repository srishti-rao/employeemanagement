package com.wowfinstack.backend.dto.employee;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterRequest {
    private String username;
    private String password;
    private String name;
    private String address;
    private String phone;
    private String position;
    private MultipartFile image;
    private String email;
}
