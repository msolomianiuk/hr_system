package ua.netcracker.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.RegistrationService;
import ua.netcracker.model.service.date.DateService;

import java.time.LocalDate;

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

    /**
     * Check whether a user already exists with that Email
     *
     * @param email user email
     * @return true - email is free, false - email busy
     */
    @RequestMapping(value = "/check_email", method = RequestMethod.POST, headers = "Accept=application/json")
    public
    @ResponseBody
    String checkEmail(@RequestBody String email, Model model) {
        Gson gson = new Gson();
        return gson.toJson(registrationService.isFreeEmail(email));
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String register(ModelMap model,
                           @RequestParam(value = "form-email") String email,
                           @RequestParam(value = "form-name") String name,
                           @RequestParam(value = "form-surname") String surname,
                           @RequestParam(value = "form-patronymic") String patronymic,
                           @RequestParam(value = "form-password") String password) {

        if (registrationService.registrationStudent(email, name, surname, patronymic, password))
            return "redirect:/registration?success";
        return "redirect:/registration?error";
    }
}
