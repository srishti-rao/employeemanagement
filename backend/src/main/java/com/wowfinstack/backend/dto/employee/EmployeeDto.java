package com.wowfinstack.backend.dto.employee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private int userId;
    private String username;
    private int empId;
    private String name;
    private String address;
    private String phone;
    private String position;
}
