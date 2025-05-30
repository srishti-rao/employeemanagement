package com.wowfinstack.backend.service;

import com.wowfinstack.backend.dto.EmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterRequest;
import com.wowfinstack.backend.dto.GetEmployeeDto;
import com.wowfinstack.backend.dto.RegisterResponse;

import java.util.List;

public interface EmployeeService {
    List<GetEmployeeDto> getAllEmployees();
    GetEmployeeDto findById(int emp_id);
    RegisterResponse registerEmployee(EmployeeRegisterRequest request);
    EmployeeDto updateEmployee(int emp_id, EmployeeDto dto);
    EmployeeDto patchEmployee(int emp_id, EmployeeDto dto);
    void deleteEmployee(int emp_id);
}
