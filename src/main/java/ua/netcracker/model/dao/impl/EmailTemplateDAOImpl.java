package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.EmailTemplateDAO;
import ua.netcracker.model.entity.EmailTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Владимир on 28.04.2016.
 */
@Repository("emailTemplateDAO")
public class EmailTemplateDAOImpl implements EmailTemplateDAO {
    static final Logger LOGGER = Logger.getLogger(EmailTemplateDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    private static final String sqlFindAll = "SELECT * FROM \"hr_system\".letter_template;";
    private static final String sqlFindById = "SELECT * FROM \"hr_system\".letter_template WHERE id = (?);";
    private static final String sqlInsert = "INSERT INTO \"hr_system\".letter_template (description, template) VALUES (?, ?)";
    private static final String sqlUpdate = "UPDATE \"hr_system\".letter_template SET description=?, template=? WHERE id = ?;";
    private static final String sqlDelete = "DELETE FROM \"hr_system\".letter_template WHERE id = ?;";
    private static final String sqlGetDescriptions = "SELECT description FROM \"hr_system\".letter_template;";
    private static final String sqlGetEmailTemplateByDescription = "SELECT * FROM \"hr_system\".letter_template WHERE decription = (?);";


    @Override
    public Collection<EmailTemplate> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlFindAll);
        Collection<EmailTemplate> emailTemplates = new ArrayList<>();
        for (Map row : rows) {
            EmailTemplate emailTemplate = new EmailTemplate();
            emailTemplate.setId((Integer) row.get("id"));
            emailTemplate.setDescription((String) row.get("description"));
            emailTemplate.setTemplate((String) row.get("template"));
            emailTemplates.add(emailTemplate);
        }
        return emailTemplates;
    }

    @Override
    public EmailTemplate find(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        EmailTemplate emailTemplate = jdbcTemplate.queryForObject(sqlFindById, new Object[]{id}, new RowMapper<EmailTemplate>() {
                    @Override
                    public EmailTemplate mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getEmailTemplate(resultSet);
                    }
                }
        );
        return emailTemplate;
    }

    @Override
    public boolean insert(Object entity) {
        return false;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }


    private EmailTemplate getEmailTemplate(ResultSet resultSet) throws SQLException {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(resultSet.getInt("id"));
        emailTemplate.setDescription(resultSet.getString("description"));
        emailTemplate.setTemplate(resultSet.getString("template"));
        return emailTemplate;
    }


    public boolean insert(EmailTemplate emailTemplate) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlInsert, new Object[]{emailTemplate.getDescription(),
                emailTemplate.getTemplate()
        });
        return true;

    }


    public boolean update(EmailTemplate emailTemplate) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlUpdate, new Object[]{emailTemplate.getDescription(),
                emailTemplate.getTemplate(), emailTemplate.getId()
        });
        return true;
    }

    @Override
    public boolean remove(EmailTemplate emailTemplate) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlDelete, new Object[]{emailTemplate.getId()
        });
        return true;
    }

    @Override
    public List<String> getDescriptions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> descriptions = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlGetDescriptions);
        for (Map row : rows) {
            String description = (String) row.get("description");
            descriptions.add(description);
        }
        return descriptions;
    }

    @Override
    public EmailTemplate getEmailTemplateByDescription(String description) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        EmailTemplate emailTemplate = jdbcTemplate.queryForObject(sqlGetEmailTemplateByDescription, new Object[]{description}, new RowMapper<EmailTemplate>() {
                    @Override
                    public EmailTemplate mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getEmailTemplate(resultSet);
                    }
                }
        );
        return emailTemplate;
    }
}
