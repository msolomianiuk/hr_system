package ua.netcracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HRController {

//  Main page
    @RequestMapping(value = "hr/interview_page", method = RequestMethod.GET)
    public String hrInterviewPage() {
        return "interviewPage";
    }
}