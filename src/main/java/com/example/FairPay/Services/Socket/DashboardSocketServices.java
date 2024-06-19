package com.example.FairPay.Services.Socket;

import com.example.FairPay.Models.DB.Group;
import com.example.FairPay.Models.DB.User;
import com.example.FairPay.Models.DB.UserDetails;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.SocketResponse;
import com.example.FairPay.Models.Types.CompleteDashboardDetails;
import com.example.FairPay.Models.Types.GroupShare;
import com.example.FairPay.Models.Types.GroupUser;
import com.example.FairPay.Repo.GroupRepo;
import com.example.FairPay.Repo.UserDetailsRepo;
import com.example.FairPay.Repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardSocketServices {

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ObjectMapper om = new ObjectMapper();

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private GroupRepo groupRepo;


    public String getDashboard(String userId) throws JsonProcessingException {
        DefaultResponse res = new DefaultResponse();
        SocketResponse response = new SocketResponse();

        try {
            User user = userRepo.findByUserId(userId);
            UserDetails userDetails = userDetailsRepo.findByUDId(user.getUserDetailsId());

            CompleteDashboardDetails details = new CompleteDashboardDetails();

            details.setTotalBalance(userDetails.getTotalBalance());
            details.setAmountOwed(userDetails.getAmountOwed());
            details.setAmountYouOwe(userDetails.getAmountYouOwe());

            List<GroupShare> list = new ArrayList<GroupShare>();
            List<GroupUser> temp = userDetails.getUserGroups();

            for(GroupUser item: temp){
                Group tempGroup = groupRepo.findByGroupId(item.getGroupId());
                GroupShare newItem = new GroupShare(tempGroup.getGroupName(), tempGroup.getGroupPhoto(), item.getUserBalance());
                list.add(newItem);
            }

            details.setGroups(list);

            res.setStatus(200);
            res.setMessage("User Dashboard Details Fetched Successfully!!");


            response.setMessageType("dashboardDetails");
            response.setBody(ow.writeValueAsString(details));

        } catch (Exception e) {
            res.setMessage(e.toString());
            res.setStatus(400);
//            throw new RuntimeException(e);
        }

        response.setResponse(res);

        return ow.writeValueAsString(response);
    }
}
