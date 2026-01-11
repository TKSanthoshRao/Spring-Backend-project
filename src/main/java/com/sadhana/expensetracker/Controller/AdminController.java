package com.sadhana.expensetracker.Controller;


import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/user/{userid}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userid) {
        System.out.println("delete request for "+userid);
        userService.deleteUser(userid);
        return new ResponseEntity<>("deleted user Successfully", HttpStatus.OK);
    }

//    @GetMapping("/role")
//    public ResponseEntity<List<Users>> getAllRoles() {
//
//    }
}
