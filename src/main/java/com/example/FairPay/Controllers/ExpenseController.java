package com.example.FairPay.Controllers;
import com.example.FairPay.Models.RequestBodies.Expenses.CreateExpenseBody;
import com.example.FairPay.Models.RequestBodies.Expenses.DeleteExpenseBody;
import com.example.FairPay.Models.ResponseBodies.DefaultResponse;
import com.example.FairPay.Services.ExpenseServices;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("expenses")
public class ExpenseController {

    @Autowired
    private ExpenseServices expenseServices;


    @PostMapping(value = "/addExpense")
    public DefaultResponse addExpense(@RequestBody CreateExpenseBody body){
        return expenseServices.addExpense(body);
    }

    @PostMapping(value = "/deleteExpense")
    public DefaultResponse deleteExpense(@RequestBody DeleteExpenseBody body){
        return expenseServices.deleteExpense(body);
    }
}
