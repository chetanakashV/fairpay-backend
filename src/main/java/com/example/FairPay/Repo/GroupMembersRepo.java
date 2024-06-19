package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.GroupMembers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepo extends MongoRepository<GroupMembers, String> {

    @Query("{'_id':?0}")
    public GroupMembers findByGMId(String gmId);
}
