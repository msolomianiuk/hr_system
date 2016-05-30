package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.RegistrationService;

/**
 * Class for processing registration requests
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.1
 */
@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registerPage(ModelMap model,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "success", required = false) String success) {

        if (error != null) {
            model.addAttribute("error", "Registration error");
        } else if (success != null) {
            model.addAttribute("success", "You have successfully signed up ...");
        } else {
            User user = new User();
            model.addAttribute("user", user);
        }
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String register(@RequestParam(value = "form-email") String email,
                           @RequestParam(value = "form-name") String name,
                           @RequestParam(value = "form-surname") String surname,
                           @RequestParam(value = "form-patronymic") String patronymic,
                           @RequestParam(value = "form-password") String password) {

        if (registrationService.registrationStudent(email, name, surname, patronymic, password))
            return "redirect:/registration?success";
        return "redirect:/registration?error";
    }
}
