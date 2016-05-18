package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import ua.netcracker.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 23:01 09.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
@Controller
public class UploadImageController implements HandlerExceptionResolver {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/upload/photo", method = RequestMethod.POST)
    public String uploadPhoto(Model model, @RequestParam(value = "inputImage") MultipartFile image,
                              @RequestParam(value = "x") int x,
                              @RequestParam(value = "y") int y,
                              @RequestParam(value = "width") int width,
                              @RequestParam(value = "height") int height) throws Exception {
        if (!image.isEmpty() && image.getContentType().equals("image/jpeg")) {
            userService.saveUserPhoto(image,x,y,width,height);
        } else {
            model.addAttribute("uploadPhotoError");
        }
        return "redirect:/";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception exception) {
        Map<Object, Object> model = new HashMap<Object, Object>();
        model.put("error", "Sorry, file too large!");
        return new ModelAndView("/error", (Map) model);
        /*if (exception instanceof MaxUploadSizeExceededException) {
            model.put("error", "File size should be less then " +
                    ((MaxUploadSizeExceededException) exception).getMaxUploadSize() + " byte.");
        } else {

        }*/

    }

}

