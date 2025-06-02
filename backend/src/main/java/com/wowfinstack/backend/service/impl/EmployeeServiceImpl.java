package com.wowfinstack.backend.service.impl;

import com.wowfinstack.backend.dto.*;
import com.wowfinstack.backend.dto.GetEmployeeDto;
import com.wowfinstack.backend.entity.Employee;
import com.wowfinstack.backend.entity.User;
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
                    dto.setUser_id(employee.getUser_id());
                    Optional<User> userOpt = userRepository.findById((long) employee.getUser_id());
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        dto.setUsername(user.getUsername());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new EmployeeRegisterResponse("User already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser_id(user.getUser_id());
        employee.setName(request.getName());
        employee.setAddress(request.getAddress());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());
        employeeRepository.save(employee);

        GetEmployeeDto response = new GetEmployeeDto();
        response.setEmp_id(employee.getEmp_id());
        response.setUser_id(user.getUser_id());
        response.setUsername(user.getUsername());
        response.setName(employee.getName());
        response.setAddress(employee.getAddress());
        response.setPhone(employee.getPhone());
        response.setPosition(employee.getPosition());

        return new EmployeeRegisterResponse("User registered successfully", response);
    }

    @Override
    public GetEmployeeDto updateEmployee(int emp_id, EmployeeDto dto) {
        Employee existing = employeeRepository.findById(emp_id).orElseThrow(() ->
                new RuntimeException("Employee not found with ID: " + emp_id));
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setPhone(dto.getPhone());
        existing.setPosition(dto.getPosition());
        Employee updated = employeeRepository.save(existing);

        Optional<User> userOpt = userRepository.findById((long)existing.getUser_id());
        String updatedUsername = null;
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if(dto.getUsername()!=null) {
                user.setUsername(dto.getUsername());
            }
            if(dto.getPassword()!=null) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            userRepository.save(user);
            updatedUsername = user.getUsername();
        }
        GetEmployeeDto response = modelMapper.map(updated, GetEmployeeDto.class);
        response.setUser_id(existing.getUser_id());
        response.setUsername(updatedUsername);
        return response;
    }

    @Override
    public GetEmployeeDto patchEmployee(int emp_id, EmployeeDto dto) {
        Optional<Employee> optional = employeeRepository.findById(emp_id);
        if (optional.isPresent()) {
            Employee entity = optional.get();
            if (dto.getName() != null) {
                entity.setName(dto.getName());
            }
            if (dto.getAddress() != null) {
                entity.setAddress(dto.getAddress());
            }
            if (dto.getPhone() != null) {
                entity.setPhone(dto.getPhone());
            }
            if (dto.getPosition() != null) {
                entity.setPosition(dto.getPosition());
            }
            Employee updated = employeeRepository.save(entity);

            Optional<User> userOpt = userRepository.findById((long) entity.getUser_id());
            String patchedUsername = null;
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (dto.getUsername() != null) {
                    user.setUsername(dto.getUsername());
                }
                if (dto.getPassword() != null) {
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                }
                userRepository.save(user);
                patchedUsername = user.getUsername();
            }
            GetEmployeeDto response = modelMapper.map(updated, GetEmployeeDto.class);
            response.setUser_id(entity.getUser_id());
            response.setUsername(patchedUsername);
            return response;
        }else {
            throw new RuntimeException("Employee not found with ID: " + emp_id);
        }
    }


    @Override
    public void deleteEmployee(int emp_id){
        employeeRepository.deleteById(emp_id);
    }

    @Override
    public GetEmployeeDto findById(int emp_id) {
        Optional<Employee> entity = employeeRepository.findById(emp_id);
        if (entity.isEmpty()) {
            System.out.println("Employee not found with ID: " + emp_id);
            return null;
        }

        GetEmployeeDto dto = modelMapper.map(entity, GetEmployeeDto.class);
        dto.setUser_id(entity.get().getUser_id());
        Optional<User> userOpt = userRepository.findById((long) entity.get().getUser_id());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            dto.setUsername(user.getUsername());
        }
        return dto;
    }
}
