package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.service.date.DateService;

/**
 * Created by Legion on 11.05.2016.
 */
@Controller
public class RegistrationPeriod {

    @Autowired
    private DateService dateService;

    @RequestMapping(value = "/registration_period", method = RequestMethod.GET)
    public ResponseEntity<Integer> period() {
        int s = dateService.registrationPeriod();

        return new ResponseEntity(s, HttpStatus.OK);

    }


}