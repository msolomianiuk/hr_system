package ua.netcracekr.hr_system.model.dao.db_dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracekr.hr_system.model.dao.ICandidateDao;
import ua.netcracekr.hr_system.model.dao.connection.ConnectionManager;
import ua.netcracekr.hr_system.model.entity.Candidate;
import ua.netcracekr.hr_system.model.entity.Question;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 26.04.2016.
 */
@Repository("candidateDao")
public class CandidateDaoImpl implements ICandidateDao {
    static final Logger LOGGER = Logger.getLogger(CandidateDaoImpl.class);
    private Candidate candidate;

    @Autowired(required = false)
    private void setCandidate(Candidate candidate) {

        this.candidate = candidate;
    }

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;


    public boolean insert(Candidate candidate) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".candidate").
                usingColumns("id", "user_id", "status_id", "course_id");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("id", candidate.getId());
        insertParameter.addValue("user_id", candidate.getUserId());
        insertParameter.addValue("status_id", candidate.getStatusId());
        insertParameter.addValue("course_id", candidate.getCourseId());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    public boolean insertAnswer(Candidate candidate, int idQuestion) {

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".candidate_answer").
                usingColumns("candidate_id", "question_id", "value");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("candidate_id", candidate.getId());
        insertParameter.addValue("question_id", idQuestion);
        insertParameter.addValue("value", candidate.getAnswerValue());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;

    }

    @Override
    public Candidate findById(Integer id) {
        return null;
    }

    @Override
    public Candidate findByName(String name) {
        return null;
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
    public boolean update(Candidate elem) {
        return false;
    }

    @Override
    public boolean remove(Candidate elem) {
        return false;
    }

}
