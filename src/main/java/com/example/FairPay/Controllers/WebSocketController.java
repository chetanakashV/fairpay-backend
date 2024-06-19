package com.example.FairPay.Controllers;

import com.example.FairPay.Models.RequestBodies.Expenses.GetBalancesBody;
import com.example.FairPay.Services.FriendsServices;
import com.example.FairPay.Services.GroupServices;
import com.example.FairPay.Services.Socket.ActivitySocketServices;
import com.example.FairPay.Services.Socket.DashboardSocketServices;
import com.example.FairPay.Services.Socket.GroupSocketServices;
import com.example.FairPay.Services.Socket.SettingsSocketServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {


    @Autowired
    private GroupSocketServices groupSocketServices;

    @Autowired
    private DashboardSocketServices dashboardSocketServices;

    @Autowired
    private ActivitySocketServices activitySocketServices;

    @Autowired
    private GroupServices groupServices;

    @Autowired
    private FriendsServices friendsServices;

    @Autowired
    private SettingsSocketServices settingsSocketServices;

    @MessageMapping("/getDashboard/{userId}")
    @SendTo("/dashboard/{userId}")
    public String getDashboard(@DestinationVariable String userId) throws JsonProcessingException{
        return dashboardSocketServices.getDashboard(userId);
    }

    @MessageMapping("/getActivities/{userId}")
    @SendTo("/activity/{userId}")
    public String getActivities(@DestinationVariable String userId) throws JsonProcessingException{
        return activitySocketServices.getActivities(userId);
    }

    @MessageMapping("/getFriends/{userId}")
    @SendTo("/friends/{userId}")
    public String getFriends(@DestinationVariable String userId) throws JsonProcessingException{
        return friendsServices.getFriends(userId);
    }

    @MessageMapping("/getDetails/{userId}")
    @SendTo("/friends/{userId}")
    public String getDetails(@DestinationVariable String userId, String targetId) throws JsonProcessingException {
        return friendsServices.getDetails(userId, targetId);
    }

    @MessageMapping("/updatePreferences/{userId}")
    public void updateActivities(@DestinationVariable String userId, String preferenceBody) throws JsonProcessingException{
         settingsSocketServices.updatePreferences(userId, preferenceBody);
    }

    @MessageMapping("/getPreferences/{userId}")
    @SendTo("/settings/{userId}")
    public String getPreferences(@DestinationVariable String userId) throws JsonProcessingException{
        return settingsSocketServices.getPreferences(userId);
    }

    @MessageMapping("/getGroups/{userId}")
    @SendTo("/groups/{userId}")
    public String getAllGroups(@DestinationVariable String userId) throws JsonProcessingException {
        return groupSocketServices.getAllGroups(userId);
    }

    @MessageMapping("/getUser/{userId}")
    @SendTo("/groups/{userId}")
    public String getUserFromEmail(String userEmail) throws JsonProcessingException {
        return groupSocketServices.getUserFromEmail(userEmail);
    }


    @MessageMapping("/groupDetails/{userId}")
    @SendTo("/groups/{userId}")
    public String getGroupDetails(String groupId) throws JsonProcessingException {
        return groupSocketServices.getGroupDetails(groupId);
    }

    @MessageMapping("/getGroupUsers/{userId}")
    @SendTo("/groups/{userId}")
    public String getGroupUsers(String groupId) throws JsonProcessingException {
        return groupSocketServices.getGroupUsers(groupId);
    }


    @MessageMapping("/message")
    @SendTo("/groups/public")
    public String Test( String msg){
        System.out.println(msg);
        return msg;
    }

    @MessageMapping("/groups/getGroupBalances/{groupId}")
    @SendTo("/groups/selectedGroup/{groupId}")
    public String getGroupBalances(@DestinationVariable String groupId) throws JsonProcessingException{
        return groupSocketServices.getGroupBalances(new GetBalancesBody(groupId));
    }



}
