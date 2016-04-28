package ua.netcracker.hr_system.model.dao.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.QuestionDao;
import ua.netcracker.hr_system.model.entity.Question;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Alex on 26.04.2016.
 */
@Repository("questionDao")
public class QuestionDaoImpl implements QuestionDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private Question question;
    private HashMap questionMap;

    @Override
    public Collection<Question> findAll() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM \"hr_system\".question";
        List<Question> questions = new ArrayList<Question>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for(Map row : rows){
            Question question = new Question();
            question.setId((int)row.get("id"));
            question.setCaption(row.get("caption").toString());
            question.setTypeId((int)row.get("type_id"));
            question.setMandatory((boolean)row.get("is_mandatory"));
            questions.add(question);
        }
        return questions;
    }

    @Override
    public Question find(int id) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM \"hr_system\".question WHERE id = " + id;
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
        question.setId(resultSet.getInt("id"));
        question.setCaption(resultSet.getString("caption"));
        question.setTypeId(resultSet.getInt("type_id"));
        question.setMandatory(resultSet.getBoolean("is_mandatory"));
        return question;
    }

    @Override
    public boolean insert(Question question) {

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".question").
                usingColumns("caption", "type_id", "is_mandatory");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("caption", question.getCaption());
        insertParameter.addValue("type_id", question.getTypeId());
        insertParameter.addValue("is_mandatory", question.isMandatory());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    @Override
    public boolean update(Question question) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sqlUpdate = "UPDATE \"hr_system\".question SET";
        return jdbcTemplate.update(sqlUpdate,
                new Object[]{question.getId(), question.getCaption(),
                        question.getTypeId(), question.isMandatory()})
                ==question.getId()?true:false;
    }

    @Override
    public boolean remove(Question elem) {
        return false;
    }
}
