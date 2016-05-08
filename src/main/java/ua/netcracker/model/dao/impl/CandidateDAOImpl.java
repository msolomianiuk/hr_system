package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository("candidateDao")
public class CandidateDAOImpl implements CandidateDAO {
    private static final Logger LOGGER = Logger.getLogger(CandidateDAOImpl.class);
    private static final String FIND_INTERVIEW_DAYS_DETAILS_ID =
            "Select interview_days_details from \"hr_system\".candidate where id = ";

    private static final String FIND_BY_ID = "select * from \"hr_system\".candidate WHERE id = ";
    private static final String FIND_ALL = "SELECT u.id , u.name , u.email, u.surname, u.patronymic " +
            "FROM \"hr_system\".users u JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id WHERE rol.role_id=";
    private static final String FIND_STATUS_BY_ID = "select * from \"hr_system\".status WHERE id = ";
    private User user;

    @Autowired
    private DataSource dataSource;

    @Override
    public int getInterviewDayDetailsById(Integer candidateId) {
        Integer interviewDaysDetails = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            interviewDaysDetails = jdbcTemplate.queryForObject(FIND_INTERVIEW_DAYS_DETAILS_ID + candidateId,
                    new RowMapper<Integer>() {
                        @Override
                        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return rs.getInt("interview_days_details");
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
        return interviewDaysDetails;
    }


    @Override
    public Candidate findCandidateById(Integer candidateId) {
        Candidate candidate = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            candidate = jdbcTemplate.queryForObject(FIND_BY_ID + candidateId, new RowMapper<Candidate>() {
                        @Override
                        public Candidate mapRow(ResultSet rs, int rowNum) {
                            Candidate candidate = new Candidate();
                            try {
                                candidate.setId(rs.getInt("id"));
                                candidate.setUserId(rs.getInt("user_id"));
                                candidate.setStatusId(rs.getInt("status_id"));
                                candidate.setCourseId(rs.getInt("course_id"));
                            } catch (SQLException e) {
                                LOGGER.info(e.getMessage());
                                LOGGER.debug(e.getStackTrace());
                            }
                            return candidate;
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }
        return candidate;

    }

    @Override
    public Collection<Candidate> findAll() {
        List list = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            List<Candidate> listCandidates = jdbcTemplate.query(FIND_ALL + Role.ROLE_STUDENT.getId(),
                    new RowMapper<Candidate>() {

                        @Override
                        public Candidate mapRow(ResultSet rs, int rowNumber) throws SQLException {
                            Candidate candidate = new Candidate();
                            user = new User();
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            candidate.setUser(user);
                            return candidate;
                        }
                    });
            list = listCandidates;
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }

        return list;
    }

    @Override
    public String findStatusById(Integer statusId) {
        String status = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            status = jdbcTemplate.queryForObject(FIND_STATUS_BY_ID + statusId, new RowMapper<String>() {
                        @Override
                        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return rs.getString("value");
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }

        return status;
    }

    public Candidate findCandidateByUserId(Integer userId) {
        Candidate candidate = new Candidate();
        if (userId > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "Select * from \"hr_system\".candidate Where user_id = " + userId;
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
                for (Map row : rows) {
                    candidate.setId((int) row.get("id"));
                    candidate.setUserId((int) row.get("user_Id"));
                    candidate.setStatusId((int) row.get("status_id"));
                    candidate.setCourseId((int) row.get("course_id"));
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return candidate;
    }


    public boolean saveCandidate(Candidate candidate) {
        if (candidate != null) {
            try {
                SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                        withTableName("\"hr_system\".candidate").
                        usingColumns("user_id", "status_id", "course_id")
                        .usingGeneratedKeyColumns("id");
                ;
                MapSqlParameterSource insertParameter = new MapSqlParameterSource();
                insertParameter.addValue("user_id", candidate.getUserId());
                insertParameter.addValue("status_id", candidate.getStatusId());
                insertParameter.addValue("course_id", candidate.getCourseId());
                simpleJdbcInsert.execute(insertParameter);

            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
            return true;
        }
        return false;
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
    public boolean update(Candidate entity) {
        return false;
    }

    @Override
    public List<Candidate> getAllAnketsCandidates() {


        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT u.name , u.surname, u.patronymic , u.email, can.id\n" +
                "FROM \"hr_system\".users u\n" +
                "JOIN \"hr_system\".candidate can ON can.user_id = u.id";

        List<Candidate> listOfCandidate = jdbcTemplate.query(sql, new RowMapper<Candidate>() {

            @Override
            public Candidate mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Candidate candidate = new Candidate();
                User user = new User();
                candidate.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setSurname(rs.getString("surname"));
                user.setPatronymic(rs.getString("patronymic"));

                candidate.setUser(user);
                return candidate;
            }

        });
        return listOfCandidate;
    }
}
