package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    UserService userService;



    @PostMapping("/create")
    public String UserCreation(@RequestBody User user1) {
        return userService.addUser(user1);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getUsers();
    }
}