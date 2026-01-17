package com.sadhana.expensetracker.Controller;


import com.sadhana.expensetracker.Model.Roles;
import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Repo.RolesRepo;
import com.sadhana.expensetracker.Service.RolesService;
import com.sadhana.expensetracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:5173/")
public class AdminController {


    private UserService userService;
    private RolesService rolesService;
    @Autowired
    public AdminController(UserService  userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }


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

    @GetMapping("/roles")
    public ResponseEntity<List<Roles>> getAllRoles() {
        return rolesService.getAllAvailableRoles();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("role/user/{userid}/role/{roleid}")
    public ResponseEntity<String> addRole(@PathVariable Long userid, @PathVariable Long roleid) {
        System.out.println("add role request for "+userid+" "+roleid);
        return ResponseEntity.ok(rolesService.addRoleToUser(userid, roleid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("role/user/{userid}/role/{roleid}")
    public ResponseEntity<String> deleteRole(@PathVariable Long userid, @PathVariable Long roleid) {
        System.out.println("delete role request for "+userid+" "+roleid);
        return rolesService.deleteRoleForUser(userid,roleid);
    }
}
