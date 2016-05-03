package ua.netcracker.hr_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for display errors
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class ErrorController {
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String login(Model model) {
        return "error";
    }
}
