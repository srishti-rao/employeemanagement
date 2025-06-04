package com.wowfinstack.backend.service;

import com.wowfinstack.backend.dto.employee.*;

import java.util.List;

public interface EmployeeService {
    List<GetEmployeeDto> getAllEmployees();
    GetEmployeeDto findById(int empId);
    EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request);
    GetEmployeeDto updateEmployee(int empId, EmployeeDto dto);
    GetEmployeeDto patchEmployee(int empId, EmployeeDto dto);
    void deleteEmployee(int empId);
    void resetEmployeePassword(ResetEmployeePasswordRequest request);
}
