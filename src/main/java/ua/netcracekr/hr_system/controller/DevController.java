package ua.netcracekr.hr_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for processing requests related to dev
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class DevController {

    @RequestMapping(value = "/dev", method = RequestMethod.GET)
    public String devMainPage(Model model) {

        return "dev";
    }
}