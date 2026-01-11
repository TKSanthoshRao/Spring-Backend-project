package com.sadhana.expensetracker.Service;

import com.sadhana.expensetracker.Model.Expense;
import com.sadhana.expensetracker.Model.UserPrincipal;
import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Repo.ExpensesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpensesRepo expensesRepo;
    private final SecurityService securityService;

    @Autowired
    public ExpenseService(ExpensesRepo expensesRepo, SecurityService securityService) {
        this.expensesRepo = expensesRepo;
        this.securityService = securityService;
    }

    public ResponseEntity<List<Expense>> giveAllExpenses() {

        Users user = securityService.getCurrentUser();
        List<Expense> expenses = new ArrayList<>();
        expenses = expensesRepo.findByUser(user);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    public ResponseEntity<List<Expense>> giveExpensesByDate(LocalDate startDate, LocalDate endDate) {
        if(startDate == null || endDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1);
            endDate = LocalDate.now().withDayOfMonth(
                    LocalDate.now().lengthOfMonth());
        }

        return ResponseEntity.ok().body(expensesRepo.findExpensesBetweenDates(startDate, endDate));
    }

    public ResponseEntity<Expense> addExpense(Expense expense) {
        Users user = securityService.getCurrentUser();
        expense.setUser(user);
        Expense expense1 = expensesRepo.save(expense);
        return new ResponseEntity<>(expense1, HttpStatus.CREATED);
    }

    public Expense findbyID(Long id) {
            return expensesRepo.findById(id).orElse(null);
    }

    public ResponseEntity<String> deleteExpense(Long id) {
        if (!expensesRepo.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Expense not found");
        }
        expensesRepo.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
