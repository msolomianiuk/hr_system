package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.AnswersDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Question;

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
    private static final String FIND_ALL_BY_ID = "SELECT * FROM \"hr_system\".candidate_answer WHERE candidate_id =?";
    private static final String FIND_ALL_BY_QUESTION_ID =
            "SELECT * FROM \"hr_system\".candidate_answer WHERE candidate_id = ? AND question_id = ?";
    private static final String INSERT =
            "INSERT INTO \"hr_system\".candidate_answer(candidate_id, question_id, value) VALUES(?,?,?)";
    private static final String DELETE_ALL = "DELETE FROM \"hr_system\".candidate_answer WHERE candidate_id =?";
    private static final String UPDATE = "UPDATE \"hr_system\".candidate_answer SET value=? WHERE question_id =? AND " +
            " candidate_id = ?";
    private static final String SELECT_ANSWER = "SELECT candidate_id, question_id, caption ,value FROM " +
            "\"hr_system\".candidate_answer join \"hr_system\".question on question_id = id where candidate_id = ";


    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<Answer> findAll(int candidateId) {
        Collection<Answer> answers = new ArrayList<>();
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_BY_ID, candidateId);
            for (Map<String, Object> row : rows) {
                Answer answer = createAnswer(row);
                answers.add(answer);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findAll" + " Error: " + e);
        }
        return answers;

    }

    @Override
    public Collection<Answer> findAllIsView(Candidate candidate, Collection<Question> listQuestions) {
        Collection<Answer> answers = new ArrayList<>();
        if (candidate.getAnswers() != null) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                for (Question question : listQuestions) {
                    Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_BY_QUESTION_ID,
                            candidate.getId(), question.getId());
                    for (Map<String, Object> row : rows) {
                        Answer answer = createAnswer(row);
                        answers.add(answer);
                    }
                }
            } catch (DataAccessException e) {
                LOGGER.error("Method: findAllIsView" + " Error: " + e);
            }
        }
        return answers;
    }

    private Answer createAnswer(Map<String, Object> row) {
        Answer answer = new Answer();
        if (row.get("value") != null) {
            answer.setQuestionId((int) row.get("question_id"));
            answer.setValue((String) row.get("value"));
        } else {
            answer.setValue(null);
        }
        return answer;
    }


    @Override
    public void saveAll(Candidate candidate) {
        try {
            for (Answer answer : candidate.getAnswers()) {
                executeSaveAnswer(candidate.getId(), answer);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: saveAll" + " Error: " + e);
        }
    }

    @Override
    public void deleteAnswers(int candidateId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(DELETE_ALL, candidateId);
        } catch (DataAccessException e) {
            LOGGER.error("Method: deleteAnswers" + " Error: " + e);
        }

    }


    private void executeSaveAnswer(int candidateId, Answer answer) {

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(INSERT, candidateId, answer.getQuestionId(), answer.getValue());
        } catch (DataAccessException e) {
            LOGGER.error("Method: executeSaveAnswer" + " Error: " + e);
        }
    }

    @Override
    public boolean update(Candidate candidate) {
        try {
            Collection<Answer> answers = findAll(candidate.getId());
            if (answers.size() == 0) {
                saveAll(candidate);
            } else {

                deleteAnswers(candidate.getId());
                answers = candidate.getAnswers();
                for (Answer answer : answers) {
                    executeSaveAnswer(candidate.getId(), answer);
                }
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: update" + " Error: " + e);
        }
        return false;
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


}
