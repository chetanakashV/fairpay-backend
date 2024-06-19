package com.example.FairPay.Services;

import com.example.FairPay.Models.DB.GroupMembers;
import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.RequestBodies.Groups.CreateGroupBody;
import com.example.FairPay.Models.Types.Participant;
import com.example.FairPay.Repo.GroupMembersRepo;
import com.example.FairPay.Repo.UserRepo;
import org.apache.tomcat.util.modeler.ParameterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberServices {

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private UserRepo userRepo;


    public String createGroupMembers(CreateGroupBody body) {
        GroupMembers groupMembers = new GroupMembers();
        List<String> temp = body.getGroupMembers();
        List<Participant> newGroupMembers = groupMembers.getGroupParticipants();
        for(String member: temp){
            User user = userRepo.findByEmail(member);
            newGroupMembers.add(new Participant(user.get_id(), 0.0F));
        }
        groupMembers.setGroupParticipants(newGroupMembers);
        groupMembersRepo.save(groupMembers);

        return groupMembers.get_id();
    }

    public List<Participant> getParticipants(String groupMemberId){
        GroupMembers groupMembers = groupMembersRepo.findByGMId(groupMemberId);
        return groupMembers.getGroupParticipants();
    }

    public void deleteGroupMembers(String id){
        groupMembersRepo.deleteById(id);
    }

    public void addParticipant(String userId, String gmId){
        GroupMembers gm = groupMembersRepo.findByGMId(gmId);
        List<Participant> list = gm.getGroupParticipants();
        Participant part = new Participant(userId, 0.0F);
        list.add(part);
        gm.setGroupParticipants(list);
        groupMembersRepo.save(gm);
    }


    public void removeParticipant(String userId, String gmId){
        GroupMembers gm = groupMembersRepo.findByGMId(gmId);
        List<Participant> list = gm.getGroupParticipants();
        for(Participant part: list){
            if(part.getUserId().equals(userId)){
                list.remove(part);
                break;
            }
        }
        gm.setGroupParticipants(list);
        groupMembersRepo.save(gm);
    }




}
