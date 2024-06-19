package com.example.FairPay.Controllers;

import com.example.FairPay.Models.RequestBodies.Users.LoginBody;
import com.example.FairPay.Models.RequestBodies.Users.PasswordResetBody;
import com.example.FairPay.Models.RequestBodies.Users.UpdateUserBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.DB.User;

import com.example.FairPay.Models.ResponseBodies.LoginResponse;
import com.example.FairPay.Services.EmailServices;
import com.example.FairPay.Services.UserServices;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;



@Configuration

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserServices userService;
    @Autowired
    private EmailServices emailService;

    @GetMapping(value = "/test")
    public String doTest(){
        return "User Controller is working";
    }

//    For Registering the User
    @PostMapping(value = "/register")
    public DefaultResponse registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

//    For Logging the User in
    @PostMapping(value = "/login")
    public LoginResponse loginUser(@RequestBody LoginBody body){ return userService.loginUser(body); }

    @PostMapping(value = "/loginOtp")
    public LoginResponse loginOtp(@RequestBody LoginBody body){ return userService.loginOtp(body); }

//    For Reset of Password

    @PutMapping(value = "/resetPassword")
    public DefaultResponse resetPassword(@RequestBody PasswordResetBody body){
        return userService.resetPassword(body);
    }

    @PostMapping(value = "/updateUser")
    public DefaultResponse updateUser(@RequestBody UpdateUserBody body){
        return userService.updateUser(body);
    }


}
