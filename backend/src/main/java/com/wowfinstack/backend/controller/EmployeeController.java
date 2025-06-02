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

    @GetMapping("/{empId}")
    public GetEmployeeDto getById(@PathVariable int empId) {
        return employeeService.findById(empId);
    }


    @PostMapping
    public EmployeeRegisterResponse registerEmployee(@RequestBody EmployeeRegisterRequest request) {
        return employeeService.registerEmployee(request);
    }

    @PutMapping("/{empId}")
    public GetEmployeeDto updateEmployee(@PathVariable int empId, @RequestBody EmployeeDto dto) {
        return employeeService.updateEmployee(empId, dto);
    }

    @PatchMapping("/{empId}")
    public GetEmployeeDto patchEmployee(@PathVariable int empId, @RequestBody EmployeeDto dto) {
        return employeeService.patchEmployee(empId, dto);
    }

    @DeleteMapping("/{empId}")
    public void deleteEmployee(@PathVariable int empId) {
        employeeService.deleteEmployee(empId);
    }
}
