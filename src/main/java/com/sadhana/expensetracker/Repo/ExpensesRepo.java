package com.sadhana.expensetracker.Repo;

import com.sadhana.expensetracker.Model.Expense;
import com.sadhana.expensetracker.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ExpensesRepo extends JpaRepository<Expense, Long> {


    public List<Expense> findByUser(Users user);


    @Query("select e from Expense e where e.date between :start and :end")
    public List<Expense> findExpensesBetweenDates(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
