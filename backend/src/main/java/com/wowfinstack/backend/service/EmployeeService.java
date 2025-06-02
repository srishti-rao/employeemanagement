package com.wowfinstack.backend.service;

import com.wowfinstack.backend.dto.EmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterRequest;
import com.wowfinstack.backend.dto.GetEmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterResponse;

import java.util.List;

public interface EmployeeService {
    List<GetEmployeeDto> getAllEmployees();
    GetEmployeeDto findById(int emp_id);
    EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request);
    GetEmployeeDto updateEmployee(int emp_id, EmployeeDto dto);
    GetEmployeeDto patchEmployee(int emp_id, EmployeeDto dto);
    void deleteEmployee(int emp_id);
}
