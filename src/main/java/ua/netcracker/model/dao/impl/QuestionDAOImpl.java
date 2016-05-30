package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.QuestionDAO;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.SendEmailService;

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
            "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
            "ORDER BY qcp.order_number";

    private static final String SELECT_ALL_MANDATORY = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
            "WHERE q.is_mandatory = true AND qcp.course_id = ";
    private static final String SELECT_ALL_MANDATORY_IS_VIEW = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
            "WHERE q.is_mandatory = true AND q.is_view = true AND qcp.course_id = ";

    private static final String SELECT_ALL_BY_COURSE_ID = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
            "WHERE qcp.course_id = ";

    private static final String SELECT_TYPE_ID = "SELECT id FROM \"hr_system\".type WHERE value = ?";

    private static final String FIND_BY_ID = "SELECT qcp.order_number, q.*, t.value ,qcp.course_id " +
            "FROM \"hr_system\".question_course_maps qcp " +
            "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
            "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
            "WHERE q.id = ?; ";

    private static final String UPDATE_QUESTION =
            "UPDATE \"hr_system\".question SET caption = ?, type_id = ?, is_mandatory = ?, " +
                    "is_view = ? WHERE id = ?;";

    private static final String UPDATE_QUESTION_COURSE_MAPS =
            "UPDATE \"hr_system\".question_course_maps SET course_id = ?, " +
                    "order_number = ? WHERE question_id = ?;";

    private static final String LAST_ID_QUESTION = "SELECT id FROM \"hr_system\".question ORDER BY id DESC limit 1";

    private static final String COURSE_ID = "SELECT id FROM \"hr_system\".course_setting ORDER BY id DESC limit 1";

    private static final String DELETE_QUESTION = "DELETE FROM \"hr_system\".question WHERE id = ?";

    private static final String SELECT_ANSWER_VARIANTS =
            "SELECT value FROM \"hr_system\".question_addition WHERE question_id = ?";

    private static final String SELECT_TYPE_VALUE = "SELECT value FROM \"hr_system\".type";

    private static final String DELETE_ANSWER_VARIANTS =
            "DELETE FROM \"hr_system\".question_addition WHERE question_id = ?";
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SendEmailService sendEmailService;

    @Override
    public Collection<Question> findQuestions(String sql) {
        Collection<Question> questions = new ArrayList<>();
        try {

            jdbcTemplate = new JdbcTemplate(dataSource);
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
        } catch (DataAccessException e) {
            sendEmailService.sendEmailAboutCriticalError("ERROR in findQuestions\n" + e.getMessage());
            LOGGER.error("Method: findQuestions" + " Error: " + e);
        }
        return questions;
    }

    @Override
    public Collection<Question> findAllMandatory(int courseId) {
        return findQuestions(SELECT_ALL_MANDATORY + courseId + " Order by qcp.order_number");
    }

    @Override
    public Collection<Question> findAllMandatoryAndView(int courseId) {
        return findQuestions(SELECT_ALL_MANDATORY_IS_VIEW + courseId + " Order by qcp.order_number");
    }

    @Override
    public Collection<Question> findAllByCourseId(int courseId) {
        return findQuestions(SELECT_ALL_BY_COURSE_ID + courseId + " Order by qcp.order_number");
    }

    @Override
    public boolean delete(Question question) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(DELETE_QUESTION, question.getId());
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: delete" + " Error: " + e);
        }
        return false;
    }

    @Override
    public Collection<String> findAnswerVariants(Question question) {
        Collection<String> additionValue = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ANSWER_VARIANTS, question.getId());
            for (Map row : rows) {
                additionValue.add(row.get("value").toString());
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: delete" + " Error: " + e);
        }
        return additionValue;
    }

    @Override
    public Collection<Question> findType() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        Collection<Question> questionType = new ArrayList<>();
        try {
            questionType.addAll(jdbcTemplate.query(SELECT_TYPE_VALUE, new RowMapper<Question>() {
                @Override
                public Question mapRow(ResultSet resultSet, int i) {
                    Question question = new Question();
                    try {
                        question.setType(resultSet.getString("value"));
                    } catch (SQLException e) {
                        LOGGER.info("Method: findType" + " SQL State: " + e.getSQLState()
                                + " Message: " + e.getMessage());
                        LOGGER.debug(e.getStackTrace(), e);
                    }
                    return question;
                }
            }));
        } catch (DataAccessException e) {
            LOGGER.error("Method: findType" + " Error: " + e);
        }
        return questionType;
    }

    @Override
    public int findQuantityQuestions() throws NullPointerException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        int question = 0;
        try {
            question = jdbcTemplate.queryForObject(LAST_ID_QUESTION, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet resultSet, int i) {
                    int quantityQuestions = 0;
                    try {
                        quantityQuestions = resultSet.getInt("id");
                    } catch (SQLException e) {
                        LOGGER.info("Method: findType" + " SQL State: " + e.getSQLState()
                                + " Message: " + e.getMessage());
                        LOGGER.debug(e.getStackTrace(), e);
                    }
                    return quantityQuestions;
                }
            });
        } catch (DataAccessException e) {
            LOGGER.error("Method: findQuantityQuestions" + " Error: " + e);
        }
        return question;
    }

    @Override
    public int findCourseId() throws NullPointerException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        int courseId = 0;
        try {
            courseId = jdbcTemplate.queryForObject(COURSE_ID, new RowMapper<Integer>() {

                @Override
                public Integer mapRow(ResultSet resultSet, int i) {
                    int courseID = 0;
                    try {
                        courseID = resultSet.getInt("id");
                    } catch (SQLException e) {
                        LOGGER.info("Method: findCourseId" + " SQL State: " + e.getSQLState()
                                + " Message: " + e.getMessage());
                        LOGGER.debug(e.getStackTrace(), e);
                    }
                    return courseID;
                }
            });
        } catch (DataAccessException e) {
            LOGGER.error("Method: findQuantityQuestions" + " Error: " + e);
        }
        return courseId;
    }

    @Override
    public Collection<Question> findAll() {
        return findQuestions(SELECT_ALL);
    }


    public Question find(int id) throws NullPointerException {
        Question question = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);

            question = jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, new RowMapper<Question>() {
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
        } catch (DataAccessException e) {
            LOGGER.error("Method: find" + " Error: " + e);
        }
        return question;
    }

    public int findTypeIdByValue(String value) throws NullPointerException {
        int id = 0;
        try {

            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_TYPE_ID, value);
            for (Map row : rows) {
                id = (int) row.get("id");
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findTypeIdByValue" + " Error: " + e);
        }
        return id;
    }


    public boolean insert(Question question) {
        try {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                    withTableName("\"hr_system\".question").
                    usingColumns("caption", "type_id", "is_mandatory", "is_view").
                    usingGeneratedKeyColumns("id");
            MapSqlParameterSource insertParameter = new MapSqlParameterSource();
            insertParameter.addValue("caption", question.getCaption());
            try {
                insertParameter.addValue("type_id", findTypeIdByValue(question.getType()));
            } catch (NullPointerException e) {
                LOGGER.error("Method: insert" + " Error: " + e);
            }
            insertParameter.addValue("is_mandatory", question.isMandatory());
            insertParameter.addValue("is_view", question.isView());
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
                    insertParameter.addValue("value", ((ArrayList<String>) question.getAnswerVariants()).get(i));
                    simpleJdbcInsert.execute(insertParameter);
                }
            } catch (NullPointerException e) {
                LOGGER.error("Method: insert" + " Error: " + e);
                return true;
            }
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: insert" + " Error: " + e);
        }
        return false;
    }


    @Override
    public boolean update(Question question) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);

            jdbcTemplate.update(DELETE_ANSWER_VARIANTS, question.getId());
            try {
                jdbcTemplate.update(UPDATE_QUESTION, question.getCaption(), findTypeIdByValue(question.getType()),
                        question.isMandatory(),
                        question.isView(), question.getId());
            } catch (NullPointerException e) {
                LOGGER.error("Method: update" + " Error: " + e);
            }
            jdbcTemplate.update(UPDATE_QUESTION_COURSE_MAPS, question.getCourseID(), question.getOrderNumber(),
                    question.getId());

            if (question.getAnswerVariants() != null) {
                for (int i = 0; i < question.getAnswerVariants().size(); i++) {
                    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                            withTableName("\"hr_system\".question_addition").
                            usingColumns("question_id", "value");
                    MapSqlParameterSource insertParameter = new MapSqlParameterSource();
                    insertParameter.addValue("question_id", question.getId());
                    insertParameter.addValue("value", ((ArrayList<String>) question.getAnswerVariants()).get(i));
                    simpleJdbcInsert.execute(insertParameter);
                }
            }
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: update" + " Error: " + e);
        }
        return false;
    }


}