package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {



    @Query("{'userEmail': ?0}")
    User findByEmail(String userEmail);


    @Query("{'userMobile': ?0}")
    User findByMobile(String userMobile);

    @Query("{'_id': ?0}")
    User findByUserId(String userId);

}
