package com.example.FairPay.Services;

import com.example.FairPay.Models.RequestBodies.Email.OTPBody;
import com.example.FairPay.Models.RequestBodies.Email.RemainderBody;
import com.example.FairPay.Models.RequestBodies.Email.VerifyBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailServices {

    Map<String, Integer> otps = new HashMap<String, Integer>();


    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;


    public DefaultResponse generateOtp(OTPBody body){
        DefaultResponse response = new DefaultResponse();
        try{
            Random rand = new Random();
            int otp = rand.nextInt(100000, 999999) ;

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(body.getEmail());
            mailMessage.setText("Your OTP is " + otp);
            mailMessage.setSubject("OTP verification");

            javaMailSender.send(mailMessage);

            otps.put(body.getEmail(), otp);

            response.setStatus(200);
            response.setMessage("OTP is sent successfully!");

        }
        catch(Exception e){
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;

    }

    public DefaultResponse verifyOtp(VerifyBody body){
        DefaultResponse response = new DefaultResponse();
        if(!otps.containsKey(body.getEmail())) {
            response.setStatus(400);
            response.setMessage("Invalid Email!");
        }

        Integer correctOtp = otps.get(body.getEmail());
        if(correctOtp.intValue() == body.getOtp().intValue()) {
            response.setMessage("Verification has been successful");
            response.setStatus(200);
            otps.remove(body.getEmail());
        }
        else {
        response.setMessage("Invalid OTP!!");
        response.setStatus(400);
        }

        return response;

    }

    public DefaultResponse resendOTP(VerifyBody body){
        DefaultResponse response = new DefaultResponse();
        try {
            Integer otp = otps.get(body.getEmail());

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(body.getEmail());
            mailMessage.setText("Your OTP is " + otp);
            mailMessage.setSubject("OTP verification");

            javaMailSender.send(mailMessage);
            response.setMessage("Otp has been sent successfully!");
            response.setStatus(200);
        } catch (MailException e) {
            response.setMessage(e.toString());
            response.setStatus(400);
        }

        return response;
    }

    public DefaultResponse sendRemainder(RemainderBody body) {
        DefaultResponse response = new DefaultResponse();

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(body.getReceiverEmail());
            mailMessage.setText("Hey " +body.getReceiverName()+ ",\n" +
                    "\n" +
                    "This is just a note to settle up on FairPay as soon as you get the chance. You can visit our page for details on who owes what:\n" +
                    "\n" +
                    "Thanks!\n" +
                    body.getSenderName() +
                    "  ");
            mailMessage.setSubject("Settling Group " + body.getGroupName());
            javaMailSender.send(mailMessage);

            response.setStatus(200);
            response.setMessage("Remainder Sent Successfully!");
        } catch (MailException e) {
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;
    }

    public void sendMail(String Email, String Text, String Subject){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(Email);
            mailMessage.setText(Text);
            mailMessage.setSubject(Subject);
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

}
