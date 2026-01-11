package com.sadhana.expensetracker.Service;

import com.sadhana.expensetracker.Model.UserPrincipal;
import com.sadhana.expensetracker.Model.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public Users getCurrentUser() {

        Object principal =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        if (principal instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getUser();
        }

        throw new RuntimeException("User not authenticated");
    }
}
