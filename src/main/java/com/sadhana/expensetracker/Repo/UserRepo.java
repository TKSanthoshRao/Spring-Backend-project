package com.sadhana.expensetracker.Repo;

import com.sadhana.expensetracker.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {

    Users findByUsername(String username);
}
