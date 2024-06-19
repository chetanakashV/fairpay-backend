package com.example.FairPay.Services.Socket;
import com.example.FairPay.Models.DB.Expense;
import com.example.FairPay.Models.DB.Group;
import com.example.FairPay.Models.DB.GroupMembers;
import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.SocketResponse;
import com.example.FairPay.Models.Types.Participant;
import com.example.FairPay.Repo.ExpenseRepo;
import com.example.FairPay.Repo.GroupMembersRepo;
import com.example.FairPay.Repo.GroupRepo;
import com.example.FairPay.Repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.FairPay.Services.GroupMemberServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GroupMemberServices groupMemberServices;

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private UserRepo userRepo;

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ObjectMapper om = new ObjectMapper();

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String destination, String message) {
        messagingTemplate.convertAndSend(destination, message);
    }

    public void sendCreatedGroupToUser(Group group){
        SocketResponse response = new SocketResponse();
        DefaultResponse res = new DefaultResponse();


        try {
            String gmId = group.getGroupMembersId();
            List<Participant> list = groupMemberServices.getParticipants(gmId);
            res.setMessage("New Group Fetched Successfully");
            res.setStatus(200);
            response.setResponse(res);
            response.setMessageType("NewGroup");
            response.setBody(ow.writeValueAsString(group));
            for(Participant member: list){
                User user = userRepo.findByUserId(member.getUserId());
                sendMessage("/groups/" + user.get_id(),  ow.writeValueAsString(response));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public void sendCreatedExpenseToGroup(String expenseId)  {

        try {
            SocketResponse res = new SocketResponse();
            DefaultResponse response = new DefaultResponse();

            Expense exp = expenseRepo.findByEid(expenseId);
            Group group = groupRepo.findByGroupId(exp.getGroupId());

            response.setStatus(200);
            response.setMessage("New Expense Fetched Successfully!");

            res.setResponse(response);
            res.setBody(ow.writeValueAsString(exp));
            res.setMessageType("newExpense");

            
            sendMessage("/groups/selectedGroup/" + group.get_id(), ow.writeValueAsString(res));



        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }


    }

    public void groupUpdated(String groupId) throws JsonProcessingException{
        DefaultResponse res = new DefaultResponse();
        SocketResponse response = new SocketResponse();

        Group group = groupRepo.findByGroupId(groupId);
        GroupMembers groupMembers = groupMembersRepo.findByGMId(group.getGroupMembersId());
        res.setStatus(200);
        res.setMessage("Group has been updated");
        response.setMessageType("groupUpdated");
        response.setResponse(res);
        response.setBody(ow.writeValueAsString(group));

        for(Participant item: groupMembers.getGroupParticipants()){
            sendMessage("/groups/" + item.getUserId(), ow.writeValueAsString(response));
        }

    }

    public void sendDeletedExpenseToGroup(String expenseId, String groupId){

        try {
            SocketResponse res = new SocketResponse();
            DefaultResponse response = new DefaultResponse();

            response.setStatus(200);
            response.setMessage("Expense deleted Successfully!!");
            res.setResponse(response);
            res.setMessageType("deletedExpense");
            res.setBody(expenseId);

            sendMessage("/groups/selectedGroup/" + groupId, ow.writeValueAsString(res));
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }

    }



}
