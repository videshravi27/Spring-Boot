package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")// Table name in PostgreSQL
public class User {

    @Id
    private Long rollNo;



    private String name;
    private String email;

    public User() {}

    public User(Long rollNo,String name, String email) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
    }

    public Long getRollNo() {
        return rollNo;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
