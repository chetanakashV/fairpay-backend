package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepo extends MongoRepository<Activity, String > {

    @Query("{'_id': ?0 }")
    Activity findByActivityId(String activityId);

    @Query("{'userId': ?0}")
    Page<Activity> findRecentByUserId(String userId, Pageable pageable);


}
