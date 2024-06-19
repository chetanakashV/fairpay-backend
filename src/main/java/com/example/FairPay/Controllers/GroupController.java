package com.example.FairPay.Controllers;

import com.example.FairPay.Models.DB.Group;
import com.example.FairPay.Models.RequestBodies.Groups.*;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Repo.GroupRepo;
import com.example.FairPay.Services.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("groups")
public class GroupController {

    @Autowired
    private GroupServices groupServices;

    @PostMapping(value = "/createGroup")
    public DefaultResponse createGroup(@RequestBody CreateGroupBody body){
        return groupServices.createGroup(body);
    }

    @DeleteMapping(value = "/deleteGroup")
    public DefaultResponse deleteGroup(@RequestBody DeleteGroupBody body){
        return groupServices.deleteGroup(body.getGroupId());
    }

    @PutMapping(value = "/updateGroup")
    public DefaultResponse updateGroup(@RequestBody UpdateGroupBody body){
        return groupServices.updateGroup(body);
    }


    @DeleteMapping(value = "/removeParticipant")
    public DefaultResponse removeParticipant(@RequestBody RemoveParticipantBody body){
        return groupServices.removeParticipant(body);
    }


}
