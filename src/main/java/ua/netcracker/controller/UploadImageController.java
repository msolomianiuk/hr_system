package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.netcracker.model.service.UserService;

/**
 * Created on 23:01 09.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
@Controller
public class UploadImageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/upload/photo", method = RequestMethod.POST)
    public String addPersonFromForm(Model model, @RequestParam(value = "inputImage", required = false) MultipartFile image) {
        if (!image.isEmpty() && image.getContentType().equals("image/jpeg")) {
            userService.saveUserPhoto(image);
        } else {
            model.addAttribute("uploadPhotoError");
        }
        return "redirect:/";
    }

}
