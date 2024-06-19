package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.UserDetails;
import com.example.FairPay.Models.RepoModels.UserGroupsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepo extends MongoRepository<UserDetails, String> {
    @Query(value = "{'_id': ?0}")
    UserDetails findByUDId(String userDetailsId);

    @Query(value = "{'_id':?0}", fields = "{'userGroups': 1}")
    String findUserGroups(String userDetailsId);
}
