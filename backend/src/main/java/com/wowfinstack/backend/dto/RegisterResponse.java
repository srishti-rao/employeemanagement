package com.wowfinstack.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String message;
    private GetEmployeeDto employee;

    public RegisterResponse(String message) {
        this.message = message;
    }

    public RegisterResponse(String message, GetEmployeeDto employee) {
        this.message = message;
        this.employee = employee;
    }
}
