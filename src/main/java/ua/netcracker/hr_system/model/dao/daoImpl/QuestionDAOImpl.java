package ua.netcracker.hr_system.model.dao.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.QuestionDAO;
import ua.netcracker.hr_system.model.entity.Question;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository("questionDao")
public class QuestionDAOImpl implements QuestionDAO {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private Question question;



    public Collection<Question> getQuestions(String sql) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Question> questions = new ArrayList<Question>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for(Map row : rows){
            Question question = new Question();
            question.setOrderNumber((int)row.get("order_number"));
            question.setId((int)row.get("id"));
            question.setCaption(row.get("caption").toString());
            question.setAdditionValue(findVariantsAnswer((int)row.get("id")));
            question.setMandatory((boolean)row.get("is_mandatory"));
            question.setCourseId((int)row.get("course_id"));
            question.setTypeValue(row.get("value").toString());
            questions.add(question);
        }
        return questions;
    }


    public Collection<Question> findAll() {
        String sql = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
                "FROM \"hr_system\".question_course_maps qcp " +
                "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
                "Inner join \"hr_system\".type t ON q.type_id = t.id " +
                "Order by qcp.order_number";
        return getQuestions(sql);
    }

    public Collection<Question> findAllMandatory() {
        String sql = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
                "FROM \"hr_system\".question_course_maps qcp " +
                "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
                "INNER JOIN \"hr_system\".type t ON q.type_id = t.id " +
                "WHERE q.is_mandatory = true " +
                "Order by qcp.order_number";
        return getQuestions(sql);
    }


    public Question find(int id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT qcp.order_number, q.*,t.value ,qcp.course_id " +
                "FROM \"hr_system\".question_course_maps qcp " +
                "INNER JOIN \"hr_system\".question q ON qcp.question_id = q.id " +
                "Inner join \"hr_system\".type t ON q.type_id = t.id " +
                "WHERE q.id = " + id;
        question = jdbcTemplate.queryForObject(sql, new RowMapper<Question>() {
                    @Override
                    public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getQuestion(resultSet);
                    }
                }
        );
        return question;
    }

    private Question getQuestion(ResultSet resultSet) throws SQLException {
        question = new Question();
        question.setOrderNumber(resultSet.getInt("order_number"));
        question.setId(resultSet.getInt("id"));
        question.setCaption(resultSet.getString("caption"));
        question.setAdditionValue(findVariantsAnswer(resultSet.getInt("id")));
        question.setMandatory(resultSet.getBoolean("is_mandatory"));
        question.setCourseId(resultSet.getInt("course_id"));
        question.setTypeValue(resultSet.getString("value"));
        return question;
    }

    private int getTypeIdByValue(String value){
        int id = 0;
        String sql = "Select id from \"hr_system\".type WHERE value = '" + value + "'" ;
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows){
            id = (int)row.get("id");
        }
        return id;
    }


    public boolean insert(Question question) {

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".question").
                usingColumns("caption", "type_id", "is_mandatory").
                usingGeneratedKeyColumns("id");
                MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("caption", question.getCaption());
        insertParameter.addValue("type_id", getTypeIdByValue(question.getTypeValue()));
        insertParameter.addValue("is_mandatory", question.isMandatory());
        Number key = simpleJdbcInsert.executeAndReturnKey(insertParameter);
        if (key != null) {
            question.setId(key.intValue());
        }

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("\"hr_system\".question_course_maps")
                .usingColumns("question_id","course_id","order_number");
        insertParameter.addValue("question_id",question.getId());
        insertParameter.addValue("course_id",question.getCourseId());
        insertParameter.addValue("order_number",question.getOrderNumber());
        simpleJdbcInsert.execute(insertParameter);

        try {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                    .withTableName("\"hr_system\".question_addition")
                    .usingColumns("question_id", "value");
            for (int i = 0; i < question.getAdditionValue().size(); i++) {
                insertParameter.addValue("question_id", question.getId());
                insertParameter.addValue("value", question.getAdditionValue().get(i));
                simpleJdbcInsert.execute(insertParameter);
            }
        }catch (NullPointerException e){
            return true;
        }
            return true;
    }


    //Not checked
    public boolean update(Question question) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sqlQuestionUpdate = "UPDATE \"hr_system\".question SET caption = ?" + question.getCaption() + ", type_id = ?" +
        + getTypeIdByValue(question.getTypeValue()) + ", is_mandatory = ?" + question.isMandatory() + "WHERE id = " + question.getId();
        jdbcTemplate.update(sqlQuestionUpdate);

        String sqlQCM = "UPDATE \"hr_system\".question_course_maps SET course_id = " + question.getCourseId() + ", " +
                "order_number = " + question.getOrderNumber() + " Where question_id = " + question.getId();
        jdbcTemplate.update(sqlQCM);

        if (question.getAdditionValue() != null) {
            String sqlQADelete = "DELETE from \"hr_system\".question_addition WHERE question_id = " + question.getId();
            jdbcTemplate.update(sqlQADelete);
            for (int i = 0; i < question.getAdditionValue().size(); i++){
                simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                        withTableName("\"hr_system\".question_addition").
                        usingColumns("question_id", "value");
                MapSqlParameterSource insertParameter = new MapSqlParameterSource();
                insertParameter.addValue("question_id", question.getId());
                insertParameter.addValue("value", question.getAdditionValue().get(i));
                simpleJdbcInsert.execute(insertParameter);
            }
        }
        return true;
    }

    /**
     *
     * @param question
     * @return
     */

    public boolean remove(Question question) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "DELETE FROM \"hr_system\".question WHERE id =" + question.getId();
        jdbcTemplate.update(sql);
        return true;
    }

    @Override
    public List<String> findVariantsAnswer(int questionId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT value FROM \"hr_system\".question_addition WHERE question_id=" + questionId;
        List<String> additionValue = new ArrayList<String>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for(Map row : rows){
            additionValue.add(row.get("value").toString());
        }
        return additionValue;
    }
}
