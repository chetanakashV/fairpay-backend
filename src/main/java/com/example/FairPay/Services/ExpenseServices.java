package com.example.FairPay.Services;

import com.example.FairPay.Models.DB.*;
import com.example.FairPay.Models.RequestBodies.Expenses.CreateExpenseBody;
import com.example.FairPay.Models.RequestBodies.Expenses.DeleteExpenseBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.Types.GroupUser;
import com.example.FairPay.Models.Types.Participant;
import com.example.FairPay.Repo.*;
import com.example.FairPay.Services.Socket.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseServices {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private SettingsRepo settingsRepo;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private GroupMembersRepo groupMembersRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private UserDetailsServices userDetailsServices;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private NotificationService notificationService;


    public DefaultResponse addExpense(CreateExpenseBody body) {

        DefaultResponse response = new DefaultResponse();

        try{
            Expense exp = new Expense();

            exp.setContributorId(body.getContributorId());
            exp.setTotalAmount(body.getTotalAmount());
            exp.setTransactionDate(body.getTransactionDate());
            exp.setParticipants(body.getParticipants());
            exp.setGroupId(body.getGroupId());
            exp.setDescription(body.getDescription());
            exp.setCreatedBy(body.getCreatedBy());

            //saving in expense Repo
            expenseRepo.save(exp);

            notificationService.sendCreatedExpenseToGroup(exp.get_id());

            Group group = groupRepo.findByGroupId(body.getGroupId());
            List<String> groupExpenses = group.getGroupExpenses();
            groupExpenses.add(exp.get_id());
            group.setGroupExpenses(groupExpenses);
            //saving to group Expenses
            groupRepo.save(group);

            String gmId = group.getGroupMembersId();
            GroupMembers groupMembers = groupMembersRepo.findByGMId(gmId);

            List<Participant> list = groupMembers.getGroupParticipants();
            List<Participant> temp = body.getParticipants();

            User contributor = userRepo.findByUserId(exp.getCreatedBy());

            for(Participant item1: temp){
                for(Participant item2: list){
                    if(item1.getUserId().equals(item2.getUserId())){
                        item2.setBalance(item2.getBalance() - item1.getBalance());

                        User user = userRepo.findByUserId(item1.getUserId());
                        UserDetails userDetails = userDetailsRepo.findByUDId(user.getUserDetailsId());

                        Activity activity = new Activity();

                        activity.setDate(new Date());
                        activity.setType("newExpense");
                        activity.setUserId(item1.getUserId());

                        if(contributor.get_id().equals(item2.getUserId())) {
                            activity.setName("You added '" + exp.getDescription() + "' in '" + group.getGroupName() + "'");
                        }
                        else {
                            activity.setName(contributor.getUserName() + " added '" + exp.getDescription() + "' in '" + group.getGroupName() + "'");
                        }

                        if(exp.getContributorId().equals(item2.getUserId())){
                            activity.setDescription("You get back ₹" + (exp.getTotalAmount() - item1.getBalance()));
                        }
                        else {
                            activity.setDescription("You owe ₹" + (item1.getBalance()));
                        }

                        activityRepo.save(activity);
                        List<String> activityList = userDetails.getUserActivities();
                        activityList.add(activity.get_id());
                        userDetails.setUserActivities(activityList);

                        List<GroupUser> list1 = userDetails.getUserGroups();

                        //updating user balances and user's balance in groups
                        for(GroupUser group1: list1){
                            if(group1.getGroupId().equals(body.getGroupId())){
                                group1.setUserBalance(group1.getUserBalance() - item1.getBalance());

                                if(body.getContributorId().equals(user.get_id())){
                                    group1.setUserBalance(group1.getUserBalance() + body.getTotalAmount());

                                    userDetails.setTotalBalance(userDetails.getTotalBalance() + body.getTotalAmount() - item1.getBalance());
                                    List<GroupUser> temp2 = userDetails.getUserGroups();
                                    userDetails.setAmountOwed(0.0F);
                                    for(GroupUser el: temp2){
                                        if(el.getUserBalance() > 0) {
                                            userDetails.setAmountOwed(userDetails.getAmountOwed() + el.getUserBalance());
                                        }
                                    }
                                    userDetails.setAmountYouOwe(0.0F);
                                    for(GroupUser el: temp2){
                                        if(el.getUserBalance() < 0) {
                                            userDetails.setAmountYouOwe(userDetails.getAmountYouOwe() - el.getUserBalance());
                                        }
                                    }
                                }

                                else {
                                    userDetails.setTotalBalance(userDetails.getTotalBalance() - item1.getBalance());
                                    List<GroupUser> temp2 = userDetails.getUserGroups();
                                    userDetails.setAmountYouOwe(0.0F);
                                    for(GroupUser el: temp2){
                                        if(el.getUserBalance() < 0) {
                                            userDetails.setAmountYouOwe(userDetails.getAmountYouOwe() - el.getUserBalance());
                                        }
                                    }
                                    userDetails.setAmountOwed(0.0F);
                                    for(GroupUser el: temp2){
                                        if(el.getUserBalance() > 0) {
                                            userDetails.setAmountOwed(userDetails.getAmountOwed() + el.getUserBalance());
                                        }
                                    }
                                }

                            }
                        }

                        userDetailsRepo.save(userDetails);
                    }
                }
            }


            for(Participant item: list){
                if(item.getUserId().equals(body.getContributorId())){
                    item.setBalance(item.getBalance() + body.getTotalAmount());
                }
            }
            User payer = userRepo.findByUserId(exp.getContributorId());
            for(Participant item: list){
                User user = userRepo.findByUserId(item.getUserId());
                Settings settings = settingsRepo.findBySid(user.getUserSettingsId());
                if(!settings.isNewExpense()) continue;
                emailServices.sendMail(user.getUserEmail(), "Hey there,\n" +
                        "\n" +
                        contributor.getUserName() + " just added an expense for " + exp.getDescription() +" to the group.\n" + group.getGroupName()+
                        "\n" +
                        "Total: " + exp.getTotalAmount() + "\n" +
                        "Paid by: " + payer.getUserName() + "\n" +
                        "See the details and split it up on FairPay\n" +
                        "\n" +
                        "The FairPay Team", "New Expense: " + exp.getDescription());
            }

            groupMembersRepo.save(groupMembers);

            response.setMessage("Transaction Added Successfully!!");
            response.setStatus(200);



        }
        catch (Exception e){
            response.setMessage(e.toString());
            response.setStatus(400);
        }

        return response;
    }


    public DefaultResponse deleteExpense(DeleteExpenseBody body) {
        String expenseId = body.getExpenseId();

        DefaultResponse response = new DefaultResponse();

        try {
            //deleting expense from expense repo
            Expense exp = expenseRepo.findByEid(expenseId);
            User contributor = userRepo.findByUserId(exp.getCreatedBy());
            expenseRepo.deleteById(expenseId);


            String groupId = exp.getGroupId();
            Group group = groupRepo.findByGroupId(groupId);

            notificationService.sendDeletedExpenseToGroup(expenseId, groupId );

            //deleting expense from groupExpenses
            List<String> groupExpenses = group.getGroupExpenses();
            groupExpenses.remove(exp.get_id());
            groupRepo.save(group);

            String gmId = group.getGroupMembersId();
            GroupMembers groupMembers = groupMembersRepo.findByGMId(gmId);

            List<Participant> list = groupMembers.getGroupParticipants();
            List<Participant> list2 = exp.getParticipants();


            for(Participant item2: list2){
                User user = userRepo.findByUserId(item2.getUserId());
                UserDetails userDetails = userDetailsRepo.findByUDId(user.getUserDetailsId());
                List<GroupUser> temp = userDetails.getUserGroups();

                for(GroupUser item3: temp){
                    if(item3.getGroupId().equals(group.get_id())){
                        if(user.get_id().equals(exp.getContributorId())){
                            item3.setUserBalance(item3.getUserBalance() - exp.getTotalAmount() + item2.getBalance());
                        }
                        else {
                            item3.setUserBalance(item3.getUserBalance() + item2.getBalance());
                        }
                    }
                }

                userDetailsRepo.save(userDetails);
            }

            //updating balances of users in the group
            for(Participant item: list){
                for(Participant item2: list2){
                    if(item.getUserId().equals(item2.getUserId())){
                        User user = userRepo.findByUserId(item.getUserId());
                        UserDetails userDetails = userDetailsRepo.findByUDId(user.getUserDetailsId());

                        Activity activity = new Activity();

                        activity.setDate(new Date());
                        activity.setType("deleteExpense");
                        activity.setUserId(item.getUserId());

                        if(contributor.get_id().equals(item.getUserId())) {
                            activity.setName("You deleted '" + exp.getDescription() + "' from '" + group.getGroupName() + "'");
                        }
                        else {
                            activity.setName(contributor.getUserName() + " deleted '" + exp.getDescription() + "' from '" + group.getGroupName() + "'");
                        }


                        if(exp.getContributorId().equals(item2.getUserId())){
                            activity.setDescription("You get back ₹" + (exp.getTotalAmount() - item2.getBalance()));
                        }
                        else {
                            activity.setDescription("You owe ₹" + (item2.getBalance()));
                        }

                        activityRepo.save(activity);
                        List<String> activityList = userDetails.getUserActivities();
                        activityList.add(activity.get_id());
                        userDetails.setUserActivities(activityList);

                        if(item.getUserId().equals(exp.getContributorId())){
                            userDetails.setTotalBalance(userDetails.getTotalBalance() + item2.getBalance() - exp.getTotalAmount());
                            item.setBalance(item.getBalance() + item2.getBalance() - exp.getTotalAmount());

                            List<GroupUser> temp = userDetails.getUserGroups();
                            userDetails.setAmountOwed(0.0F);
                            for(GroupUser el: temp){
                                if(el.getUserBalance() > 0) {
                                    userDetails.setAmountOwed(userDetails.getAmountOwed() + el.getUserBalance());
                                }
                            }
                            userDetails.setAmountYouOwe(0.0F);
                            for(GroupUser el: temp){
                                if(el.getUserBalance() < 0) {
                                    userDetails.setAmountYouOwe(userDetails.getAmountYouOwe() - el.getUserBalance());
                                }
                            }
                            userDetails.setAmountOwed(0.0F);
                        }
                        else {
                            item.setBalance(item.getBalance() + item2.getBalance());
                            userDetails.setTotalBalance(userDetails.getTotalBalance() + item2.getBalance());

                            List<GroupUser> temp = userDetails.getUserGroups();
                            userDetails.setAmountYouOwe(0.0F);
                            for(GroupUser el: temp){
                                if(el.getUserBalance() < 0) {
                                    userDetails.setAmountYouOwe(userDetails.getAmountYouOwe() - el.getUserBalance());
                                }
                            }
                            userDetails.setAmountOwed(0.0F);
                            for(GroupUser el: temp){
                                if(el.getUserBalance() > 0) {
                                    userDetails.setAmountOwed(userDetails.getAmountOwed() + el.getUserBalance());
                                }
                            }
                        }

                        userDetailsRepo.save(userDetails);
                    }
                }
            }

            for(Participant item: list){
                User user = userRepo.findByUserId(item.getUserId());
                Settings settings = settingsRepo.findBySid(user.getUserSettingsId());

                if(!settings.isDeleteExpense()) continue;

                emailServices.sendMail(user.getUserEmail(), "Hey there,\n" +
                        "\n" +
                        "This quick note is to let you know that the expense for " + exp.getDescription() +" has been removed from the group.\n" +
                        "\n" +
                        "No further action needed on your end.\n" +
                        "\n" +
                        "The FairPay Team", "Expense Removed: " + exp.getDescription());
            }

            groupMembers.setGroupParticipants(list);

            response.setMessage("Transaction Deleted Successfully!!");
            response.setStatus(200);

            groupMembersRepo.save(groupMembers);

        } catch (Exception e) {
            response.setMessage(e.toString());
            response.setStatus(400);
            throw new RuntimeException(e);
        }

        return response;

    }
}
