package ua.netcracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.entity.Role;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class RoleRestController {

    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> listAllRoles() {
        Collection<String> strings = new ArrayList<>();

        Role[] roles = Role.values();

        for (Role role : roles) {
            strings.add(role.name());
        }

        if (strings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
