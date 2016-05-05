package ua.netcracker.hr_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class CandidateController {
    private String webRootPath = "/static/images/";
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String getProfilePage(Model model) {
        return "student";
    }

   /* @RequestMapping(value = "/student1", method = RequestMethod.POST)
    public String addImageFromForm(Candidate candidate, BindingResult bindingResult,
                                   @RequestParam(value = "image", required = false)
                                   MultipartFile image){
        if(bindingResult.hasErrors()){
            return "student";
        }
        try{
            if(!image.isEmpty()) {
                try {
                    File file = new File(webRootPath + candidate.getId());
                    FileUtils.writeByteArrayToFile(file, image.getBytes());
                } catch (IOException e) {
                    throw new IOException("Unable to save image", e);
                }
            }
        }catch (IOException e){
            bindingResult.reject(e.getMessage());
            return "student";
        }
        return "student";
    }*/
}