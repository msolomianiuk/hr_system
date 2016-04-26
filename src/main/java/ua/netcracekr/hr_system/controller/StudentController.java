package ua.netcracekr.hr_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class StudentController {
    /**
     * This method return String param for page web-interface "Student"
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String mainPage(Model model) {
        return "student";
    }
}
