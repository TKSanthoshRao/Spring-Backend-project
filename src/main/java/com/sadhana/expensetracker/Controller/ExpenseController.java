package com.sadhana.expensetracker.Controller;

import com.sadhana.expensetracker.Model.Expense;
import com.sadhana.expensetracker.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class ExpenseController {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> allExpenses() {
        System.out.println("Getting all expenses");
        return expenseService.giveAllExpenses();
    }

    @GetMapping("/expense/filter")
    public ResponseEntity<List<Expense>> allExpensesthisMonth(@RequestParam LocalDate Startdate , @RequestParam LocalDate EndDate) {
        System.out.println(
                "Getting all expenses " + Startdate + " to " + EndDate + " and month"
        );

        return expenseService.giveExpensesByDate(Startdate,EndDate);
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
           return expenseService.addExpense(expense);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        return expenseService.deleteExpense(id);
    }




}
