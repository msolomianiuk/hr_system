package ua.netcracekr.hr_system.model.dao.db_dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracekr.hr_system.model.dao.IQuestionDao;
import ua.netcracekr.hr_system.model.entity.Question;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 26.04.2016.
 */
@Repository("questionDao")
public class QuestionDaoImpl implements IQuestionDao {
    private Question question;



    @Autowired(required = false)
    private void setQuestion(Question question) {
        this.question = question;
    }

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public Collection<Question> findAll() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".question";
        List<Question> listQuestions = new ArrayList<Question>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for(Map row: rows){
            Question question = new Question();
            question.setId((int)row.get("id"));
            question.setCaption((String)row.get("caption"));
            question.setTypeId((int)row.get("type_id"));
            question.setMandatory((boolean)row.get("is_mandatory"));
            listQuestions.add(question);
        }
        return listQuestions;
    }

    @Override
    public Question find(int id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".question where id=" + id;
        question = jdbcTemplate.queryForObject(sql, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                question = new Question();
                question.setId(resultSet.getInt("id"));
                question.setCaption(resultSet.getString("caption"));
                question.setTypeId(resultSet.getInt("type_id"));
                question.setMandatory(resultSet.getBoolean("is_mandatory"));
                return question;
            }

        });
        return question;
    }

    @Override
    public boolean insert(Question elem) {

        return false;
    }

    @Override
    public boolean update(Question elem) {
        return false;
    }

    @Override
    public boolean remove(Question elem) {
        return false;
    }
}
