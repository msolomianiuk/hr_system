package ua.netcracker.hr_system.controller;

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

    @RequestMapping(value = "/hr", method = RequestMethod.GET)
    public String devMainPage(Model model) {

        return "hr";
    }
}