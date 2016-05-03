package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.entity.User;
import ua.netcracker.hr_system.model.service.serviceImpl.CustomUserDetailsServiceImpl;

import java.util.ArrayList;

/**
 * Created by Legion on 27.04.2016.
 */
@RestController
public class RegistrationPersonalRestController {

    private User user;

    private void setUser(User user) {
        this.user = user;
    }

//    private UserDaoNew userDaoNew;
//
//    @Autowired
//    private void setUserDao (UserDaoNew userDaoNew){
//        this.userDaoNew = userDaoNew;
//    }
    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;


    @Autowired
    private void setUserDetailsService(CustomUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    @RequestMapping(value = "/new_personal", method = RequestMethod.GET)
    public ResponseEntity<User> setNewPersonal(
            @RequestParam String idRole,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String patronymic
    ) {

        ArrayList role = new ArrayList();
        role.add(idRole);

        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(role);

//                userDaoNew.insert(user);

        return ResponseEntity.ok(user);
    }

}
