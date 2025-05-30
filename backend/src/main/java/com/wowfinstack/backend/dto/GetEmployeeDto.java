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
    private int user_id;
    private String username;
    private int emp_id;
    private String name;
    private String address;
    private String phone;
    private String position;
}
