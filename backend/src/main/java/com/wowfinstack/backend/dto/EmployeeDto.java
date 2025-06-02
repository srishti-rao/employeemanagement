package com.wowfinstack.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private int userId;
    private String username;
    private String password;
    private int empId;
    private String name;
    private String address;
    private String phone;
    private String position;
}
