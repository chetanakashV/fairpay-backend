package com.example.FairPay.Services.Socket;

import com.example.FairPay.Models.DB.Activity;
import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.DB.UserDetails;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.SocketResponse;
import com.example.FairPay.Repo.ActivityRepo;
import com.example.FairPay.Repo.UserDetailsRepo;
import com.example.FairPay.Repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivitySocketServices {

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ObjectMapper om = new ObjectMapper();

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    public String getActivities(String userId) throws JsonProcessingException {
        SocketResponse response = new SocketResponse();
        DefaultResponse res = new DefaultResponse();

        try {
            User user = userRepo.findByUserId(userId);
            PageRequest pageable = PageRequest.of(0, 15, Sort.by("date").descending());
            response.setBody(ow.writeValueAsString(activityRepo.findRecentByUserId(userId,pageable)));

            res.setMessage("User Activities fetched Successfully!");
            res.setStatus(200);
        } catch (Exception e) {
            res.setMessage(e.toString());
            res.setStatus(400);
        }

        response.setMessageType("getActivities");
        response.setResponse(res);

        return ow.writeValueAsString(response);
    }

}
