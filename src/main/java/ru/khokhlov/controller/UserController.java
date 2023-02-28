package ru.khokhlov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.khokhlov.entity.User;
import ru.khokhlov.service.UserService;


import java.util.List;

@RestController
@RequestMapping("/exchange/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> findAll(){
        return userService.getAllUsers();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public String addUser(@RequestBody User user){
        String key = userService.createUser(user);
        return  "secret_key: " + key;
    }
}
