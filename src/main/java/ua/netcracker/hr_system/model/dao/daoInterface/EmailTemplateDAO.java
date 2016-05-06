package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.adminconfiguration.EmailTemplate;

import java.util.Collection;

/**
 * Created by Владимир on 28.04.2016.
 */
public interface EmailTemplateDAO extends DAO<Integer,EmailTemplate> {
    Collection<String> getDescriptions();

    EmailTemplate getEmailTemplateByDescription(String description);

}
