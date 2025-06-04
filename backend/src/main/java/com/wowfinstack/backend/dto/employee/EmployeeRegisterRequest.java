package com.wowfinstack.backend.dto.employee;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterRequest {
    private String username;
//    private String password;
    private String name;
    private String address;
    private String phone;
    private String position;
}
