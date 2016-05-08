package ua.netcracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for processing requests related to hr
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class HRController {

    @RequestMapping(value = "hr/students_list", method = RequestMethod.GET)
    public String hrMainPage() {
        return "studentsList";
    }

    @RequestMapping(value = "hr/interview_page", method = RequestMethod.GET)
    public String hrInterviewPage() {
        return "interviewPage";
    }
}