package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.dao.impl.UserDAOImpl;
import ua.netcracker.model.entity.User;

import java.util.List;

/**
 * Created by Legion on 27.04.2016.
 */
@RestController
public class GetPersonallByIdRestController {


    //ЮЗАЙТЕ СЕРВІС!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    //|
    //|
    @Autowired
    private UserDAOImpl userDao;

    @RequestMapping(value = "/service/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(@RequestParam int id) {
        List<User> users=userDao.getAllPersonalById(id);
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

}
