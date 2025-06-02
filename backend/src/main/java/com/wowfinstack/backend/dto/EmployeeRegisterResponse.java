package com.wowfinstack.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterResponse {
    private String message;
    private int userId;
    private String username;
    private int empId;
    private String name;
    private String address;
    private String phone;
    private String position;
}
