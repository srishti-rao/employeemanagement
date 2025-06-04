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
                    dto.setUserId(employee.getUserId());
                    Optional<User> userOpt = userRepository.findById((long) employee.getUserId());
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        dto.setUsername(user.getUsername());
                    }
                    dto.setMessage("Successfully retrieved list of employees");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeRegisterResponse registerEmployee(EmployeeRegisterRequest request) {
        Optional<User> existingUserOpt = userRepository.findByUsername(request.getUsername());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            Optional<Employee> existingEmployeeOpt = employeeRepository.findByUserId(existingUser.getUserId());

            if (existingEmployeeOpt.isPresent()) {
                Employee existingEmployee = existingEmployeeOpt.get();
                return new EmployeeRegisterResponse(
                        "User already exists"
//                        ,0,null,0,null,null,null,null
                );
            } else {
                // User exists but no employee record found; optionally handle this case
                return new EmployeeRegisterResponse(
                        "User exists but employee record not found"
//                        , existingUser.getUserId(),
//                        existingUser.getUsername(),
//                        0, null, null, null, null
                );
            }
        }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);

            Employee employee = new Employee();
            employee.setUserId(user.getUserId());
            employee.setName(request.getName());
            employee.setAddress(request.getAddress());
            employee.setPhone(request.getPhone());
            employee.setPosition(request.getPosition());
            employeeRepository.save(employee);

            GetEmployeeDto response = new GetEmployeeDto();
            response.setEmpId(employee.getEmpId());
            response.setUserId(user.getUserId());
            response.setUsername(user.getUsername());
            response.setName(employee.getName());
            response.setAddress(employee.getAddress());
            response.setPhone(employee.getPhone());
            response.setPosition(employee.getPosition());

            return new EmployeeRegisterResponse(
                    "User registered successfully"
//                    , user.getUserId(),
//                    user.getUsername(),
//                    employee.getEmpId(),
//                    employee.getName(),
//                    employee.getAddress(),
//                    employee.getPhone(),
//                    employee.getPosition()
            );
    }


    @Override
    public GetEmployeeDto updateEmployee(int empId, EmployeeDto dto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (optionalEmployee.isEmpty()) {
            GetEmployeeDto response = new GetEmployeeDto();
            response.setMessage("Employee not found with ID: " + empId);
            return response;
        }
        Employee existing = optionalEmployee.get();
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setPhone(dto.getPhone());
        existing.setPosition(dto.getPosition());
        Employee updated = employeeRepository.save(existing);

        Optional<User> userOpt = userRepository.findById((long)existing.getUserId());
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
        response.setUserId(existing.getUserId());
        response.setUsername(updatedUsername);
        response.setMessage("Employee updated successfully");
        return response;
    }

    @Override
    public GetEmployeeDto patchEmployee(int empId, EmployeeDto dto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (optionalEmployee.isEmpty()) {
            GetEmployeeDto response = new GetEmployeeDto();
            response.setMessage("Employee not found with ID: " + empId);
            return response;
        }
        Employee existing = optionalEmployee.get();
        if(dto.getName()!=null) {
            existing.setName(dto.getName());
        }
        if(dto.getAddress()!=null) {
            existing.setAddress(dto.getAddress());
        }
        if(dto.getPhone()!=null) {
            existing.setPhone(dto.getPhone());
        }
        if(dto.getPosition()!=null) {
            existing.setPosition(dto.getPosition());
        }
        Employee patched = employeeRepository.save(existing);

        Optional<User> userOpt = userRepository.findById((long)existing.getUserId());
        String patchedUsername = null;
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if(dto.getUsername()!=null) {
                user.setUsername(dto.getUsername());
            }
            if(dto.getPassword()!=null) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            userRepository.save(user);
            patchedUsername = user.getUsername();
        }
        GetEmployeeDto response = modelMapper.map(patched, GetEmployeeDto.class);
        response.setUserId(existing.getUserId());
        response.setUsername(patchedUsername);
        response.setMessage("Employee patched successfully");
        return response;
    }


    @Override
    public void deleteEmployee(int empId){
        employeeRepository.deleteById(empId);
    }

    @Override
    public GetEmployeeDto findById(int empId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (optionalEmployee.isEmpty()) {
            GetEmployeeDto response = new GetEmployeeDto();
            response.setMessage("Employee not found with ID: " + empId);
            return response;
        }
        Employee employee = optionalEmployee.get();
        GetEmployeeDto dto = modelMapper.map(employee, GetEmployeeDto.class);
        dto.setUserId(employee.getUserId());
        Optional<User> userOpt = userRepository.findById((long) employee.getUserId());
        userOpt.ifPresent(user -> dto.setUsername(user.getUsername()));

        dto.setMessage("Employee found successfully");
        return dto;
    }
}
