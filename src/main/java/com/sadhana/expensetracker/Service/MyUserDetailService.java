package com.sadhana.expensetracker.Service;

import com.sadhana.expensetracker.Model.UserPrincipal;
import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if(user == null){
            System.out.println("Username not found");
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }
}
