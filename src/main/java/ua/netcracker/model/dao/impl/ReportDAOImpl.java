package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.ReportDAO;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Владимир on 26.05.2016.
 */
@Repository("reportDao")
public class ReportDAOImpl implements ReportDAO {

    static final Logger LOGGER = Logger.getLogger(ReportDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    private static final String SQL_MAIN_REPORT = "SELECT u.email, u.name, u.surname, u.patronymic, q.caption as question, ca.value as answer, s.value as status " +
            "FROM \"hr_system\".users u " +
            "INNER JOIN \"hr_system\".candidate c ON u.id = c.user_id " +
            "INNER JOIN \"hr_system\".candidate_answer ca ON c.id = ca.candidate_id " +
            "INNER JOIN \"hr_system\".question q ON ca.question_id = q.id " +
            "INNER JOIN \"hr_system\".status s ON s.id = c.status_id " +
            "WHERE c.course_id = ";

    @Override
    public Collection<Collection<String>> getReport(String sql) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            Collection<Collection<String>> report = new ArrayList<>();
            report.add(rows.get(0).keySet());
            for (Map row : rows) {
                report.add(row.values());
            }
            return report;
        } catch (DataAccessException ex) {
            LOGGER.trace("User request ", ex);
            return new ArrayList<>();
        } catch (Exception ex) {
            LOGGER.error("User request ", ex);
            Collection<Collection<String>> defaultReport = new ArrayList<>();
            Collection<String> defaultRow = new ArrayList<String>() {{
                add("empty");
            }};
            defaultReport.add(defaultRow);
            return defaultReport;
        }
    }

    @Override
    public Collection<Collection<String>> getStudentsByCourseId(int courseId) {
        return getReport(SQL_MAIN_REPORT + courseId + ";");
    }

    @Override
    public Collection<Collection<String>> getStudentsByCourseIdAndStatusId(int courseId, int statusId) {
        return getReport(SQL_MAIN_REPORT + courseId + " AND c.status_id = " + statusId + ";");
    }
}
