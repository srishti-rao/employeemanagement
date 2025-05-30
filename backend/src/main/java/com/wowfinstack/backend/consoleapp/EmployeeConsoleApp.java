//package com.wowfinstack.backend.consoleapp;
//
//import com.wowfinstack.backend.consoleapp.service.EmployeeService;
//import com.wowfinstack.backend.consoleapp.dto.EmployeeDto;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Scanner;
//
//@Getter
//@Setter
//@Component
//public class EmployeeConsoleApp implements CommandLineRunner {
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("\n---Employee Management Console---");
//            System.out.println("1. Get All Employee Details");
//            System.out.println("2. Get Employee ID");
//            System.out.println("3. Add Employee");
//            System.out.println("4. Update Employee");
//            System.out.println("5. Update partially");
//            System.out.println("6. Delete Employee");
//            System.out.println("7. Exit");
//            System.out.println("Enter your choice: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//            switch (choice) {
//                case 1 -> {
//                    List<EmployeeDto> employees = employeeService.getAllEmployees();
//                    for (EmployeeDto employee : employees) {
//                        System.out.println(employee);
//                    }
//                }
//                case 2-> {
//
//                }
//                case 2 -> {
//                    EmployeeDto dto = new EmployeeDto();
//                    System.out.println("Enter Name: ");
//                    dto.setName(scanner.nextLine());
//                    System.out.println("Enter Address: ");
//                    dto.setAddress(scanner.nextLine());
//                    System.out.println("Enter Phone: ");
//                    dto.setPhone(scanner.nextLine());
//                    System.out.println("Enter Position: ");
//                    dto.setPosition(scanner.nextLine());
//                    employeeService.addEmployee(dto);
//                }
//                case 3 -> {
//                    System.out.println("Enter ID to update: ");
//                    int id = scanner.nextInt();
//                    scanner.nextLine();
//                    try{
//                        EmployeeDto existingEmployee = employeeService.findById(id);
//                        EmployeeDto employeeDto = new EmployeeDto();
//                        System.out.println("Enter New Name (Existing Name: "+ existingEmployee.getName() +") : ");
//                        employeeDto.setName(scanner.nextLine());
//                        System.out.println("Enter New Address (Existing Address: "+existingEmployee.getAddress() +") : ");
//                        employeeDto.setAddress(scanner.nextLine());
//                        System.out.println("Enter New Phone (Existing Phone: "+existingEmployee.getPhone() +") : ");
//                        employeeDto.setPhone(scanner.nextLine());
//                        System.out.println("Enter New Position(Existing Position: "+existingEmployee.getPosition() +") : ");
//                        employeeDto.setPosition(scanner.nextLine());
//                        employeeService.updateEmployee(id, employeeDto);
//                    } catch (RuntimeException ex) {
//                        System.out.println("ID:"+id+" does not exist");
//                    }
//
//                }
//                case 4 -> {
//                    System.out.println("Enter ID to delete: ");
//                    int id = scanner.nextInt();
//                    try{
//                        employeeService.findById(id);
//                        employeeService.deleteEmployee(id);
//                    } catch (RuntimeException ex) {
//                        System.out.println("ID:"+id+" does not exist");
//                    }
//                }
//                case 5 -> {
//                    System.out.println("Exiting...");
//                    return;
//                    //break;
//                    //System.exit(0);
//                }
//                default -> System.out.println("Invalid choice");
//            }
//        }
//    }
//}
