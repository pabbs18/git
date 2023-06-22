package com.springsecurity.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.springsecurity.dto.UserDto;
import com.springsecurity.springsecurity.entity.UserEntity;
import com.springsecurity.springsecurity.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String createUser( @RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
}
