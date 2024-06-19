package com.example.FairPay.Services;

import com.example.FairPay.Models.DB.*;
import com.example.FairPay.Models.RequestBodies.Groups.AddParticipantBody;
import com.example.FairPay.Models.RequestBodies.Groups.CreateGroupBody;
import com.example.FairPay.Models.RequestBodies.Groups.RemoveParticipantBody;
import com.example.FairPay.Models.RequestBodies.Groups.UpdateGroupBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Repo.*;
import com.example.FairPay.Services.Socket.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.FairPay.Models.Types.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Part;
import java.util.Date;
import java.util.List;

@Service
public class GroupServices {

    @Autowired
    private GroupRepo groupRepo;


    @Autowired
    private UserDetailsServices userDetailsService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SettingsRepo settingsRepo;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private GroupMemberServices groupMemberService;


    public DefaultResponse createGroup(CreateGroupBody body) {
        DefaultResponse response = new DefaultResponse();

        try{
            Group group = new Group();
            group.setGroupName(body.getGroupName());
            group.setGroupDescription(body.getGroupDescription());
            group.setGroupMembersId(groupMemberService.createGroupMembers(body));
            if(body.getGroupPhoto().isEmpty()){
                group.setGroupPhoto(GravatarUtil.generateGravatarUrl(group.getGroupMembersId()));
            }
            else {
                group.setGroupPhoto(body.getGroupPhoto());
            }
            group.setGroupType(body.getGroupType());
            group.setCreatedBy(body.getCreatedBy());
            List<String> temp = body.getGroupMembers();
            groupRepo.save(group);
            String groupId = group.get_id();
            User creator = userRepo.findByUserId(body.getCreatedBy());
            for(String member: temp){
                User user = userRepo.findByEmail(member);
                String _id = user.get_id();
                if(_id != null) {
                    userDetailsService.addUserGroup(groupId, _id);
                    Settings settings = settingsRepo.findBySid(user.getUserSettingsId());

                    if(settings.isNewGroup() && !creator.get_id().equals(_id)) {
                        emailServices.sendMail(member, "Hey There," + "\n"  + " You've been added to " + group.getGroupName() + " by "+ creator.getUserName() + " on Fair Pay.\n" +
                                "\n" +
                                "For notification settings: account settings > Notifications > Groups And Updates .\n" +
                                "\n" +
                                "See you there!\n" +
                                "\n" +
                                "The Fair Pay Team", "Added to Group: " + group.getGroupName() + " on FairPay");
                    }

                    Activity activity = new Activity();
                    activity.setType("newGroup");
                    activity.setUserId(_id);

                    
                    if(_id.equals(body.getCreatedBy())){
                        activity.setName("You Created Group '" + body.getGroupName() + "'" );
                    }
                    else{
                        activity.setName("You Have been added to '" + body.getGroupName() + "' By '" + creator.getUserName() + "'");
                    }
                    activity.setDate(new Date());
                    activityRepo.save(activity);
                    userDetailsService.addActivity(activity.get_id(), _id);
                }
            }
            response.setStatus(200);
            response.setMessage("Group Created Successfully");

            notificationService.sendCreatedGroupToUser(group);
        }
        catch (Exception e){
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;

    }

    public DefaultResponse deleteGroup(String groupId){
        DefaultResponse response = new DefaultResponse();
        try{
        Group group = groupRepo.findByGroupId(groupId);
        String gmId = group.getGroupMembersId();

        List<Participant> list = groupMemberService.getParticipants(gmId);

        for(Participant part: list){
            userDetailsService.deleteUserGroup(groupId, part.getUserId());
        }

        groupMemberService.deleteGroupMembers(gmId);
        groupRepo.deleteById(groupId);

        response.setStatus(200);
        response.setMessage("Group Deleted Successfully!!");

        }
        catch (Exception e){
            response.setStatus(400);
            response.setMessage(e.toString());
        }


        return response;
    }


    public DefaultResponse addParticipant(AddParticipantBody body, String updatorId){
        DefaultResponse response = new DefaultResponse();
        try{
            userDetailsService.addUserGroup(body.getGroupId(), body.getUserId());
            Group group = groupRepo.findByGroupId(body.getGroupId());
            String gmId = group.getGroupMembersId();
            groupMemberService.addParticipant(body.getUserId(), gmId);

            User updater = userRepo.findByUserId(updatorId);
            Settings settings = settingsRepo.findBySid(body.getUserId());
            if(settings.isNewGroup()){
                User user = userRepo.findByUserId(body.getUserId());
                emailServices.sendMail(user.getUserEmail(), "Hey There," + "\n" + "\n"  + " You've been added to " + group.getGroupName() + " by "+ updater.getUserName() + " on Fair Pay.\n" +
                        "\n" +
                        "For notification settings: account settings > Notifications > Groups And Updates .\n" +
                        "\n" +
                        "See you there!\n" +
                        "\n" +
                        "The Fair Pay Team", "Added to Group: " + group.getGroupName() + " on FairPay");
            }

            Activity activity = new Activity();
            activity.setName("You have been added to the group '" + group.getGroupName() + "'");
            activity.setType("newGroup");
            activity.setDate(new Date());
            activity.setUserId(body.getUserId());
            activityRepo.save(activity);

            userDetailsService.addActivity(activity.get_id(),body.getUserId());

            response.setStatus(200);
            response.setMessage("User Added Successfully");
        }
        catch (Exception e){
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;

    }


    public DefaultResponse removeParticipant(RemoveParticipantBody body){
        DefaultResponse response = new DefaultResponse();
        try{
            userDetailsService.deleteUserGroup(body.getGroupId(), body.getUserId());
            Group group = groupRepo.findByGroupId(body.getGroupId());
            String gmId = group.getGroupMembersId();
            groupMemberService.removeParticipant(body.getUserId(), gmId);
            response.setStatus(200);
            response.setMessage("User Removed Successfully");
        }
        catch (Exception e){
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;
    }

    public DefaultResponse updateGroup(UpdateGroupBody body){
        DefaultResponse response = new DefaultResponse();

        try {
            Group group = groupRepo.findByGroupId(body.getGroupId());

            group.setGroupName(body.getGroupName());
            group.setGroupDescription(body.getGroupDescription());
            group.setGroupPhoto(body.getGroupPhoto());
            group.setGroupType(body.getGroupType());
            groupRepo.save(group);

            notificationService.groupUpdated(group.get_id());
            User updator = userRepo.findByUserId(body.getUpdatedBy());

            GroupMembers groupMembers = groupMembersRepo.findByGMId(group.getGroupMembersId());
            List<Participant> list = groupMembers.getGroupParticipants();

            for(Participant item: list){
                User user = userRepo.findByUserId(item.getUserId());
                if(item.getUserId().equals(body.getUpdatedBy())) continue;
                Settings settings = settingsRepo.findBySid(user.getUserSettingsId());
                if(!settings.isUpdateGroup()) continue;
                emailServices.sendMail(user.getUserEmail(),"Hey There,\n" +
                        "\n" +
                        "Just a quick heads-up that the details for the " + group.getGroupName() +" group on FairPay have been updated by " + updator.getUserName() +
                        "\n" +
                        "See you in the group!\n" +
                        "\n" +
                        "The FairPay Team","Updates to "+ group.getGroupName() + " on FairPay" );
            }

            for(String newUser: body.getNewUsers()){
                addParticipant(new AddParticipantBody(newUser, group.get_id()), body.getUpdatedBy());
            }

            response.setStatus(200);
            response.setMessage("Group Updated Successfully!");
        } catch (Exception e) {
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;
    }


}
