package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.InterviewResultDAO;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.InterviewResult;
import ua.netcracker.model.entity.Recommendation;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by Alex on 07.05.2016.
 * Changed by Alyona on 08.05.2016.
 */
@Repository("InterviewResultDao")
public class InterviewResultDAOImpl implements InterviewResultDAO {

    @Autowired
    private UserDAO userDAO;
    private static final Logger LOGGER = Logger.getLogger(InterviewResultDAOImpl.class);
    private static final String FIND_MARK = "Select mark, interviewer_id from \"hr_system\".interview_result " +
            "where candidate_id =?";
    private static final String FIND_RECOMMENDATION =
            "Select r.value, i.interviewer_id from \"hr_system\".interview_result i " +
                    "inner join \"hr_system\".recommendation r " +
                    "on i.recommendation_id = r.id where i.candidate_id =?";
    private static final String FIND_COMMENT =
            "Select response from \"hr_system\".interview_result where i.candidate_id = ?";
    private static final String FIND_ALL = "Select ir.interviewer_id, ir.mark, ir.comment, r.value from \"hr_system\".interview_result ir " +
            "inner join \"hr_system\".recommendation r on ir.recommendation_id = r.id where ir.candidate_id = ?";
    private static final String CREATE = "Insert into \"hr_system\".interview_result " +
            "(interviewer_id, candidate_id, mark, comment, recommendation_id) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE \"hr_system\".interview_result SET " +
            "(mark, comment, recommendation_id)=(?,?,?) " +
            "WHERE interviewer_id = ? AND candidate_id = ?";

    @Override
    public Collection<InterviewResult> findResultsByCandidateId(Integer candidateId) {
        Collection<InterviewResult> results = new ArrayList<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL, candidateId);
            for (Map<String, Object> row : rows) {
                InterviewResult interviewResult = new InterviewResult();
                interviewResult.setInterviewerId((int) row.get("interviewer_id"));
                interviewResult.setMark((int) row.get("mark"));
                interviewResult.setComment((String) row.get("comment"));
                interviewResult.setRecommendation(Recommendation.valueOf((String) row.get("value")));
                interviewResult.setInterviewer(userDAO.find((int) row.get("interviewer_id")));
                results.add(interviewResult);
            }

        } catch (DataAccessException e) {
            LOGGER.error("Method: findResultsByCandidateId" + " Error: " + e);
        }
        return results;
    }


    @Autowired
    private DataSource dataSource;

    @Override
    public Map<Integer, Integer> findMarks(Integer candidateId) {
        Map<Integer, Integer> marks = new HashMap<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_MARK, candidateId);
            for (Map<String, Object> row : rows) {
                marks.put((int) row.get("interviewer_id"), (int) row.get("mark"));
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findMarks" + " Error: " + e);
        }
        return marks;
    }

    @Override
    public Map<Integer, String> findRecommendations(Integer candidateId) {
        Map<Integer, String> recommendations = new HashMap<>();

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_RECOMMENDATION, candidateId);
            for (Map<String, Object> row : rows) {
                recommendations.put((int) row.get("interviewer_id"), (String) row.get("value"));
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findRecommendations" + " Error: " + e);
        }
        return recommendations;
    }

    @Override
    public Map<Integer, String> findComments(Integer candidateId) {
        Map<Integer, String> comments = new HashMap<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_COMMENT, candidateId);
            for (Map<String, Object> row : rows) {
                comments.put((int) row.get("interviewer_id"), (String) row.get("value"));
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findComments" + " Error: " + e);
        }
        return comments;
    }

    @Override
    public boolean createInterviewResult(Integer candidate_id, InterviewResult interviewResult) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(CREATE, interviewResult.getInterviewerId(), candidate_id,
                    interviewResult.getMark(), interviewResult.getComment(),
                    interviewResult.getRecommendation().getId());
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: createInterviewResult" + " Error: " + e);
        }
        return false;
    }

    @Override
    public boolean updateInterviewResult(Integer candidate_id, InterviewResult interviewResult) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE, interviewResult.getMark(), interviewResult.getComment(),
                    interviewResult.getRecommendation().getId(),
                    interviewResult.getInterviewerId(), candidate_id);
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: updateInterviewResult" + " Error: " + e);
        }
        return false;
    }


    @Override
    public Collection findAll() {

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
