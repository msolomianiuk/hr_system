package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.AnswersDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 06.05.2016.
 */
@Repository("answersDAO")
public class AnswersDAOImpl implements AnswersDAO {
    private static final Logger LOGGER = Logger.getLogger(AnswersDAOImpl.class);
    private static final String FIND_ALL_BY_ID = "SELECT * FROM \"hr_system\".candidate_answer WHERE candidate_id =";
    private static final String INSERT =
            "INSERT INTO \"hr_system\".candidate_answer(candidate_id, question_id, value) VALUES(?,?,?)";
    private static final String DELETE = "DELETE FROM \"hr_system\".candidate_answer WHERE candidate_id = ";
    private static final String UPDATE = "UPDATE \"hr_system\".candidate_answer SET value=? WHERE question_id =? AND " +
            " candidate_id = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<Answer> findAll(int candidateId) {
        Collection<Answer> answers = new ArrayList<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_BY_ID + candidateId);
            for (Map<String, Object> row : rows) {
                Answer answer = new Answer();
                answer.setQuestionId((int) row.get("question_id"));
                answer.setValue((String) row.get("value"));
                answers.add(answer);
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return answers;

    }


    @Override
    public void saveAll(Candidate candidate) {
        try {
            for (Answer answer : candidate.getAnswers()) {
                executeSaveAnswer(candidate.getId(), answer);
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
    }

    @Override
    public void deleteAnswers(int candidateId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(DELETE + candidateId);
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }

    }

    private void executeSaveAnswer(int candidateId, Answer answer) {

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(INSERT, candidateId, answer.getQuestionId(), answer.getValue());
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
    }


    @Override
    public Collection<Candidate> findAll() {
        return null;
    }

    @Override
    public Candidate find(int id) {
        return null;
    }

    @Override
    public boolean insert(Candidate entity) {
        return false;
    }

    @Override
    public boolean update(Candidate candidate) {
        try {
            Collection<Answer> answers = findAll(candidate.getId());
            if (answers.size() == 0) {
                saveAll(candidate);
            } else {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                answers = candidate.getAnswers();
                for (Answer answer : answers) {
                    jdbcTemplate.update(UPDATE, answer.getValue(), answer.getQuestionId(), candidate.getId());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return false;
    }
}
