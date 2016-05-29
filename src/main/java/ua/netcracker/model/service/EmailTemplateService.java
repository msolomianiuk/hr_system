package ua.netcracker.model.service;


import ua.netcracker.model.entity.EmailTemplate;

import java.util.Collection;

/**
 * Created by Владимир on 06.05.2016.
 */
public interface EmailTemplateService {
    Collection<EmailTemplate> getAllEmailTemplates();

    boolean updateEmailTemplate(EmailTemplate emailTemplate);
}
