package ua.netcracker.hr_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for processing requests related to students
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class StudentController {

    @RequestMapping(value = "/student",method = RequestMethod.GET)
    public String mainPage(Model model){

        return "student";
    }
}
