package com.example.FairPay.Services;

import com.example.FairPay.Models.DB.*;
import com.example.FairPay.Models.RequestBodies.Friends.FriendRequest;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.FriendsResponse;
import com.example.FairPay.Models.ResponseBodies.SocketResponse;
import com.example.FairPay.Models.Types.EveryGroupUsers;
import com.example.FairPay.Models.Types.GroupShare;
import com.example.FairPay.Models.Types.GroupUser;
import com.example.FairPay.Models.Types.Suggestion;
import com.example.FairPay.Repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.*;

@Service
public class FriendsServices {

    @Autowired
    private FriendsRepo friendsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailServices emailServices;
    @Autowired
    private SettingsRepo settingsRepo;
    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private ActivityRepo activityRepo;

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ObjectMapper om = new ObjectMapper();

    public void NewUser(String userId){
        List<User> list = userRepo.findAll();
        List<Suggestion> suggestions = new ArrayList<Suggestion>();

            for(User user: list){
                if(user.get_id().equals(userId)) continue;
                Friends friends = friendsRepo.findByFid(user.getUserFriendsId());
                List<Suggestion> temp = friends.getUserSuggestions();
                if(temp.size()>30) continue;
                temp.add(new Suggestion(userId, false));
                friends.setUserSuggestions(temp);
                friendsRepo.save(friends);
                suggestions.add(new Suggestion(user.get_id(), false));
            }

        User newUser = userRepo.findByUserId(userId);
        Friends newUserFriends = friendsRepo.findByFid(newUser.getUserFriendsId());
        newUserFriends.setUserSuggestions(suggestions);
        friendsRepo.save(newUserFriends);
    }

    public void SendRequest(String senderId, String receiverId){
        User sender = userRepo.findByUserId(senderId); User receiver = userRepo.findByUserId(receiverId);
        Friends senderFriends = friendsRepo.findByFid(sender.getUserFriendsId());
        Friends receiverFriends = friendsRepo.findByFid(receiver.getUserFriendsId());

        //Changing receiver's status in sender's suggestions
        List<Suggestion> list = senderFriends.getUserSuggestions();
        for(Suggestion item: list){
            if(item.getUserId().equals(receiverId)){
                item.setSent(true);
            }
        }
        senderFriends.setUserSuggestions(list);

        //Removing sender from receiver's suggestions
        List<Suggestion> list1 = receiverFriends.getUserSuggestions();
        List<Suggestion> filter_list1 = list1.stream().filter(suggestion -> !suggestion.getUserId().equals(senderId)).toList();
        receiverFriends.setUserSuggestions(filter_list1);

        //Adding sender to receiver's requests
        List<String> list2 = receiverFriends.getUserRequests();
        list2.add(senderId);
        receiverFriends.setUserRequests(list2);

        //saving Friends
        friendsRepo.save(senderFriends); friendsRepo.save(receiverFriends);

        //Adding Activity to receiver
        Activity activity = new Activity();
        activity.setUserId(receiverId);
        activity.setDate(new Date());
        activity.setName("You have received a friend request from " + sender.getUserName());
        activity.setType("newRequest");

        activityRepo.save(activity);

        //Sending Email to receiver
        Settings settings = settingsRepo.findBySid(receiver.getUserSettingsId());
        if(settings.isSendRequest()){
            emailServices.sendMail(receiver.getUserEmail(), "Hey there,\n" +
                    "\n" +
                    sender.getUserName() + " just sent you a friend request on FairPay!\n" +
                    "\n" +
                    "You can:\n" +
                    "\n" +
                    "Accept: This will allow you to easily split expenses and see shared past expenses with "+ sender.getUserName() + ". ([Link to Accept])\n" +
                    "Ignore: You won't be connected on FairPay.\n" +
                    "See you there!\n" +
                    "\n" +
                    "The FairPay Team", "New Friend Request on FairPay!");
        }
    }

    public void AcceptRequest(String senderId, String receiverId){
        User sender = userRepo.findByUserId(senderId); User receiver = userRepo.findByUserId(receiverId);
        Friends senderFriends = friendsRepo.findByFid(sender.getUserFriendsId());
        Friends receiverFriends = friendsRepo.findByFid(receiver.getUserFriendsId());

        // Remove receiver from sender's suggestions
        List<Suggestion> list = senderFriends.getUserSuggestions();
        List<Suggestion> filter_list = list.stream().filter(suggestion -> !suggestion.getUserId().equals(receiverId)).toList();
        senderFriends.setUserSuggestions(filter_list);

        // Add receiver to sender's friends
        List<String> list2 = senderFriends.getUserFriends();
        list2.add(receiverId);
        senderFriends.setUserFriends(list2);

        // Remove sender from receiver's Requests
        List<String> list3 = receiverFriends.getUserRequests();
        list3.remove(senderId);
        receiverFriends.setUserRequests(list3);

        // Add sender to receiver's friends
        List<String> list4 = receiverFriends.getUserFriends();
        list4.add(senderId);
        receiverFriends.setUserFriends(list4);

        // save friends
        friendsRepo.save(senderFriends); friendsRepo.save(receiverFriends);

        // Add Activity to sender
        Activity activity = new Activity();
        activity.setName(receiver.getUserName() + " has accepted your friend request");
        activity.setDate(new Date());
        activity.setType("acceptRequest");
        activity.setUserId(senderId);

        activityRepo.save(activity);

        // Send a mail to sender
        Settings settings = settingsRepo.findBySid(sender.getUserSettingsId());
        if(settings.isAcceptRequest()){
            emailServices.sendMail(sender.getUserEmail(), "Hey there,\n" +
                    "\n" +
                    "Great news! "+ sender.getUserName() +" has accepted your friend request on FairPay.\n" +
                    "\n" +
                    "Now you can:\n" +
                    "\n" +
                    "Easily split future expenses with " + sender.getUserName() + ".\n" +
                    "See shared past expenses you both added (if any).\n" +
                    "Keep track of who owes who – no more awkward money conversations!\n" +
                    "Get started splitting with " + sender.getUserName() + " on FairPay \n" +
                    "\n" +
                    "The FairPay Team", " It's official! You're now friends with " +  sender.getUserName() +" on FairPay");
        }

    }

    public void RejectRequest(String senderId, String receiverId){
      User sender = userRepo.findByUserId(senderId), receiver = userRepo.findByUserId(receiverId);
      Friends senderFriends = friendsRepo.findByFid(sender.getUserFriendsId()); Friends receiverFriends = friendsRepo.findByFid(receiver.getUserFriendsId());

      // reset the receiver in sender's suggestions
        List<Suggestion> list = senderFriends.getUserSuggestions();
        for(Suggestion item: list){
            if(item.getUserId().equals(receiverId)){
                item.setSent(false);
            }
        }
        senderFriends.setUserSuggestions(list);

      // remove the sender in receiver's requests
        List<String> list1 = receiverFriends.getUserRequests();
        list1.remove(senderId);
        receiverFriends.setUserRequests(list1);

      // add the sender in receiver's suggestions : can be removed when deploying, just for testing
        List<Suggestion> list2 = receiverFriends.getUserSuggestions();
        list2.add(new Suggestion(senderId, false));
        receiverFriends.setUserSuggestions(list2);

      // saving friends
        friendsRepo.save(receiverFriends); friendsRepo.save(senderFriends);

      // saving activity to sender
        Activity activity = new Activity();
        activity.setDate(new Date());
        activity.setType("rejectRequest");
        activity.setUserId(senderId);
        activity.setName(receiver.getUserName() + " has rejected your friend request");

        activityRepo.save(activity);

    }

    public String getFriends(String userId) throws JsonProcessingException {
        FriendsResponse response = new FriendsResponse();
        SocketResponse res = new SocketResponse();
        DefaultResponse resp = new DefaultResponse();

        try {
            List<EveryGroupUsers> list = new ArrayList<EveryGroupUsers>();
            User user = userRepo.findByUserId(userId);
            Friends friends = friendsRepo.findByFid(user.getUserFriendsId());

            for(Suggestion item: friends.getUserSuggestions()){
                User temp = userRepo.findByUserId(item.getUserId());
                EveryGroupUsers tempUser = new EveryGroupUsers(temp.getUserName(), temp.getUserEmail(), temp.get_id(), temp.getImageUrl());
                list.add(tempUser);
            }

            for(String item: friends.getUserRequests()){
                User temp = userRepo.findByUserId(item);
                EveryGroupUsers tempUser = new EveryGroupUsers(temp.getUserName(), temp.getUserEmail(), temp.get_id(), temp.getImageUrl());
                list.add(tempUser);
            }

            for(String item: friends.getUserFriends()){
                User temp = userRepo.findByUserId(item);
                EveryGroupUsers tempUser = new EveryGroupUsers(temp.getUserName(), temp.getUserEmail(), temp.get_id(), temp.getImageUrl());
                list.add(tempUser);
            }

            resp.setStatus(200);
            resp.setMessage("User Friends Fetched Successfully!!");

            response.setSuggestions(friends.getUserSuggestions());
            response.setFriends(friends.getUserFriends());
            response.setRequests(friends.getUserRequests());
            response.setUsers(list);

        } catch (Exception e) {
//            throw new RuntimeException(e);
             resp.setStatus(400);
             resp.setMessage(e.toString());
        }

        res.setMessageType("userFriends");
        res.setResponse(resp);
        res.setBody(ow.writeValueAsString(response));

        return ow.writeValueAsString(res);
    }

    public String getDetails(String userId, String targetId) throws JsonProcessingException{
        SocketResponse response = new SocketResponse();
        DefaultResponse res = new DefaultResponse();

        try {
            User user1 = userRepo.findByUserId(userId), user2 = userRepo.findByUserId(targetId);
            UserDetails userDetails1 = userDetailsRepo.findByUDId(user1.getUserDetailsId()), userDetails2 = userDetailsRepo.findByUDId(user2.getUserDetailsId());
            List<GroupUser> list1 = userDetails1.getUserGroups(), list2 = userDetails2.getUserGroups();
            List<String> ids = new ArrayList<String>();
            for(GroupUser item: list2){
                ids.add(item.getGroupId());
            }
            List<GroupShare> ans = new ArrayList<GroupShare>();

            for(GroupUser item: list1){
                if(ids.contains(item.getGroupId())){
                    Group group = groupRepo.findByGroupId(item.getGroupId());
                    ans.add(new GroupShare(group.getGroupName(), group.getGroupPhoto(), item.getUserBalance()));
                }
            }

            response.setBody(ow.writeValueAsString(ans));
            res.setStatus(200);
            res.setMessage("Common Groups Fetched Successfully!");
        } catch (JsonProcessingException e) {
            res.setStatus(400);
            res.setMessage(e.toString());
        }

        response.setResponse(res);
        response.setMessageType("commonGroups");

        return ow.writeValueAsString(response);
    }

    public DefaultResponse handleRequest(FriendRequest request)  {
        DefaultResponse response = new DefaultResponse();
        try {
            String type = request.getType();
            if(type.equals("sendRequest")){
                SendRequest(request.getSenderId(), request.getReceiverId());
            }
            else if(type.equals("acceptRequest")){
                AcceptRequest(request.getSenderId(), request.getReceiverId());
            }
            else {
                RejectRequest(request.getSenderId(), request.getReceiverId());
            }

            response.setMessage("Request Processed Successfully!");
            response.setStatus(200);
        } catch (Exception e) {
            response.setMessage(e.toString());
            response.setStatus(400);
        }

        return response;
    }


}
