package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
//    private List<User> users = new ArrayList<>();
    @Autowired
    private UserRepository users;

    public String addUser(User user) {
        if (!isValidEmail(user.getEmail())) {
            return "Invalid email format: " + user.getEmail();
        }
        users.save(user);
        return "User added successfully: " + user.getName();
    }

    public List<User> getUsers() {
        return users.findAll();
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".");
    }
}
