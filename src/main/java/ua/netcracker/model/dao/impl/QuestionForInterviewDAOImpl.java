package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.QuestionForInterviewDAO;
import ua.netcracker.model.entity.QuestionForInterview;
import ua.netcracker.model.entity.SubjectQuestionForInterview;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by bohda on 09.05.2016.
 */
@Repository("questionDaoForInterview")
public class QuestionForInterviewDAOImpl implements QuestionForInterviewDAO {


    private static final Logger LOGGER = Logger.getLogger(QuestionForInterviewDAO.class);

    private static final String SELECT_QUESTION = "SELECT * FROM \"hr_system\".interview_question WHERE id = ?";
    private static final String SELECT_ALL_SUBJECT = "SELECT id,name FROM \"hr_system\".subject";
    private static final String SELECT_ALL_SUBJECT_QUESTION = "SELECT * FROM \"hr_system\".subject";
    private static final String SELECT_ALL_BY_SUBJECT = "SELECT id,question FROM \"hr_system\".interview_question WHERE subject_id = ?";
    private static final String UPDATE_SUBJECT = "UPDATE \"hr_system\".subject SET (role_id,name)=(?,?) WHERE id =?";
    private static final String UPDATE_QUESTION = "UPDATE \"hr_system\".interview_question SET (subject_id,question)=(?,?) WHERE id =?";
    private static final String DELETE_SUBJECT = "DELETE FROM \"hr_system\".subject WHERE id=?";
    private static final String DELETE_QUESTION = "DELETE FROM \"hr_system\".interview_question WHERE id=?";
    private static final String DELETE_QUESTION_BY_SUBJECT = "DELETE FROM \"hr_system\".interview_question WHERE subject_id=?";

    @Autowired
    private DataSource dataSource;


    @Override
    public QuestionForInterview find(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            QuestionForInterview questionForInterview = jdbcTemplate.queryForObject(SELECT_QUESTION, new Object[]{id}, new RowMapper<QuestionForInterview>() {
                        @Override
                        public QuestionForInterview mapRow(ResultSet resultSet, int i) throws SQLException {
                            QuestionForInterview question = new QuestionForInterview();
                            question.setSubjectId(resultSet.getInt("subject_id"));
                            question.setId(resultSet.getInt("id"));
                            question.setValue(resultSet.getString("question"));
                            return question;
                        }
                    }
            );
            return questionForInterview;
        } catch (Exception e) {
            LOGGER.error("Error :" + e);
        }
        return null;
    }

    @Override

    public boolean insert(QuestionForInterview questionForInterview) {
        try {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                    .withTableName("\"hr_system\".interview_question")
                    .usingColumns("subject_id", "question");
            MapSqlParameterSource insertParameter = new MapSqlParameterSource();
            insertParameter.addValue("subject_id", questionForInterview.getSubjectId());
            insertParameter.addValue("question", questionForInterview.getValue());
            simpleJdbcInsert.execute(insertParameter);
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return false;
    }

    @Override
    public boolean update(QuestionForInterview questionForInterview) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE_QUESTION, questionForInterview.getSubjectId(), questionForInterview.getValue(), questionForInterview.getId() );
            return true;
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return false;
    }


    @Override
    public Collection findAllQuestionBySubject(int subjectId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<QuestionForInterview> questions = new ArrayList<QuestionForInterview>();
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ALL_BY_SUBJECT, subjectId);
            for (Map row : rows) {
                QuestionForInterview question = new QuestionForInterview();
                question.setId((int) row.get("id"));
                question.setValue(row.get("question").toString());
                questions.add(question);
            }
            return questions;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public Collection findAll() {
        try {
            List<SubjectQuestionForInterview> subjectAll = new ArrayList<SubjectQuestionForInterview>();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ALL_SUBJECT_QUESTION);
            for (Map row : rows) {
                SubjectQuestionForInterview subjects = new SubjectQuestionForInterview();
                subjects.setId((int) row.get("id"));
                subjects.setValue(row.get("name").toString());
                subjects.setRoleId((int) row.get("role_id"));
                subjects.setQuestionForInterviews(findAllQuestionBySubject((int) row.get("id")));
                subjectAll.add(subjects);
            }
            return subjectAll;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public Collection findAllSubject() {
        try {
            List<SubjectQuestionForInterview> subjectAll = new ArrayList<SubjectQuestionForInterview>();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ALL_SUBJECT);
            for (Map row : rows) {
                SubjectQuestionForInterview subjects = new SubjectQuestionForInterview();
                subjects.setId((int) row.get("id"));
                subjects.setValue(row.get("name").toString());
                subjectAll.add(subjects);
            }
            return subjectAll;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public boolean insertSubject(SubjectQuestionForInterview subject) {
        try {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                    .withTableName("\"hr_system\".subject")
                    .usingColumns("role_id", "name");
            MapSqlParameterSource insertParameter = new MapSqlParameterSource();
            insertParameter.addValue("role_id", subject.getRoleId());
            insertParameter.addValue("name", subject.getValue());
            simpleJdbcInsert.execute(insertParameter);
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return false;
    }

    @Override
    public boolean deleteSubject(SubjectQuestionForInterview subject) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            for (Object question : findAllQuestionBySubject(subject.getId())) {
                jdbcTemplate.update(DELETE_QUESTION_BY_SUBJECT, subject.getId());
            }
            jdbcTemplate.update(DELETE_SUBJECT, subject.getId());
            return true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean delete(Integer questionId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(DELETE_QUESTION, questionId);
            return true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean updateSubject(SubjectQuestionForInterview subject) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE_SUBJECT, subject.getRoleId(), subject.getValue());
            return true;
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return false;
    }


}
