package com.sadhana.expensetracker.Service;

import com.sadhana.expensetracker.Model.Roles;
import com.sadhana.expensetracker.Model.Users;
import com.sadhana.expensetracker.Repo.RolesRepo;
import com.sadhana.expensetracker.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RolesService {

    private UserRepo userRepo;
    private RolesRepo rolesRepo;
    private SecurityService securityService;

    @Autowired
    public RolesService(RolesRepo rolesRepo, UserRepo userRepo,SecurityService securityService) {
        this.rolesRepo = rolesRepo;
        this.userRepo = userRepo;
        this.securityService = securityService;
    }


    public ResponseEntity<List<Roles>> getAllAvailableRoles() {
        return new ResponseEntity<>(rolesRepo.findAll(), HttpStatus.OK);
    }

    public String addRoleToUser(Long userid, Long roleid) {
        Optional<Users> user = userRepo.findById(userid);
        Optional<Roles> role = rolesRepo.findById(roleid);
        if(user.isPresent() && role.isPresent()) {
            Set<Roles> current_user_roles = user.get().getRoles();
            current_user_roles.add(role.get());
            user.get().setRoles(current_user_roles);
            userRepo.save(user.get());
            return "Role Added Successfully";
        }else  {
            return "Role Not Found";
        }
    }
}
