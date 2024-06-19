package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.Friends;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepo extends MongoRepository<Friends,String> {
    @Query("{'_id': ?0}")
    Friends findByFid(String fid);
}
