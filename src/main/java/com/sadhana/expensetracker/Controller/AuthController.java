package com.sadhana.expensetracker.Controller;


import com.sadhana.expensetracker.DTO.JwtRequest;
import com.sadhana.expensetracker.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/me")
    public ResponseEntity<?> currentUser(@RequestBody JwtRequest jwtRequest) {
        return authService.authunticateCurrentUser(jwtRequest);
    }

}
