package ua.netcracker.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.netcracker.model.securiry.UserAuthenticationDetails;
import ua.netcracker.model.utils.RolesUtils;

/**
 * Class for processing requests to main page
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class MainController {

    /**
     * If the user is anonymous - displays the home page,
     * else - redirects the user to the him section
     *
     * @return name of jsp file with view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserAuthenticationDetails userDetails =
                    (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String url = RolesUtils.getUrlByRoles(userDetails.getAuthorities());
            return "redirect:" + url;
        }
        return "index";
    }

}
