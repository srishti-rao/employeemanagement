package com.wowfinstack.backend.service;

import com.wowfinstack.backend.dto.EmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterRequest;
import com.wowfinstack.backend.dto.GetEmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterResponse;

import java.util.List;

public interface EmployeeService {
    List<GetEmployeeDto> getAllEmployees();
    GetEmployeeDto findById(int empId);
    EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request);
    GetEmployeeDto updateEmployee(int empId, EmployeeDto dto);
    GetEmployeeDto patchEmployee(int empId, EmployeeDto dto);
    void deleteEmployee(int empId);
}
