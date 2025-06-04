package com.wowfinstack.backend.service.impl;

import com.wowfinstack.backend.dto.employee.*;
import com.wowfinstack.backend.entity.Employee;
import com.wowfinstack.backend.entity.User;
import com.wowfinstack.backend.exception.DuplicateResourceException;
import com.wowfinstack.backend.exception.ResourceNotFoundException;
import com.wowfinstack.backend.repository.EmployeeRepository;
import com.wowfinstack.backend.repository.UserRepository;
import com.wowfinstack.backend.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GetEmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> {
                    GetEmployeeDto dto = modelMapper.map(employee, GetEmployeeDto.class);
                    dto.setUserId(employee.getUserId());
                    userRepository.findById((long) employee.getUserId())
                            .ifPresent(user -> dto.setUsername(user.getUsername()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            if(employeeRepository.findByUserId(existingUser.get().getUserId()).isPresent()) {
                throw new DuplicateResourceException("User already exists");
            }
            throw new ResourceNotFoundException("User exists but no employee record found");
        }

        User user = new User();
        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Employee employee = new Employee();
        employee.setUserId(user.getUserId());
        employee.setName(request.getName());
        employee.setAddress(request.getAddress());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());
        employeeRepository.save(employee);
        return new EmployeeRegisterResponse("Employee registered successfully");
    }


    @Override
    public GetEmployeeDto updateEmployee(int empId, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id " + empId));
        employee.setName(dto.getName());
        employee.setAddress(dto.getAddress());
        employee.setPhone(dto.getPhone());
        employee.setPosition(dto.getPosition());
        employeeRepository.save(employee);

        User user = userRepository.findById((long) employee.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id " + employee.getUserId()));
        if(dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        userRepository.save(user);

        GetEmployeeDto response = modelMapper.map(employee, GetEmployeeDto.class);
        response.setUserId(employee.getUserId());
        response.setUsername(user.getUsername());
        return response;
    }

    @Override
    public GetEmployeeDto patchEmployee(int empId, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));

        if (dto.getName() != null) employee.setName(dto.getName());
        if (dto.getAddress() != null) employee.setAddress(dto.getAddress());
        if (dto.getPhone() != null) employee.setPhone(dto.getPhone());
        if (dto.getPosition() != null) employee.setPosition(dto.getPosition());
        employeeRepository.save(employee);

        User user = userRepository.findById((long) employee.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + employee.getUserId()));

        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        userRepository.save(user);

        GetEmployeeDto response = modelMapper.map(employee, GetEmployeeDto.class);
        response.setUserId(employee.getUserId());
        response.setUsername(user.getUsername());
        return response;
    }


    @Override
    public void deleteEmployee(int empId){
        if (!employeeRepository.existsById(empId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + empId);
        }
        employeeRepository.deleteById(empId);
    }

    @Override
    public GetEmployeeDto findById(int empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));
        GetEmployeeDto dto = modelMapper.map(employee, GetEmployeeDto.class);
        dto.setUserId(employee.getUserId());
        userRepository.findById((long) employee.getUserId())
                .ifPresent(user -> dto.setUsername(user.getUsername()));
        return dto;
    }

    @Override
    public void resetEmployeePassword(ResetEmployeePasswordRequest request){
        Employee employee = employeeRepository.findById(request.getEmpId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + request.getEmpId()));
        User user = userRepository.findById((long) employee.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for employee"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
