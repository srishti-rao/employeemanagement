package com.wowfinstack.backend.controller;

import com.wowfinstack.backend.dto.employee.*;
import com.wowfinstack.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EmployeeRegisterResponse registerEmployee(@ModelAttribute EmployeeRegisterRequest request) {
        return employeeService.registerEmployee(request);
    }

    @PutMapping(value="/{empId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GetEmployeeDto updateEmployee(@PathVariable int empId, @ModelAttribute EmployeeDto dto) {
        return employeeService.updateEmployee(empId, dto);
    }

    @PatchMapping(value="/{empId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GetEmployeeDto patchEmployee(@PathVariable int empId, @ModelAttribute EmployeeDto dto) {
        return employeeService.patchEmployee(empId, dto);
    }

    @DeleteMapping("/{empId}")
    public void deleteEmployee(@PathVariable int empId) {
        employeeService.deleteEmployee(empId);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetEmployeePassword(@RequestBody ResetEmployeePasswordRequest request) {
        employeeService.resetEmployeePassword(request);
        return ResponseEntity.ok("Employee password reset successfully");
    }
}
