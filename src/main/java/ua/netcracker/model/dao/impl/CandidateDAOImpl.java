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
import ua.netcracker.model.entity.*;
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
    private static final String PAGINATION = "WITH cand AS " +
            "(SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,"
            + "candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
            "FROM \"hr_system\".users u " +
            "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
            "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
            "LEFT OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
            "LEFT OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
            "WHERE rol.role_id = 5 ) " +
            "SELECT  cand.id,cand.name,cand.email ,cand.surname, cand.patronymic,cand.status_id, cand.course_id ," +
            " cand.interviewer_id, cand.mark, cand.comment, cand.value " +
            "FROM cand ";

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
    public boolean updateCandidateStatus(Integer candidateId, Status newStatus) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE_STATUS, newStatus.getId(), candidateId);
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
        return findCandidates(FIND_PART_BY_COURSE + courseId + " ORDER BY status_id xDESC OFFSET " +
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
    public long rowsFind(String find) {

        int id = 2000000000;
        try {
            id = Integer.valueOf(find);
        } catch (Exception e) {

        }

        long rows;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            rows = jdbcTemplate.queryForObject(
                    "WITH cand AS (SELECT  DISTINCT ON(candidate.id) *" +
                            "FROM \"hr_system\".users u " +
                            "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                            "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                            "LEFT OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
                            "LEFT OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
                            "LEFT OUTER JOIN \"hr_system\".status status on candidate.status_id = status.id " +
                            "LEFT OUTER JOIN \"hr_system\".candidate_answer answer on candidate.id = answer.candidate_id " +
                            "WHERE rol.role_id = 5  " +
                            "and( name LIKE '%" + find + "%' " +
                            "or surname LIKE '%" + find + "%' " +
                            "or patronymic LIKE '%" + find + "%' " +
                            "or email LIKE '%" + find + "%' " +
                            "or answer.value LIKE '%" + find + "%' " +
                            "or status.value LIKE '%" + find + "%' " +
                            "or candidate.id = " + id + "))SELECT COUNT(*) FROM cand ", new RowMapper<Long>() {
                        @Override
                        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                            long row = resultSet.getLong("count");
                            return row;
                        }
                    });
            return rows;
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return 0;
    }

    @Override
    public Collection<Candidate> findForSearch(Integer elementPage, Integer fromElement, String find) {

        int d = 2000000000;
        try {
            d = Integer.valueOf(find);
        } catch (Exception e) {

        }

        Collection<Candidate> listCandidates;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            listCandidates = jdbcTemplate.query(
                    "WITH cand AS (SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, " +
                            "u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark," +
                            " ir.comment, r.value " +
                            "FROM \"hr_system\".users u " +
                            "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                            "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                            "LEFT OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
                            "LEFT OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
                            "LEFT OUTER JOIN \"hr_system\".status status on candidate.status_id = status.id " +
                            "LEFT OUTER JOIN \"hr_system\".candidate_answer answer on " +
                            "candidate.id = answer.candidate_id " +
                            "WHERE rol.role_id = 5  " +
                            "and( name LIKE '%" + find + "%' " +
                            "or surname LIKE '%" + find + "%' " +
                            "or patronymic LIKE '%" + find + "%' " +
                            "or email LIKE '%" + find + "%' " +
                            "or answer.value LIKE '%" + find + "%' " +
                            "or status.value LIKE '%" + find + "%' " +
                            "or candidate.id = " + d + "))SELECT * FROM cand  " +
                            "ORDER BY cand.course_id DESC, " +
                            "cand.status_id DESC,  cand.id " +
                            "limit " + elementPage + " offset " + fromElement,
                    new RowMapper<Candidate>() {
                        @Override
                        public Candidate mapRow(ResultSet resultSet, int i) throws SQLException {
                            Candidate candidate = new Candidate();
                            User user = new User();
                            user.setName(resultSet.getString("name"));
                            user.setSurname(resultSet.getString("surname"));
                            user.setPatronymic(resultSet.getString("patronymic"));
                            user.setEmail(resultSet.getString("email"));
                            candidate.setUser(user);
                            candidate.setId(resultSet.getInt("id"));
                            candidate.setStatusId(resultSet.getInt("status_id"));
                            candidate.setCourseId(resultSet.getInt("course_id"));

                            Collection<InterviewResult> list = interviewResultDAO.
                                    findResultsByCandidateId(candidate.getId());
                            candidate.setInterviewResults(list);

                            return candidate;
                        }
                    });
            return listCandidates;
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return null;
    }

    @Override
    public Collection<Candidate> paginationCandidates(Integer elementPage, Integer fromElement) {

        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            String sql = PAGINATION +
                    "ORDER BY cand.course_id DESC,cand.interviewer_id,cand.status_id DESC, cand.id LIMIT ";
            jdbcTemplate = new JdbcTemplate(dataSource);
            listCandidates.addAll(jdbcTemplate.query(sql + elementPage + " offset " + fromElement,
                    new RowMapper<Candidate>() {
                @Override
                public Candidate mapRow(ResultSet resultSet, int i) throws SQLException {
                    Candidate candidate = new Candidate();
                    User user = new User();
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setPatronymic(resultSet.getString("patronymic"));
                    user.setEmail(resultSet.getString("email"));
                    candidate.setUser(user);
                    candidate.setId(resultSet.getInt("id"));
                    candidate.setStatusId(resultSet.getInt("status_id"));
                    candidate.setCourseId(resultSet.getInt("course_id"));
                    Collection<InterviewResult> list = interviewResultDAO.findResultsByCandidateId(candidate.getId());

                    candidate.setInterviewResults(list);

                    return candidate;
                }
            }));

        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return listCandidates;

    }

    private List<Candidate> getCandidatesListFromSqlQuery(String sql) {
        List<Candidate> listCandidates = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            listCandidates = jdbcTemplate.query(sql, new RowMapper<Candidate>() {
                @Override
                public Candidate mapRow(ResultSet resultSet, int i) throws SQLException {
                    Candidate candidate = new Candidate();
                    User user = new User();
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setPatronymic(resultSet.getString("patronymic"));
                    user.setEmail(resultSet.getString("email"));
                    candidate.setUser(user);
                    candidate.setId(resultSet.getInt("id"));
                    candidate.setStatusId(resultSet.getInt("status_id"));
                    candidate.setCourseId(resultSet.getInt("course_id"));
                    Collection<InterviewResult> list = interviewResultDAO.findResultsByCandidateId(candidate.getId());

                    candidate.setInterviewResults(list);

                    return candidate;
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return listCandidates;
    }

    private String appendQueryByAnswers(String sql, List<Answer> expected) {
        for (Answer answer : expected) {
            sql = sql.concat("(SELECT candidate_id " +
                    " FROM \"hr_system\".candidate_answer" +
                    " WHERE question_id = " + answer.getQuestionId() + " AND value LIKE '%" +
                    answer.getValue() + "%' AND candidate_id IN ");
        }

        //cut off the last "AND candidate_id IN "
        sql = sql.substring(0, sql.length() - 20);

        for (int i = 0; i < expected.size(); i++) {
            sql = sql.concat(")");
        }

        return sql;
    }

    @Override
    public Collection<Candidate> filtration(List<Answer> expected, Integer limit, Integer offset) {
        if (expected.isEmpty()) {
            return paginationCandidates(limit, offset);
        }

        String sql = PAGINATION.concat(" WHERE cand.id IN ");


        sql = appendQueryByAnswers(sql, expected);

        sql = sql.concat(" ORDER BY cand.course_id DESC,cand.interviewer_id,cand.status_id DESC," +
                " cand.id LIMIT " + limit + " offset " + offset);

        return getCandidatesListFromSqlQuery(sql);
    }

    @Override
    public Long getRows(List<Answer> expected) {
        String sql = "SELECT count(*) FROM \"hr_system\".candidate";
        if (!expected.isEmpty()) {

            sql += " WHERE candidate.id IN ";

            sql = appendQueryByAnswers(sql, expected);
        }

        Long rows = null;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            rows = jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {
                @Override
                public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getLong("count");
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return rows;
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
