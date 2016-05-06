package ua.netcracker.hr_system.model.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.daoInterface.EmailTemplateDAO;
import ua.netcracker.hr_system.model.entity.adminconfiguration.EmailTemplate;
import ua.netcracker.hr_system.model.service.serviceInterface.EmailTemplateService;

import java.util.Collection;

/**
 * Created by Владимир on 05.05.2016.
 */
@Service()
public class EmailTemplateServiceImpl implements EmailTemplateService {
    @Autowired
    private EmailTemplateDAO emailTemplateDao;

    @Override
    public Collection<EmailTemplate> getAllEmailTemplates() {
        return emailTemplateDao.findAll();
    }

    @Override
    public boolean manageEmailTemplate(EmailTemplate emailTemplate, String status) {
        switch (status) {
            case "delete":
                emailTemplateDao.remove(emailTemplate);
                break;
            case "insert":
                emailTemplateDao.insert(emailTemplate);
                break;
            case "update":
                emailTemplateDao.update(emailTemplate);
                break;
            default:
                return false;
        }
        return true;
    }
}
