package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends MongoRepository<Group, String> {

    @Query("{'_id': ?0}")
    Group findByGroupId(String groupId);


}
