package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.InterviewResultDAO;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 07.05.2016.
 */
@Repository("InterviewResultDao")
public class InterviewResultDAOImpl implements InterviewResultDAO {
    private static final Logger LOGGER = Logger.getLogger(InterviewResultDAOImpl.class);
    private static final String FIND_MARK = "Select mark, id_interviewer from \"hr_system\".interview_result " +
            "where candidate_id = ";
    private static final String FIND_RECOMMENDATION =
            "Select r.value, i.interviewer_id from \"hr_system\".interview_result i " +
                    "inner join \"hr_system\".recommendation r " +
                    "on i.recommendation_id = r.id where i.candidate_id = ";
    private static final String FIND_COMMENT =
            "Select response from \"hr_system\".interview_result where i.candidate_id = ";


    @Autowired
    private DataSource dataSource;

    @Override
    public Map<Integer, Integer> findMarks(Integer candidateId) {
        Map<Integer, Integer> mark = new HashMap<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_MARK + candidateId);
            for (Map<String, Object> row : rows) {
                mark.put((int) row.get("interviewer_id"), (int) row.get("mark"));
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }

        return mark;
    }

    @Override
    public Map<Integer, String> findRecommendations(Integer candidateId) {
        Map<Integer, String> recommendations = new HashMap<>();

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_RECOMMENDATION + candidateId);
            for (Map<String, Object> row : rows) {
                recommendations.put((int) row.get("interviewer_id"), (String) row.get("value"));
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return recommendations;
    }

    @Override
    public Map<Integer, String> findComments(Integer candidateId) {
        Map<Integer, String> comments = new HashMap<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_COMMENT + candidateId);
            for (Map<String, Object> row : rows) {
                comments.put((int) row.get("interviewer_id"), (String) row.get("value"));
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return comments;
    }


    @Override
    public Collection findAll() {
        //////

        return null;
    }

    @Override
    public Object find(int id) {
        return null;
    }

    @Override
    public boolean insert(Object entity) {
        return false;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }
}
