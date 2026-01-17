package com.sadhana.expensetracker.Service;

import com.sadhana.expensetracker.DTO.JwtRequest;
import com.sadhana.expensetracker.DTO.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    JwtService  jwtService;

    @Autowired
    MyUserDetailService  myUserDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<JwtResponse> authunticateCurrentUser(JwtRequest  jwtRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
            if(auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails userDetails) {
                return ResponseEntity.ok(new JwtResponse(jwtService.generateToken(userDetails), userDetails.getUsername()));
            }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
