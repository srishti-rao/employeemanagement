package com.wowfinstack.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeDto {
    private String message;
    private int userId;
    private String username;
    private int empId;
    private String name;
    private String address;
    private String phone;
    private String position;
}
