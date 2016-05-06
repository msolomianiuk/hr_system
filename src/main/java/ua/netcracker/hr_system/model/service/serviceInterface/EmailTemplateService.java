package ua.netcracker.hr_system.model.service.serviceInterface;

import java.util.Collection;

/**
 * Created by Владимир on 06.05.2016.
 */
public interface EmailTemplateService {
    Collection<EmailTemplate> getAllEmailTemplates();
    boolean manageEmailTemplate(EmailTemplate emailTemplate, String status);
}
