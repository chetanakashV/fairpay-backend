package com.example.FairPay.Repo;

import com.example.FairPay.Models.DB.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepo extends MongoRepository <Expense, String> {

    @Query(value="{'_id':?0}")
    public Expense findByEid(String expenseId);

}
