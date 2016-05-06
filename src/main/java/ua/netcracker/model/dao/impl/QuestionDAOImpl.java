package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.QuestionDAO;
import ua.netcracker.model.entity.Question;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Repository("questionDao")
public class QuestionDAOImpl implements QuestionDAO {

    private static final Logger LOGGER = Logger.getLogger(QuestionDAOImpl.class);

    private static final String SELECT_ALL = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "Inner join \"hr_system\".type t ON q.type_id = t.id " +
            "Order by qcp.order_number";;

    private static final String SELECT_ALL_MANDATORY = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
            "WHERE q.is_mandatory = true " +
            "Order by qcp.order_number";

    private static final String SELECT_TYPE_ID = "Select id from \"hr_system\".type WHERE value = '";

    private static final String FIND_BY_ID = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "Inner join \"hr_system\".type t ON q.type_id = t.id " +
            "WHERE q.id = ";

    private static final String UPDATE_QUESTION = "UPDATE \"hr_system\".question SET caption = ?, type_id = ?, is_mandatory = ? " +
            "WHERE id = ?;";

    private static final String UPDATE_QUESTION_COURSE_MAPS = "UPDATE \"hr_system\".question_course_maps SET course_id = ?, " +
            "order_number = ?, Where question_id = ?;";

    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<Question> findQuestions(String sql) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Question> questions = new ArrayList<Question>();
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map row : rows) {
                Question question = new Question();
                question.setOrderNumber((int) row.get("order_number"));
                question.setId((int) row.get("id"));
                question.setCaption(row.get("caption").toString());
                question.setAnswerVariants(findAnswerVariants(question));
                question.setMandatory((boolean) row.get("is_mandatory"));
                question.setCourseID((int) row.get("course_id"));
                question.setType(row.get("value").toString());
                questions.add(question);
            }
            return questions;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public Collection<Question> findAll() {
        return findQuestions(SELECT_ALL);
    }

    public Collection<Question> findAllMandatory() {
        return findQuestions(SELECT_ALL_MANDATORY);
    }


    public Question find(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            Question question = jdbcTemplate.queryForObject(FIND_BY_ID + id, new RowMapper<Question>() {
                        @Override
                        public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                            Question question = new Question();
                            question.setOrderNumber(resultSet.getInt("order_number"));
                            question.setId(resultSet.getInt("id"));
                            question.setCaption(resultSet.getString("caption"));
                            question.setAnswerVariants(findAnswerVariants(question));
                            question.setMandatory(resultSet.getBoolean("is_mandatory"));
                            question.setCourseID(resultSet.getInt("course_id"));
                            question.setType(resultSet.getString("value"));
                            return question;
                        }
                    }
            );
            return question;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    public int findTypeIdByValue(String value) {
        try {
            int id = 0;
            String sql = "Select id from \"hr_system\".type WHERE value = '" + value + "'";
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_TYPE_ID + value + "'");
            for (Map row : rows) {
                id = (int) row.get("id");
            }
            return id;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return 0;
    }


    @Override
    public boolean insert(Question question) {
        try {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                    withTableName("\"hr_system\".question").
                    usingColumns("caption", "type_id", "is_mandatory").
                    usingGeneratedKeyColumns("id");
            MapSqlParameterSource insertParameter = new MapSqlParameterSource();
            insertParameter.addValue("caption", question.getCaption());
            insertParameter.addValue("type_id", findTypeIdByValue(question.getType()));
            insertParameter.addValue("is_mandatory", question.isMandatory());
            Number key = simpleJdbcInsert.executeAndReturnKey(insertParameter);
            if (key != null) {
                question.setId(key.intValue());
            }
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                    .withTableName("\"hr_system\".question_course_maps")
                    .usingColumns("question_id", "course_id", "order_number");
            insertParameter.addValue("question_id", question.getId());
            insertParameter.addValue("course_id", question.getCourseID());
            insertParameter.addValue("order_number", question.getOrderNumber());
            simpleJdbcInsert.execute(insertParameter);

            try {
                simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                        .withTableName("\"hr_system\".question_addition")
                        .usingColumns("question_id", "value");
                for (int i = 0; i < question.getAnswerVariants().size(); i++) {
                    insertParameter.addValue("question_id", question.getId());
                    insertParameter.addValue("value", question.getAnswerVariants().get(i));
                    simpleJdbcInsert.execute(insertParameter);
                }
            } catch (NullPointerException e) {
                return true;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    //Not checked
    public boolean update(Question question) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            jdbcTemplate.update(UPDATE_QUESTION, question.getCaption(), findTypeIdByValue(question.getType()), question.isMandatory(),
                    question.getId());

            jdbcTemplate.update(UPDATE_QUESTION_COURSE_MAPS, question.getCourseID(), question.getOrderNumber(), question.getId());

            if (question.getAnswerVariants() != null) {
                String sqlQADelete = "DELETE from \"hr_system\".question_addition WHERE question_id = " + question.getId();
                jdbcTemplate.update(sqlQADelete);
                for (int i = 0; i < question.getAnswerVariants().size(); i++) {
                    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                            withTableName("\"hr_system\".question_addition").
                            usingColumns("question_id", "value");
                    MapSqlParameterSource insertParameter = new MapSqlParameterSource();
                    insertParameter.addValue("question_id", question.getId());
                    insertParameter.addValue("value", question.getAnswerVariants().get(i));
                    simpleJdbcInsert.execute(insertParameter);

                }
            }
            return true;

        } catch (Exception e) {
            LOGGER.error(e);
        }

        return false;
    }


    @Override
    public boolean delete(Question question) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "DELETE FROM \"hr_system\".question WHERE id =" + question.getId();
            jdbcTemplate.update(sql);
            return true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public List<String> findAnswerVariants(Question question) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT value FROM \"hr_system\".question_addition WHERE question_id=" + question.getId();
            List<String> additionValue = new ArrayList<String>();
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map row : rows) {
                additionValue.add(row.get("value").toString());
            }
            return additionValue;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }
}
