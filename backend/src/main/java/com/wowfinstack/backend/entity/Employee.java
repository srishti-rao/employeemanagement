package com.wowfinstack.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id", nullable=false)
    private int empId;

    @Column(name="user_id", nullable=false)
    private int userId;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="position")
    private String position;

    @Column(name="image", columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "email")
    private String email;

}
