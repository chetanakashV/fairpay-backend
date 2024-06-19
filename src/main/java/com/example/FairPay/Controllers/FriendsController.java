package com.example.FairPay.Controllers;


import com.example.FairPay.Models.RequestBodies.Friends.FriendRequest;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.FriendsResponse;
import com.example.FairPay.Repo.FriendsRepo;
import com.example.FairPay.Services.FriendsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("friends")
public class FriendsController {

    @Autowired
    private FriendsRepo friendsRepo;
    @Autowired
    private FriendsServices friendsServices;

    @PostMapping(value = "/handleRequest")
    public DefaultResponse acceptRequest(@RequestBody FriendRequest body) {
       return friendsServices.handleRequest(body);
    }

}
