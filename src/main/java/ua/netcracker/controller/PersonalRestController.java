package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.User;

import java.lang.String;import java.util.HashMap;
import java.util.List;

@RestController
public class PersonalRestController {

    @Autowired
    UserDAO userDao;

    @RequestMapping(value = "/personal_list", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, List<User>>> listAllStudents() {

        HashMap<String, List<User>> personal = new HashMap<String, List<User>>();

        List<User> listHr = userDao.getAllPersonalById(2);
        personal.put("HR", listHr);
        List<User> listBA = userDao.getAllPersonalById(3);
        personal.put("BA", listBA);
        List<User> listDev = userDao.getAllPersonalById(4);
        personal.put("DEV", listDev);

        if (personal.isEmpty()) {
            return new ResponseEntity<HashMap<String, List<User>>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<HashMap<String, List<User>>>(personal, HttpStatus.OK);
    }

}
