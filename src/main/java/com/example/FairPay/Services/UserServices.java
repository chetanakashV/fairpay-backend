package com.example.FairPay.Services;

import com.example.FairPay.Models.DB.Activity;
import com.example.FairPay.Models.DB.Friends;
import com.example.FairPay.Models.DB.Settings;
import com.example.FairPay.Models.RequestBodies.Users.LoginBody;
import com.example.FairPay.Models.RequestBodies.Users.PasswordResetBody;
import com.example.FairPay.Models.RequestBodies.Users.UpdateUserBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Models.ResponseBodies.LoginResponse;
import com.example.FairPay.Repo.ActivityRepo;
import com.example.FairPay.Repo.FriendsRepo;
import com.example.FairPay.Repo.SettingsRepo;
import com.example.FairPay.Repo.UserRepo;
import com.example.FairPay.Models.DB.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsServices userDetailsService;

    @Autowired
    private FriendsServices friendsServices;

    @Autowired
    private  ActivityRepo activityRepo;

    @Autowired
    private FriendsRepo friendsRepo;

    @Autowired
    private SettingsRepo settingsRepo;

    public DefaultResponse registerUser(User user){
        DefaultResponse response = new DefaultResponse();
        User user1;
        try{
            String userEmail = user.getUserEmail();
            String pwd = user.getPassword();
            pwd = PasswordUtils.hashPassword(pwd);
            user.setPassword(pwd);
            user.setImageUrl(AvatarUtil.generateAvatarUrl(user.getUserName(), userEmail));


            try{
                user1 = userRepo.findByEmail(userEmail);
                if(user1 == null){
                    response.setStatus(200);

                    String userDetailsId = userDetailsService.createUserDetails();
                    user.setUserDetailsId(userDetailsId);

                    Settings settings = new Settings();
                    settingsRepo.save(settings);
                    user.setUserSettingsId(settings.get_id());

                    Friends friends = new Friends();
                    friendsRepo.save(friends);
                    user.setUserFriendsId(friends.get_id());

                    response.setMessage("User Registered Successfully!!");
                    userRepo.save(user);

                    friendsServices.NewUser(user.get_id());
                }
                else {
                response.setStatus(400);
                response.setMessage("User Already Exists With This Email!!");
                }
            }
            catch(Exception e){
                response.setStatus(400);
                response.setMessage(e.toString());
            }
        }
        catch(Exception e){
           response.setStatus(400);
           response.setMessage(e.toString());
           return response;
        }


        return response;
    }

    public LoginResponse loginUser(LoginBody body){
        LoginResponse res = new LoginResponse();
        DefaultResponse response = new DefaultResponse();
        User user = null;
        try{
         user = userRepo.findByEmail(body.getUserEmail());
         if(user == null){
             response.setStatus(400);
             response.setMessage("User doesn't exist!!");
         }
         else if(PasswordUtils.checkPassword(body.getPassword(),user.getPassword())){
         response.setStatus(200);
         response.setMessage("User is logged In!!");
         res.setUser(user);
         }
         else {
         response.setStatus(400);
         response.setMessage("Password is Incorrect!!");
         }
        }
        catch(Exception e){

            response.setStatus(404);
            response.setMessage(e.toString());
        }

        res.setResponse(response);

        return res;

    }

    public LoginResponse loginOtp(LoginBody body){
        LoginResponse res = new LoginResponse();
        DefaultResponse response = new DefaultResponse();
        User user = null;
        try{
         user = userRepo.findByEmail(body.getUserEmail());
         if(user == null){
             response.setStatus(400);
             response.setMessage("User doesn't exist!!");
         }
         else{
         response.setStatus(200);
         response.setMessage("User is logged In!!");
         res.setUser(user);
         }

        }
        catch(Exception e){

            response.setStatus(404);
            response.setMessage(e.toString());
        }

        res.setResponse(response);

        return res;

    }



    public DefaultResponse resetPassword(PasswordResetBody body) {
        DefaultResponse response = new DefaultResponse();
        try{
            User user = userRepo.findByEmail(body.getUserEmail());
            String pwd = PasswordUtils.hashPassword(user.getPassword());
            user.setPassword(pwd);
            userRepo.save(user);

            Activity activity = new Activity();
            activity.setType("resetPassword");
            activity.setName("Password Has Been Reset!!");
            activity.setDate(new Date());
            activity.setUserId(user.get_id());
            activityRepo.save(activity);
            userDetailsService.addActivity(activity.get_id(), user.get_id());

            response.setStatus(200);
            response.setMessage("Password has been updated");
        }
        catch(Error e){
            response.setStatus(400);
            response.setMessage(e.toString());
        }

        return response;
    }


    public DefaultResponse updateUser(UpdateUserBody body){
        DefaultResponse res = new DefaultResponse();

        try {
            User user = userRepo.findByUserId(body.getUserId());

            user.setUserEmail(body.getUserEmail());
            user.setUserMobile(body.getUserMobile());
            user.setUserName(body.getUserName());
            if(!body.getPassword().equals("dontChange")){
                user.setPassword(PasswordUtils.hashPassword(body.getPassword()));
            }
            user.setImageUrl(body.getUserPhoto());

            Activity activity = new Activity();
            activity.setName("Your User Details Have been updated");
            activity.setDate(new Date());
            activity.setType("updateUser");
            activity.setUserId(user.get_id());
            activityRepo.save(activity);

            userDetailsService.addActivity(activity.get_id(), user.get_id());

            res.setStatus(200);
            res.setMessage("User Details Updated Successfully!!");

            userRepo.save(user);
        } catch (Exception e) {
            res.setMessage(e.toString());
            res.setStatus(400);
        }

        return res;

    }


}
