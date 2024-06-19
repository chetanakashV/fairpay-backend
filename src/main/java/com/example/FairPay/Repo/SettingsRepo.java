package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepo extends MongoRepository<Settings, String> {
    @Query("{'_id': ?0}")
    public Settings findBySid(String sid);
}
