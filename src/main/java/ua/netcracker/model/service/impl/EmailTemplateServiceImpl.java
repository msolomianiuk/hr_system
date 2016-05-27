package ua.netcracker.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.EmailTemplateDAO;
import ua.netcracker.model.entity.EmailTemplate;
import ua.netcracker.model.service.EmailTemplateService;

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
    public boolean updateEmailTemplate(EmailTemplate emailTemplate) {
        return emailTemplateDao.update(emailTemplate);
    }
}
