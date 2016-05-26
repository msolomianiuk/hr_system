package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.dao.InterviewResultDAO;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.SendEmailService;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("candidateDao")
public class CandidateDAOImpl implements CandidateDAO {
    private static final Logger LOGGER = Logger.getLogger(CandidateDAOImpl.class);

    private static final String FIND_INTERVIEW_DAYS_DETAILS_ID =
            "SELECT interview_days_details FROM \"hr_system\".candidate WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM \"hr_system\".candidate WHERE id = ";
    private static final String FIND_ALL = "SELECT * FROM \"hr_system\".candidate";
    private static final String FIND_ALL_BY_COURSE = "SELECT * FROM \"hr_system\".candidate WHERE course_id = ";
    private static final String FIND_STATUS_BY_ID = "SELECT * FROM \"hr_system\".status WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT * FROM \"hr_system\".candidate WHERE user_id = ?";
    private static final String UPDATE =
            "UPDATE \"hr_system\".candidate SET(status_id,interview_days_details_id)=(?,?) " +
                    " WHERE id = ? ";
    private static final String FIND_BY_STATUS = "SELECT * FROM \"hr_system\".candidate WHERE status_id =?";
    private static final String FIND_ALL_STATUS = "SELECT * FROM \"hr_system\".status";
    private static final String UPDATE_STATUS = "UPDATE \"hr_system\".candidate SET status_id=(?)" +
            "WHERE id=?";
    private static final String FIND_ALL_MARKED_BY_CURRENT_INTERVIEWER =
            "SELECT c.id, c.status_id, ir.mark, ir.comment, ir.recommendation_id\n" +
                    "FROM \"hr_system\".candidate c\n" +
                    "JOIN \"hr_system\".interview_result ir ON c.id = ir.candidate_id\n" +
                    "WHERE ir.interviewer_id = ?;";
    private static final String FIND_PART = "SELECT * FROM \"hr_system\".candidate ORDER BY id OFFSET ";
    private static final String FIND_PART_BY_COURSE = "SELECT * FROM \"hr_system\".candidate WHERE course_id = ";
    private static final String SELECT_CANDIDATE_COUNT =
            "SELECT COUNT(*) FROM \"hr_system\".candidate WHERE course_id = ";
    private static final String SELECT_CANDIDATE_COUNT_BY_INTERVIEWDID =
            "SELECT COUNT(*) FROM \"hr_system\".candidate WHERE interview_days_details_id = ";

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    @Autowired
    private InterviewResultDAO interviewResultDAO;
    @Autowired
    private SendEmailService sendEmailService;

    @Override
    public int findInterviewDetailsByCandidateId(Integer candidateId) {
        int interviewDaysDetails = 0;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            interviewDaysDetails = jdbcTemplate.queryForObject(FIND_INTERVIEW_DAYS_DETAILS_ID,
                    new RowMapper<Integer>() {
                        @Override
                        public Integer mapRow(ResultSet rs, int rowNum) {
                            int details = 0;
                            try {
                                details = rs.getInt("interview_days_details");
                            } catch (SQLException e) {
                                LOGGER.info("Method: findInterviewDetailsByCandidateId" + " SQL State: "
                                        + e.getSQLState() + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return details;
                        }
                    }, candidateId);
        } catch (DataAccessException e) {
            LOGGER.error("Method: findInterviewDetailsByCandidateId" + " Error: " + e);
        }
        return interviewDaysDetails;
    }

    @Override
    public Candidate findByCandidateId(Integer candidateId) throws NullPointerException {
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
                                LOGGER.info("Method: findByCandidateId" + " SQL State: " + e.getSQLState()
                                        + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return candidate;
                        }
                    }
            );
        } catch (DataAccessException e) {
            LOGGER.error("Method: findByCandidateId" + " Error: " + e);
        }
        return candidate;

    }

    @Override
    public Collection<Candidate> findAll() {
        return findCandidates(FIND_ALL);
    }

    @Override
    public Status findStatusById(Integer statusId) throws IllegalArgumentException {
        String value = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            value = jdbcTemplate.queryForObject(FIND_STATUS_BY_ID, new RowMapper<String>() {
                        @Override
                        public String mapRow(ResultSet rs, int rowNum) {
                            String value = null;
                            try {
                                value = rs.getString("value");
                            } catch (SQLException e) {
                                LOGGER.info("Method: findStatusById" + " SQL State: " + e.getSQLState()
                                        + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return value;
                        }
                    }, statusId
            );
        } catch (DataAccessException e) {
            LOGGER.error("Method: findStatusById" + " Error: " + e);
        }
        return Status.valueOf(value);
    }

    @Override
    public Collection<Candidate> findCandidateByStatus(String status) {
        Collection<Candidate> listCandidate = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.
                    queryForList(FIND_BY_STATUS, Status.valueOf(status).getId());
            for (Map<String, Object> row : rows) {
                Candidate candidate = new Candidate();
                candidate.setId((int) row.get("id"));
                candidate.setUserId((int) row.get("user_id"));
                candidate.setStatusId((int) row.get("status_id"));
                try {
                    candidate.setStatus(findStatusById(candidate.getStatusId()));
                } catch (IllegalArgumentException e) {
                    LOGGER.info("Method: findCandidateByStatus" + e.getStackTrace()
                            + " Message: " + e.getMessage());
                    LOGGER.debug(e.getStackTrace(), e);
                }
                if (row.get("interview_days_details_id") != null)
                    candidate.setInterviewDaysDetailsId((int) row.get("interview_days_details_id"));
                candidate.setCourseId((int) row.get("course_id"));
                listCandidate.add(candidate);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findCandidateByStatus" + " Error: " + e);
        }
        return listCandidate;
    }


    public Candidate findByUserId(Integer userId) {
        Candidate candidate = new Candidate();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_BY_USER_ID, userId);
            for (Map row : rows) {
                candidate.setId((int) row.get("id"));
                candidate.setUserId((int) row.get("user_Id"));
                candidate.setStatusId((int) row.get("status_id"));
                try {
                    candidate.setStatus(findStatusById(candidate.getStatusId()));
                } catch (IllegalArgumentException e) {
                    LOGGER.info("Method: findByUserId" + e.getStackTrace()
                            + " Message: " + e.getMessage());
                    LOGGER.debug(e.getStackTrace(), e);
                }
                candidate.setCourseId((int) row.get("course_id"));
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findByUserId" + " Error: " + e);
        }

        return candidate;
    }

    @Override
    public Collection<Candidate> findAllByCourse(Integer courseId) {
        return findCandidates(FIND_ALL_BY_COURSE + courseId + " ORDER BY status_id DESC");
    }

    @Override
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
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.error("Method: saveCandidate" + " Error: " + e);
            sendEmailService.sendEmailAboutCriticalError("ERROR in saveCandidate\n" + e.getMessage());
        }
        return false;
    }

    @Override
    public Map<Integer, String> findAllStatus() {
        Map<Integer, String> statusMap = new HashMap<>();
        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_STATUS);
            for (Map row : rows) {
                statusMap.put((int) row.get("id"), row.get("value").toString());
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findAllStatus" + " Error: " + e);
        }
        return statusMap;
    }

    @Override
    public boolean updateCandidateStatus(Integer candidateId, Integer newStatusId) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE_STATUS, newStatusId, candidateId);
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: updateCandidateStatus" + " Error: " + e);
        }
        return false;
    }

    @Override
    public Collection<Candidate> getAllMarked(User user) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_MARKED_BY_CURRENT_INTERVIEWER,
                    user.getId());
            for (Map row : rows) {
                Candidate candidate = new Candidate();
                candidate.setId((int) row.get("id"));
                candidate.setStatusId((int) row.get("status_id"));
                try {
                    candidate.setStatus(findStatusById(candidate.getStatusId()));
                } catch (IllegalArgumentException e) {
                    LOGGER.info("Method: getAllMarked" + e.getStackTrace()
                            + " Message: " + e.getMessage());
                    LOGGER.debug(e.getStackTrace(), e);
                }
                candidate.setCourseId((int) row.get("course_id"));
                candidate.setInterviewResults(interviewResultDAO.findResultsByCandidateId(candidate.getId()));
                listCandidates.add(candidate);
            }
        } catch (Exception e) {
            LOGGER.error("Method: getAllMarked" + " Error: " + e);
        }
        return listCandidates;
    }

    @Override
    public Collection<Candidate> findPart(Integer with, Integer to) {
        return findCandidates(FIND_PART + with + " LIMIT " + to);
    }

    @Override
    public Collection<Candidate> findPartByCourse(Integer courseId, Integer with, Integer to) {
        return findCandidates(FIND_PART_BY_COURSE + courseId + " ORDER BY status_id DESC OFFSET " +
                with + " LIMIT " + to);
    }

    private Collection<Candidate> findCandidates(String sql) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : rows) {
                Candidate candidate = new Candidate();
                candidate.setId((int) row.get("id"));
                candidate.setUserId((int) row.get("user_id"));
                candidate.setStatusId((int) row.get("status_id"));
                try {
                    candidate.setStatus(findStatusById(candidate.getStatusId()));
                } catch (IllegalArgumentException e) {
                    LOGGER.info("Method: findCandidates " + e.getStackTrace()
                            + " Message: " + e.getMessage());
                    LOGGER.debug(e.getStackTrace(), e);
                }
                candidate.setCourseId((int) row.get("course_id"));
                listCandidates.add(candidate);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Method: findCandidates" + " Error: " + e);
        }
        return listCandidates;
    }

    //Розібратися з цією 1 і 0
    @Override
    public Integer getCandidateCount(int courseId) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Integer count = jdbcTemplate.queryForObject(SELECT_CANDIDATE_COUNT + courseId, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) {
                    int count = 0;
                    try {
                        count = rs.getInt(1);
                    } catch (SQLException e) {
                        LOGGER.info("Method: findCandidates " + " SQL State: " + e.getSQLState()
                                + " Message: " + e.getMessage());
                        LOGGER.debug(e.getStackTrace(), e);
                    }
                    return count;
                }
            });
            return count;

        } catch (DataAccessException e) {
            LOGGER.error("Method: getCandidateCount" + " Error: " + e);
        }
        return 0;
    }

    //теж саме з 1
    @Override
    public Integer getCandidateCountByInterviewId(int interviewId) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Integer count = jdbcTemplate.queryForObject(SELECT_CANDIDATE_COUNT_BY_INTERVIEWDID + interviewId,
                    new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) {
                    int count = 0;
                    try {
                        count = rs.getInt(1);
                    } catch (SQLException e) {
                        LOGGER.info("Method: getCandidateCountByInterviewId " + " SQL State: " + e.getSQLState()
                                + " Message: " + e.getMessage());
                        LOGGER.debug(e.getStackTrace(), e);
                    }
                    return count;
                }
            });
            return count;

        } catch (DataAccessException e) {
            LOGGER.error("Method: getCandidateCountByInterviewId" + " Error: " + e);
        }
        return 0;
    }

    @Override
    public boolean update(Candidate candidate) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE, candidate.getStatusId(), candidate.getInterviewDaysDetailsId(),
                    candidate.getId());
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("Method: update" + " Error: " + e);
            sendEmailService.sendEmailAboutCriticalError("ERROR in saveOrUpdateAnswers\n" + e.getMessage());
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


}
