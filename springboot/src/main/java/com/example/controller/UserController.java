package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result dbUser=userService.login(user);
        return Result.success(dbUser);
    }


    @GetMapping("selectByUsername")
    public Result selectByUsername(@RequestParam String username){

        return Result.success(userService.selectByUsername(username));
    }




}
