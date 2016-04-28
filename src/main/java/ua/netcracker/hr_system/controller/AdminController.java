package ua.netcracker.hr_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for processing requests related to administrator
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String mainPage(Model model) {
        return "admin";
    }
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String mainPageHome(Model model) {
        return "admin";
    }
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String mainPageStudentsList(Model model) {

        return "admin";
    }
    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public String mainPagePersonalList(Model model) {

        return "admin";
    }
    @RequestMapping(value = "/interview_scheduler", method = RequestMethod.GET)
    public String mainPageInterviewSheduler(Model model) {

        return "admin";
    }
}
