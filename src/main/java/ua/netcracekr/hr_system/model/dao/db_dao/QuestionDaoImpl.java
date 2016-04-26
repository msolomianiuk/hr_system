package ua.netcracekr.hr_system.model.dao.db_dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.netcracekr.hr_system.model.entity.Question;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * Created by Alex on 26.04.2016.
 */
public class QuestionDaoImpl implements IQuestionDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public Collection<Question> findAll() {
        return null;
    }

    @Override
    public Question find(String sql) {
        return null;
    }
    public Question find(int candidateId, int questionId){
        jdbcTemplate = new JdbcTemplate(dataSource);

        return null;
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
