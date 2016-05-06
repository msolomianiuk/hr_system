package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.entity.EmailTemplate;
import ua.netcracker.hr_system.model.service.EmailTemplateService;

import java.util.Collection;

/**
* Created by Владимир on 29.04.2016.
*/
@RestController
public class AdminTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @RequestMapping(value = "/service/getEmailTemplates", method = RequestMethod.GET)
    public ResponseEntity<Collection<EmailTemplate>> getAllEmailTemplates() {
        Collection<EmailTemplate> emailTemplates = emailTemplateService.getAllEmailTemplates();
        if (emailTemplates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(emailTemplates, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/setEmailTemplates", method = RequestMethod.GET)
    public ResponseEntity<EmailTemplate> setAllEmailTemplates(@RequestParam String id,
                                                              @RequestParam String description,
                                                              @RequestParam String template,
                                                              @RequestParam String status) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setTemplate(template);
        emailTemplate.setDescription(description);
        emailTemplate.setId(Integer.valueOf(id));
        if(emailTemplateService.manageEmailTemplate(emailTemplate,status)){

                return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
