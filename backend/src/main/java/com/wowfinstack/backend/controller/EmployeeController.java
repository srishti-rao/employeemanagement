package com.wowfinstack.backend.controller;

import com.wowfinstack.backend.dto.EmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterRequest;
import com.wowfinstack.backend.dto.GetEmployeeDto;
import com.wowfinstack.backend.dto.EmployeeRegisterResponse;
import com.wowfinstack.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<GetEmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{emp_id}")
    public GetEmployeeDto getById(@PathVariable int emp_id) {
        return employeeService.findById(emp_id);
    }


    @PostMapping
    public EmployeeRegisterResponse registerEmployee(@RequestBody EmployeeRegisterRequest request) {
        return employeeService.registerEmployee(request);
    }

    @PutMapping("/{emp_id}")
    public GetEmployeeDto updateEmployee(@PathVariable int emp_id, @RequestBody EmployeeDto dto) {
        return employeeService.updateEmployee(emp_id, dto);
    }

    @PatchMapping("/{emp_id}")
    public GetEmployeeDto patchEmployee(@PathVariable int emp_id, @RequestBody EmployeeDto dto) {
        return employeeService.patchEmployee(emp_id, dto);
    }

    @DeleteMapping("/{emp_id}")
    public void deleteEmployee(@PathVariable int emp_id) {
        employeeService.deleteEmployee(emp_id);
    }
}
