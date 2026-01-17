package com.sadhana.expensetracker.Controller;

import com.sadhana.expensetracker.Model.SignUpUser;
import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpUser signUpUser) {
        System.out.println("signup request by"+signUpUser.getUsername());
        return userService.save(signUpUser);
    }
}
