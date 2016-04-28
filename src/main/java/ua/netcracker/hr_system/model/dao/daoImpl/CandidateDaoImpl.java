package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.CandidateDao;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 26.04.2016.
 */
@Repository("candidateDao")
public class CandidateDaoImpl implements CandidateDao {
    static final Logger LOGGER = Logger.getLogger(CandidateDaoImpl.class);
    private Candidate candidate;
    private User user;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired(required = false)
    private void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired(required = false)
    private void setUser(User user) {
        this.user = user;
    }

    @Autowired(required = false)
    private void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }


    public boolean insert(Candidate candidate) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".candidate").
                usingColumns("user_id", "status_id", "course_id");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("user_id", candidate.getUserId());
        insertParameter.addValue("status_id", candidate.getStatusId());
        insertParameter.addValue("course_id", candidate.getCourseId());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    @Override
    public List<Candidate> getAllProfileById(int roleId) {

        List list = null;

        if (roleId == 5) {
            jdbcTemplate = new JdbcTemplate(dataSource);

            String sql = "SELECT u.id , u.name , u.email, u.surname, u.patronymic " +
                    "FROM \"hr_system\".users u " +
                    "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                    "WHERE rol.role_id=" + roleId;

            List<Candidate> listOfCandidate = jdbcTemplate.query(sql, new RowMapper<Candidate>() {

                @Override
                public Candidate mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    candidate = new Candidate();
                    user = new User();
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));

                    candidate.setUser(user);
                    return candidate;
                }

            });

            list = listOfCandidate;

        }
        return list;
    }


    @Override
    public HashMap getProfileById() {
        return null;
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
    public int findByStatusId(Integer statusId) {
        return 0;
    }

    @Override
    public Collection<Candidate> findAll() {
        return null;
    }

    public boolean insertAnswer(Candidate candidate, int questionId) {

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".candidate_answer").
                usingColumns("candidate_id", "question_id", "value");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("candidate_id", candidate.getId());
        insertParameter.addValue("question_id", questionId);
        insertParameter.addValue("value", candidate.getAnswerValue());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;

    }

    public Map<Integer, String> findAnswersAll() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".candidate_answer";
        Map<Integer, String> listAnswer = new HashMap<Integer, String>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            listAnswer.put((int) row.get("question_id"), (String) row.get("value"));
        }
        return listAnswer;
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
