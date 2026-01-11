package com.sadhana.expensetracker.Service;

import com.sadhana.expensetracker.Model.Roles;
import com.sadhana.expensetracker.Model.SignUpUser;
import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Repo.RolesRepo;
import com.sadhana.expensetracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepo userRepo ;
    private RolesRepo rolesRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserService (UserRepo userRepo, RolesRepo rolesRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<?> save(SignUpUser signUpUser) {
        Users user = new Users();
        user.setUsername(signUpUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(signUpUser.getPassword()));
        user.setRoles(Set.of(rolesRepo.findByName("ROLE_USER")));
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }


    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(userRepo.findAll(),HttpStatus.OK);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
