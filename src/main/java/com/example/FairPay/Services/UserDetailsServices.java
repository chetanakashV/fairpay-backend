package com.example.FairPay.Services;

import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.DB.UserDetails;
import com.example.FairPay.Models.Types.GroupUser;
import com.example.FairPay.Models.Types.Participant;
import com.example.FairPay.Repo.UserDetailsRepo;
import com.example.FairPay.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServices {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private UserRepo userRepo;

    public String createUserDetails(){
        UserDetails userDetails = new UserDetails();
        userDetailsRepo.save(userDetails);
        return userDetails.getId();
    }

    public void addUserGroup(String groupId, String userId){
        try{
            User user1 = userRepo.findByUserId(userId);
            UserDetails userDetails = userDetailsRepo.findByUDId(user1.getUserDetailsId());
            List<GroupUser> temp= userDetails.getUserGroups();
            temp.add(new GroupUser(groupId, 0.0F));
            userDetails.setUserGroups(temp);
            userDetailsRepo.save(userDetails);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    public void addActivity(String activityId, String userId){
        try{
            User user = userRepo.findByUserId(userId);
            UserDetails userDetails = userDetailsRepo.findByUDId(user.getUserDetailsId());
            List<String> list = userDetails.getUserActivities();
            list.add(activityId);
            userDetails.setUserActivities(list);
            userDetailsRepo.save(userDetails);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void deleteUserGroup(String groupId, String userId){
        try{
            User user1 = userRepo.findByUserId(userId);
            String udId = user1.getUserDetailsId();
            UserDetails userDetails = userDetailsRepo.findByUDId(udId);
            List<GroupUser> list = userDetails.getUserGroups();
            GroupUser part = null;
            for(GroupUser gr: list){
                if(gr.getGroupId().equals(groupId)){
                    part = gr;
                }
            }
            if(part != null) list.remove(part);
            userDetails.setUserGroups(list);
            userDetailsRepo.save(userDetails);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }




}
