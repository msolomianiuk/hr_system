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
import ua.netcracker.model.entity.Status;
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
            "SELECT interview_days_details FROM \"hr_system\".candidate WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM \"hr_system\".candidate WHERE id = ?";
    private static final String FIND_ALL = "SELECT u.id , u.name , u.email, u.surname, u.patronymic " +
            "FROM \"hr_system\".users u JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id WHERE rol.role_id=?";
    private static final String FIND_STATUS_BY_ID = "SELECT * FROM \"hr_system\".status WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT * FROM \"hr_system\".candidate WHERE user_id = ?";
    private static final String UPDATE = "UPDATE \"hr_system\".candidate SET(status_id,interview_days_details_id)=(?,?) " +
            " WHERE id = ? ";
    private static final String FIND_BY_STATUS = "SELECT * FROM \"hr_system\".candidate WHERE status_id = ";
    private User user;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;


    @Override
    public Collection<Candidate> findCandidateByStatus(String status) {
        Collection<Candidate> listCandidate = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.
                    queryForList(FIND_BY_STATUS +Status.valueOf(status).getId());
            for(Map<String , Object> row : rows){
                Candidate candidate = new Candidate();
                candidate.setId((int)row.get("id"));
                candidate.setUserId((int)row.get("user_id"));
                candidate.setStatusId((int)row.get("status_id"));
                candidate.setInterviewDaysDetailsId((int)row.get("interview_days_details_id"));
                candidate.setCourseId((int)row.get("course_id"));
                listCandidate.add(candidate);
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }

        return listCandidate;
    }

    @Override
    public int findInterviewDetailsByCandidateId(Integer candidateId) {
        int interviewDaysDetails = 0;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            interviewDaysDetails = jdbcTemplate.queryForObject(FIND_INTERVIEW_DAYS_DETAILS_ID + candidateId,
                    new RowMapper<Integer>() {
                        @Override
                        public Integer mapRow(ResultSet rs, int rowNum) {
                            int details = 0;
                            try {
                                details = rs.getInt("interview_days_details");
                            } catch (SQLException e) {
                                LOGGER.info("SQLState: " + e.getSQLState() + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return details;
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return interviewDaysDetails;
    }


    @Override
    public Candidate findByCandidateId(Integer candidateId) {
        Candidate candidate = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
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
                                LOGGER.info("SQLState: " + e.getSQLState() + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return candidate;
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return candidate;

    }

    @Override
    public Collection<Candidate> findAll() {
        Collection<Candidate> list = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            list = jdbcTemplate.query(FIND_ALL + Role.ROLE_STUDENT.getId(),
                    new RowMapper<Candidate>() {

                        @Override
                        public Candidate mapRow(ResultSet rs, int rowNumber) {
                            Candidate candidate = new Candidate();
                            try {
                                user = new User();
                                user.setName(rs.getString("name"));
                                user.setEmail(rs.getString("email"));
                                candidate.setUser(user);
                            } catch (SQLException e) {
                                LOGGER.info("SQLState: " + e.getSQLState() + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return candidate;
                        }
                    });
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return list;
    }


    @Override
    public String findStatusById(Integer statusId) {
        String status = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            status = jdbcTemplate.queryForObject(FIND_STATUS_BY_ID + statusId, new RowMapper<String>() {
                        @Override
                        public String mapRow(ResultSet rs, int rowNum) {
                            String status = null;
                            try {
                                rs.getString("value");
                            } catch (SQLException e) {
                                LOGGER.info("SQLState: " + e.getSQLState() + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return status;
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return status;
    }

    public Candidate findByUserId(Integer userId) {
        Candidate candidate = new Candidate();

        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_BY_USER_ID + userId);
            for (Map row : rows) {
                candidate.setId((int) row.get("id"));
                candidate.setUserId((int) row.get("user_Id"));
                candidate.setStatusId((int) row.get("status_id"));
                candidate.setCourseId((int) row.get("course_id"));
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }

        return candidate;
    }


    public boolean saveCandidate(Candidate candidate) {
        try {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                    withTableName("\"hr_system\".candidate").
                    usingColumns("user_id", "status_id", "course_id")
                    .usingGeneratedKeyColumns("id");
            MapSqlParameterSource insertParameter = new MapSqlParameterSource();
            insertParameter.addValue("user_id", candidate.getUserId());
            insertParameter.addValue("status_id", candidate.getStatusId());
            insertParameter.addValue("course_id", candidate.getCourseId());
            simpleJdbcInsert.execute(insertParameter);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
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
    public boolean update(Candidate candidate) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE, candidate.getStatusId(), candidate.getInterviewDaysDetailsId(), candidate.getId());
            return true;
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }
        return false;
    }


}
