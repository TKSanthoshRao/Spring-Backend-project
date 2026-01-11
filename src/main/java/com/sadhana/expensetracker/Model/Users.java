package com.sadhana.expensetracker.Model;

import jakarta.persistence.*;
import com.sadhana.expensetracker.Model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String username;
   private String password;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Roles> roles;
}
