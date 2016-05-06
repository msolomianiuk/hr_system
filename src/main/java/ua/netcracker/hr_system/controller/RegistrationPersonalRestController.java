package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.dao.UserDAO;
import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Legion on 27.04.2016.
 */
@RestController
public class RegistrationPersonalRestController {

    private User user;
    @Autowired
    private void setUser(User user) {
        this.user = user;
    }

    private UserDAO userDAO;
    @Autowired
    private void setUserDao (UserDAO userDAO){
        this.userDAO = userDAO;
    }

    private static String sha256Password(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (Exception e) {
//            LOGGER.error(e);
        }
        return null;
    }
    public static Role getRoleByStr(String role) {
        if (Role.ADMIN.toString().equals(role))
            return Role.ADMIN;
        if (Role.STUDENT.toString().equals(role))
            return Role.STUDENT;
        if (Role.HR.toString().equals(role))
            return Role.HR;
        if (Role.BA.toString().equals(role))
            return Role.BA;
        if (Role.DEV.toString().equals(role))
            return Role.DEV;
        return null;
    }

    @RequestMapping(value = "/new_personal", method = RequestMethod.GET)
    public ResponseEntity<User> setNewPersonal(
            @RequestParam String Role_Id,
            @RequestParam String name_peronal,
            @RequestParam String sername_peronal,
            @RequestParam String email_peronal,
            @RequestParam String password_peronal,
            @RequestParam String patronymic_peronal
    ) {


        user.setName(name_peronal);
        user.setSurname(sername_peronal);
        user.setPatronymic(patronymic_peronal);
        user.setPassword(sha256Password(password_peronal));
        user.setEmail(email_peronal);
        user.setRoles( new ArrayList<>(Arrays.asList(getRoleByStr(Role_Id))));

        userDAO.insert(user);


        return ResponseEntity.ok(user);
    }

}