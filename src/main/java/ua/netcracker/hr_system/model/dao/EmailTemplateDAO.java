package ua.netcracker.hr_system.model.dao;

import ua.netcracker.hr_system.model.entity.EmailTemplate;

import java.util.Collection;

/**
 * Created by Владимир on 28.04.2016.
 */
public interface EmailTemplateDAO extends DAO {
    boolean insert(EmailTemplate emailTemplate);

    boolean remove(EmailTemplate emailTemplate);

    Collection<String> getDescriptions();

    EmailTemplate getEmailTemplateByDescription(String description);

}
