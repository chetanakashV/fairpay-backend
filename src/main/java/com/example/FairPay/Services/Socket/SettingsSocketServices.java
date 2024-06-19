package com.example.FairPay.Services.Socket;

import com.example.FairPay.Models.DB.Activity;
import com.example.FairPay.Models.DB.Settings;
import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.SocketResponse;
import com.example.FairPay.Repo.SettingsRepo;
import com.example.FairPay.Repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsSocketServices {

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ObjectMapper om = new ObjectMapper();

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SettingsRepo settingsRepo;


    public String getPreferences(String userId) throws JsonProcessingException {
        SocketResponse res = new SocketResponse();
        DefaultResponse response = new DefaultResponse();

        try {
            User user = userRepo.findByUserId(userId);
            Settings settings = settingsRepo.findBySid(user.getUserSettingsId());

            response.setMessage("User Preferences Fetched Successfully");
            response.setStatus(200);
            res.setBody(ow.writeValueAsString(settings));

        } catch (JsonProcessingException e) {
            response.setStatus(400);
            response.setMessage(e.toString());
        }

            res.setResponse(response);
            res.setMessageType("userPreferences");

        return ow.writeValueAsString(res);
    }


    public void updatePreferences(String userId, String preferenceBody) throws JsonProcessingException {
//        System.out.println(preferenceBody);
        Settings settings = om.readValue(preferenceBody, Settings.class);
        settingsRepo.save(settings);
    }
}
