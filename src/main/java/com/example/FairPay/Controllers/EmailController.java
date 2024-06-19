package com.example.FairPay.Controllers;

import com.example.FairPay.Models.RequestBodies.Email.OTPBody;
import com.example.FairPay.Models.RequestBodies.Email.RemainderBody;
import com.example.FairPay.Models.RequestBodies.Email.VerifyBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("email")
public class EmailController {
    @Autowired
    private EmailServices emailService;

    @GetMapping(value = "/test")
    public String test(){return "Email Controller working";}

    @PostMapping(value = "/generateOTP")
    public DefaultResponse generateOTP(@RequestBody OTPBody body){
        return emailService.generateOtp(body);
    }

    @PostMapping(value = "/verifyOTP")
    public DefaultResponse verifyOTP(@RequestBody VerifyBody body){
        return emailService.verifyOtp(body);
    }

    @PostMapping(value = "/resendOTP")
    public DefaultResponse resendOTP(@RequestBody VerifyBody body) {
        return emailService.resendOTP(body);
    }

    @PostMapping(value = "/sendRemainder")
    public DefaultResponse sendRemainder(@RequestBody RemainderBody body){
        return emailService.sendRemainder(body);
    }

}
