package com.sadhana.expensetracker.Repo;

import com.sadhana.expensetracker.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles,Long> {
    public Roles findByName(String name);
}
