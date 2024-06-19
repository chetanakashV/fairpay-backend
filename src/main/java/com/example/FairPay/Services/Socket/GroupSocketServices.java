package com.example.FairPay.Services.Socket;


import com.example.FairPay.Models.DB.Expense;
import com.example.FairPay.Models.DB.Group;
import com.example.FairPay.Models.DB.GroupMembers;
import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.RequestBodies.Expenses.GetBalancesBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.SocketResponse;
import com.example.FairPay.Models.Types.CompleteGroupDetails;
import com.example.FairPay.Models.Types.EveryGroupUsers;
import com.example.FairPay.Models.Types.GroupUserBalance;
import com.example.FairPay.Models.Types.Participant;
import com.example.FairPay.Repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.mail.Part;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupSocketServices {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ObjectMapper om = new ObjectMapper();


    public String getAllGroups(String userId) throws JsonProcessingException {
        SocketResponse socketResponse = new SocketResponse();
        DefaultResponse response = new DefaultResponse();

        try {
            //get userDetailsId
            User user = userRepo.findByUserId(userId);
            String userDetailsId = user.getUserDetailsId();

            JsonNode rootNode = om.readTree(userDetailsRepo.findUserGroups(userDetailsId));

            List<JsonNode> userGroups = new ArrayList<>();

                JsonNode userGroupsNode = rootNode.get("userGroups");
                if (userGroupsNode != null && userGroupsNode.isArray()) {
                    userGroupsNode.forEach(userGroups::add);
                }

            List<Group> groups = new ArrayList<Group>();
            for(JsonNode groupNode : userGroups) {
                String groupId = groupNode.get("groupId").asText();

                groups.add(groupRepo.findByGroupId(groupId));
            }
            response.setMessage("User Groups Fetched Successfully");
            response.setStatus(200);
            socketResponse.setBody(ow.writeValueAsString(groups));


        } catch (Exception e) {
            response.setStatus(400);
            response.setMessage(e.toString());
        }

           socketResponse.setMessageType("userGroups");
           socketResponse.setResponse(response);
           return ow.writeValueAsString(socketResponse);
    }




    public String getUserFromEmail(String userEmail) throws JsonProcessingException {
        DefaultResponse res = new DefaultResponse();
        SocketResponse response = new SocketResponse();
        try {
            User user = userRepo.findByEmail(userEmail);
            if(user == null){
            res.setStatus(404);
            res.setMessage("User With Email Not found!!");
            }else {
                res.setStatus(200);
                response.setBody(ow.writeValueAsString(user));
                res.setMessage("User Details Fetched Successfully!!");
            }
        } catch (Exception e) {
            res.setStatus(400);
            res.setMessage(e.toString());
        }
        response.setResponse(res);
        response.setMessageType("userByEmail");
        return ow.writeValueAsString(response);
    }


    public String getGroupDetails(String groupId) throws JsonProcessingException {
        DefaultResponse res = new DefaultResponse();
        SocketResponse response = new SocketResponse();
        CompleteGroupDetails details = new CompleteGroupDetails();

        try{
            Group group = groupRepo.findByGroupId(groupId);
            GroupMembers groupMembers = groupMembersRepo.findByGMId(group.getGroupMembersId());

            details.setGroupId(group.get_id());
            details.setGroupDescription(group.getGroupDescription());
            details.setGroupPhoto(group.getGroupPhoto());
            details.setGroupType(group.getGroupType());
            details.setGroupName(group.getGroupName());
            details.setGroupMembersBody(ow.writeValueAsString(groupMembers));

            List<Expense> list = new ArrayList<Expense>();
            List<String> temp = group.getGroupExpenses();
            for(String item: temp ){
                Expense expense = expenseRepo.findByEid(item);
                list.add(expense);
            }



            List<Participant> temp2 = groupMembersRepo.findByGMId(group.getGroupMembersId()).getGroupParticipants();
            List<EveryGroupUsers> list2 = new ArrayList<EveryGroupUsers>();

            for(Participant item: temp2){
                User user = userRepo.findByUserId(item.getUserId());
                list2.add(new EveryGroupUsers(user.getUserName(), user.getUserEmail(), user.get_id(), user.getImageUrl()));
            }

            details.setExpensesBody(ow.writeValueAsString(list));
            details.setGroupUsersBody(ow.writeValueAsString(list2));

            res.setStatus(200);
            res.setMessage("Details Fetched Successfully !!");
        }
        catch (Exception e){
            res.setStatus(400);
            res.setMessage(e.toString());
        }

        response.setResponse(res);
        response.setMessageType("groupDetails");
        response.setBody(ow.writeValueAsString(details));

        return ow.writeValueAsString(response);
    }

    public String getGroupUsers(String groupId) throws JsonProcessingException {
        DefaultResponse res = new DefaultResponse();
        SocketResponse response = new SocketResponse();

        try {
            Group group = groupRepo.findByGroupId(groupId);
            String gmId = group.getGroupMembersId();
            GroupMembers groupMembers = groupMembersRepo.findByGMId(gmId);
            List<Participant> temp = groupMembers.getGroupParticipants();

            List<EveryGroupUsers> list = new ArrayList<EveryGroupUsers>();

            for( Participant item: temp){
                User user = userRepo.findByUserId(item.getUserId());
                EveryGroupUsers newItem = new EveryGroupUsers(user.getUserName(), user.getUserEmail(), user.get_id(), user.getImageUrl());
                list.add(newItem);
            }
            res.setMessage("Users of Group Fetched Successfully!!");
            res.setStatus(200);
           response.setBody(ow.writeValueAsString(list));
        } catch (Exception e) {
            res.setStatus(400);
            res.setMessage(e.toString());
        }

        response.setResponse(res);
        response.setMessageType("groupUserDetails");

        return ow.writeValueAsString(response);

    }

    public String getGroupBalances(GetBalancesBody body) throws JsonProcessingException {
        SocketResponse res = new SocketResponse();
        DefaultResponse response = new DefaultResponse();

        try {

            Group group = groupRepo.findByGroupId(body.getGroupId());
            GroupMembers groupMembers = groupMembersRepo.findByGMId(group.getGroupMembersId());

            List<Participant> list = groupMembers.getGroupParticipants();
            List<GroupUserBalance> ans = new ArrayList<GroupUserBalance>();

            Float total = 0.0F;
            List<Participant> pos = new ArrayList<Participant>(), neg = new ArrayList<Participant>();

            for(Participant item: list){
                if(item.getBalance() > 0.0F) {
                    pos.add(item); total += item.getBalance();
                }
                else if(item.getBalance() < 0.0F) neg.add(item);
                ans.add(new GroupUserBalance(item.getUserId(), item.getBalance()));
            }

            int p1 = 0, p2 = 0, trans = 1000;

            while(total > 0 && trans > 0){
                trans--;
                Participant first = pos.get(p1), second = neg.get(p2);

                User firstUser = userRepo.findByUserId(first.getUserId()),
                secondUser = userRepo.findByUserId(second.getUserId());

                if(first.getBalance() < Math.abs(second.getBalance())){
                    String s1, s2;

                    s1 = firstUser.getUserName() + " Gets ₹" + (double) Math.round(first.getBalance() * 100) / 100 + " from " + secondUser.getUserName();
                    s2 = secondUser.getUserName() + " Owes ₹" + (double) Math.round(first.getBalance() * 100) / 100 + " to " + firstUser.getUserName();
                    total -= first.getBalance(); p1++;
                    second.setBalance(second.getBalance() + first.getBalance());

                    for(GroupUserBalance temp: ans){
                        if(temp.getUserId().equals(first.getUserId())){
                            List<String> temp2 = temp.getItems();
                            temp2.add(s1);
                            temp.setItems(temp2);
                        }
                        else if(temp.getUserId().equals(second.getUserId())){
                            List<String> temp2 = temp.getItems();
                            temp2.add(s2);
                            temp.setItems(temp2);
                        }
                    }
                }
                else if(first.getBalance() > Math.abs(second.getBalance())){
                    String s1, s2;

                    s1 = firstUser.getUserName() + " Gets ₹" + Math.abs((double) Math.round(second.getBalance() * 100) / 100) +" from " + secondUser.getUserName();
                    s2 = secondUser.getUserName() + " Owes ₹" + Math.abs((double) Math.round(second.getBalance() * 100) / 100) + " to " + firstUser.getUserName();
                    total -= Math.abs(second.getBalance()); p2++;
                    first.setBalance(first.getBalance() + second.getBalance());

                    for(GroupUserBalance temp: ans){
                        if(temp.getUserId().equals(first.getUserId())){
                            List<String> temp2 = temp.getItems();
                            temp2.add(s1);
                            temp.setItems(temp2);
                        }
                        else if(temp.getUserId().equals(second.getUserId())){
                            List<String> temp2 = temp.getItems();
                            temp2.add(s2);
                            temp.setItems(temp2);
                        }
                    }
                }
                else {
                    String s1, s2;

                    s1 = firstUser.getUserName() + " Gets ₹" + Math.abs((double) Math.round(second.getBalance() * 100) / 100) +" from " + secondUser.getUserName();
                    s2 = secondUser.getUserName() + " Owes ₹" + Math.abs((double) Math.round(second.getBalance() * 100) / 100) + " to " + firstUser.getUserName();
                    total -= Math.abs(second.getBalance()); p2++; p1++;

                    for(GroupUserBalance temp: ans){
                        if(temp.getUserId().equals(first.getUserId())){
                            List<String> temp2 = temp.getItems();
                            temp2.add(s1);
                            temp.setItems(temp2);
                        }
                        else if(temp.getUserId().equals(second.getUserId())){
                            List<String> temp2 = temp.getItems();
                            temp2.add(s2);
                            temp.setItems(temp2);
                        }
                    }
                }

            }

                res.setBody(ow.writeValueAsString(ans));
                response.setMessage("Group Balances Fetched Successfully!");
                response.setStatus(200);

        } catch (Exception e) {
            response.setStatus(400);
            response.setMessage(e.toString());
            throw new RuntimeException(e);
        }

        res.setResponse(response);
        res.setMessageType("groupBalances");


        return ow.writeValueAsString(res);
    }
}
