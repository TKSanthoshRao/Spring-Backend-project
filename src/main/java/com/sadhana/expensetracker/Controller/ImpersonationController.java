package com.sadhana.expensetracker.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RestController("/login/impersonate")
@CrossOrigin("http://localhost:5173/")
public class ImpersonationController {
}
