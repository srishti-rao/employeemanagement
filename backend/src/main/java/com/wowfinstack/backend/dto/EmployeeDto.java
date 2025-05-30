package com.wowfinstack.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private int user_id;
    private String username;
    private String password;
    private int emp_id;
    private String name;
    private String address;
    private String phone;
    private String position;
}
