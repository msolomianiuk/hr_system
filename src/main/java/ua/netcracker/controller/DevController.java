package ua.netcracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DevController {

//  Main page
    @RequestMapping(value = "dev/interview_page", method = RequestMethod.GET)
    public String devMainPage() {
        return "interviewPage";
    }
}