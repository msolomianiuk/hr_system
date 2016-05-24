package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String UPDATE = "UPDATE \"hr_system\".candidate SET(status_id,interview_days_details_id)=(?,?) " +
            " WHERE id = ? ";
    private static final String FIND_BY_STATUS = "SELECT * FROM \"hr_system\".candidate WHERE status_id =?";
    private static final String FIND_ALL_STATUS = "SELECT * FROM \"hr_system\".status";
    private static final String UPDATE_STATUS = "UPDATE \"hr_system\".candidate SET status_id=(?)" +
            "WHERE id=?";
    private static final String PAGINATION = "WITH padik AS " +
            "(SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
            "FROM \"hr_system\".users u " +
            "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
            "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
            "LEFT OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
            "LEFT OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
            "WHERE rol.role_id = 5 ) " +
            "SELECT  padik.id,padik.name,padik.email ,padik.surname, padik.patronymic,padik.status_id, padik.course_id , padik.interviewer_id, padik.mark, padik.comment, padik.value " +
            "FROM padik " +
            "ORDER BY padik.course_id DESC,padik.interviewer_id,padik.status_id DESC,  padik.id LIMIT ";
    private static final String LAST_ROWS = "SELECT id FROM \"hr_system\".candidate ORDER BY id DESC LIMIT 1";
    private static final String FIND_ALL_MARKED_BY_CURRENT_INTERVIEWER =
            "SELECT c.id, c.status_id, ir.mark, ir.comment, ir.recommendation_id\n" +
                    "FROM \"hr_system\".candidate c\n" +
                    "JOIN \"hr_system\".interview_result ir ON c.id = ir.candidate_id\n" +
                    "WHERE ir.interviewer_id = ?;";
    private static final String FIND_PART = "SELECT * FROM \"hr_system\".candidate ORDER BY id OFFSET ";
    private static final String FIND_PART_BY_COURSE = "SELECT * FROM \"hr_system\".candidate WHERE course_id = ";
    private static final String SELECT_CANDIDATE_COUNT = "SELECT COUNT(*) FROM \"hr_system\".candidate WHERE course_id = ";
    private static final String SELECT_CANDIDATE_COUNT_BY_INTERVIEWDID = "SELECT COUNT(*) FROM \"hr_system\".candidate WHERE interview_days_details_id = ";

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private InterviewResultDAO interviewResultDAO;
    @Autowired
    private SendEmailService sendEmailService;

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
                if (row.get("interview_days_details_id") != null)
                    candidate.setInterviewDaysDetailsId((int) row.get("interview_days_details_id"));
                candidate.setCourseId((int) row.get("course_id"));
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
            interviewDaysDetails = jdbcTemplate.queryForObject(FIND_INTERVIEW_DAYS_DETAILS_ID,
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
                    }, candidateId);
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
    public Status findStatusById(Integer statusId) {
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
                                LOGGER.info("SQLState: " + e.getSQLState() + " Message: " + e.getMessage());
                                LOGGER.debug(e.getStackTrace(), e);
                            }
                            return value;
                        }
                    }, statusId
            );
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return Status.valueOf(value);
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
                candidate.setCourseId((int) row.get("course_id"));
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }

        return candidate;
    }

    @Override
    public Collection<Candidate> findAllByCourse(Integer courseId) {
        return findCandidates(FIND_ALL_BY_COURSE + courseId + " ORDER BY status_id DESC");
    }

    @Override
    public Collection<Candidate> findPartByCourse(Integer courseId, Integer with, Integer to) {
        return findCandidates(FIND_PART_BY_COURSE + courseId + " ORDER BY status_id DESC OFFSET " + with + " LIMIT " + to);
    }

    @Override
    public Integer getCandidateCount(int courseId) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Integer count = jdbcTemplate.queryForObject(SELECT_CANDIDATE_COUNT + courseId, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            });
            return count;

        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return 0;
    }

    @Override
    public Integer getCandidateCountByInterviewId(int interviewId) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Integer count = jdbcTemplate.queryForObject(SELECT_CANDIDATE_COUNT_BY_INTERVIEWDID + interviewId, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            });
            return count;

        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return 0;
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
            sendEmailService.sendEmailAboutCriticalError("ERROR in saveCandidate\n" + e.getMessage());
        }

        return false;
    }

    @Override
    public Map<Integer, String> findAllStatus() {
        Map<Integer, String> status = new HashMap<>();
        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_STATUS);
            for (Map row : rows) {
                status.put((int) row.get("id"), row.get("value").toString());
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return status;
    }

    @Override
    public boolean updateCandidateStatus(Integer candidateID, Integer newStatusID) {
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(UPDATE_STATUS, newStatusID, candidateID);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }
        return false;

    }

    @Override
    public Collection<Candidate> getAllMarked(User user) {
        Collection<Candidate> listCandidates = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_MARKED_BY_CURRENT_INTERVIEWER, user.getId());
            for (Map row : rows) {
                Candidate candidate = new Candidate();
                candidate.setId((int) row.get("id"));
                candidate.setStatusId((int) row.get("status_id"));
                candidate.setCourseId((int) row.get("course_id"));
                candidate.setInterviewResults(interviewResultDAO.findResultsByCandidateId(candidate.getId()));
                listCandidates.add(candidate);
            }
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
        }
        return listCandidates;
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
            sendEmailService.sendEmailAboutCriticalError("ERROR in saveOrUpdateAnswers\n" + e.getMessage());
        }
        return false;
    }


    @Override
    public Collection<Candidate> findAll() {
        return findCandidates(FIND_ALL);
    }

    public Collection<Candidate> findPart(Integer with, Integer to) {
        return findCandidates(FIND_PART + with + " LIMIT " + to);
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
                candidate.setStatus(findStatusById(candidate.getStatusId()));
                candidate.setCourseId((int) row.get("course_id"));
                listCandidates.add(candidate);
            }
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return listCandidates;

    }

    @Override
    public Collection<Candidate> pagination(Integer elementPage, Integer fromElement) {

        Collection<Candidate> listCandidates;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            listCandidates = jdbcTemplate.query(PAGINATION + elementPage + " offset " + fromElement, new RowMapper<Candidate>() {
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
                    Collection<InterviewResult> list = null;
                    try {
                        list = jdbcTemplate.query("SELECT ir.interviewer_id, ir.mark, ir.comment, r.value " +
                                        "FROM \"hr_system\".users u " +
                                        "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                                        "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                                        "FULL OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
                                        "FULL OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
                                        "WHERE rol.role_id = 5 and candidate.id = " + candidate.getId()
                                , new RowMapper<InterviewResult>() {
                                    @Override
                                    public InterviewResult mapRow(ResultSet resultSet, int i) throws SQLException {
                                        InterviewResult interviewResult = new InterviewResult();
                                        interviewResult.setInterviewerId(resultSet.getInt("interviewer_id"));
                                        interviewResult.setMark(resultSet.getInt("mark"));
                                        interviewResult.setComment(resultSet.getString("comment"));
                                        interviewResult.setRecommendation(Recommendation.valueOf(resultSet.getString("value")));
                                        return interviewResult;
                                    }
                                });

                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
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
    public Long getRows(List<Answer> expected) {
        if (expected.isEmpty()) {

            Long rows = null;
            try {
                jdbcTemplate = new JdbcTemplate(dataSource);
                rows = jdbcTemplate.queryForObject(
                        "WITH padik AS (SELECT  DISTINCT ON(candidate.id) *" +
                                "FROM \"hr_system\".users u " +
                                "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                                "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                                "LEFT OUTER JOIN \"hr_system\".interview_result ir ON candidate.id = ir.candidate_id " +
                                "LEFT OUTER JOIN \"hr_system\".recommendation r ON ir.recommendation_id = r.id " +
                                "LEFT OUTER JOIN \"hr_system\".status status ON candidate.status_id = status.id " +
                                "LEFT OUTER JOIN \"hr_system\".candidate_answer answer ON candidate.id = answer.candidate_id " +
                                "WHERE rol.role_id = 5  " +
                                ")SELECT COUNT(*) FROM padik ", new RowMapper<Long>() {
                            @Override
                            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                                Long row = resultSet.getLong("count");
                                return row;
                            }
                        });
            } catch (Exception e) {
                LOGGER.error("Error:" + e);
            }

            return rows;
        } else {


            String sql = "WITH padik AS " +
                    "(SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
                    "FROM \"hr_system\".users u " +
                    "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                    "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                    "LEFT OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
                    "LEFT OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
                    "WHERE rol.role_id = 5 ) " +
                    "SELECT COUNT(*) " +
                    "FROM padik WHERE padik.id IN ";

            for (Answer answer : expected) {
                sql = sql.concat("(SELECT candidate_id " +
                        " FROM \"hr_system\".candidate_answer" +
                        " WHERE question_id = " + answer.getQuestionId() + " AND value LIKE '%" +
                        answer.getValue() + "%' AND candidate_id IN ");
            }

            sql = sql.substring(0, sql.length() - 20);

            for (int i = 0; i < expected.size(); i++) {
                sql = sql.concat(")");
            }

//            sql = sql.concat(" ORDER BY padik.course_id DESC,padik.interviewer_id,padik.status_id DESC");

            Long rows = null;
            try {
                jdbcTemplate = new JdbcTemplate(dataSource);
                rows = jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {
                            @Override
                            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                                Long row = resultSet.getLong("count");
                                return row;
                            }
                        });
            } catch (Exception e) {
                LOGGER.error("Error:" + e);
            }

            return rows;


        }

//        Candidate candidate = new Candidate();
//
//        try {
//            jdbcTemplate = new JdbcTemplate(dataSource);
//            candidate = jdbcTemplate.queryForObject(LAST_ROWS, new RowMapper<Candidate>() {
//                @Override
//                public Candidate mapRow(ResultSet resultSet, int i) throws SQLException {
//                    Candidate candidate = new Candidate();
//                    candidate.setId(resultSet.getInt("id"));
//                    return candidate;
//                }
//            });
//
//        } catch (Exception e) {
//            LOGGER.error("Error: " + e);
//        }
//
//        return candidate.getId();
    }

    @Override
    public Collection<Candidate> findForSerach(Integer elementPage, Integer fromElement, String find) {

        int d = 2000000000;
        try {
            d = Integer.valueOf(find);
        } catch (Exception e) {

        }

        Collection<Candidate> listCandidates;
        try {
            jdbcTemplate = new JdbcTemplate(dataSource);
            listCandidates = jdbcTemplate.query(
                    "WITH padik AS (SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
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
                            "or candidate.id = " + d + "))SELECT * FROM padik  " +
                            "ORDER BY padik.course_id DESC, " +
                            "padik.status_id DESC,  padik.id " +
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

                            Collection<InterviewResult> list = null;
                            try {
                                list = jdbcTemplate.query("SELECT ir.interviewer_id, ir.mark, ir.comment, r.value " +
                                                "FROM \"hr_system\".users u " +
                                                "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                                                "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                                                "FULL OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
                                                "FULL OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
                                                "WHERE rol.role_id = 5 and candidate.id = " + candidate.getId()
                                        , new RowMapper<InterviewResult>() {
                                            @Override
                                            public InterviewResult mapRow(ResultSet resultSet, int i) throws SQLException {
                                                InterviewResult interviewResult = new InterviewResult();
                                                interviewResult.setInterviewerId(resultSet.getInt("interviewer_id"));
                                                interviewResult.setMark(resultSet.getInt("mark"));
                                                interviewResult.setComment(resultSet.getString("comment"));
                                                interviewResult.setRecommendation(Recommendation.valueOf(resultSet.getString("value")));
                                                return interviewResult;
                                            }
                                        });

                            } catch (Exception e) {
                                LOGGER.error(e);
                            }
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
                    "WITH padik AS (SELECT  DISTINCT ON(candidate.id) *" +
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
                            "or candidate.id = " + id + "))SELECT COUNT(*) FROM padik ", new RowMapper<Long>() {
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
    public Collection<Candidate> filterCandidates(List<Answer> expected, Integer limit, Integer offset) {
        if (expected.isEmpty()) {
            return pagination(limit, offset);
        }

        String sql = "WITH padik AS " +
                "(SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
                "FROM \"hr_system\".users u " +
                "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                "LEFT OUTER JOIN \"hr_system\".interview_result ir ON candidate.id = ir.candidate_id " +
                "LEFT OUTER JOIN \"hr_system\".recommendation r ON ir.recommendation_id = r.id " +
                "WHERE rol.role_id = 5 ) " +
                "SELECT  padik.id,padik.name,padik.email ,padik.surname, padik.patronymic,padik.status_id, padik.course_id , padik.interviewer_id, padik.mark, padik.comment, padik.value " +
                "FROM padik WHERE padik.id IN ";

        for (Answer answer : expected) {
            sql = sql.concat("(SELECT candidate_id " +
                    " FROM \"hr_system\".candidate_answer" +
                    " WHERE question_id = " + answer.getQuestionId() + " AND value LIKE '%" +
                    answer.getValue() + "%' AND candidate_id IN ");
        }

        sql = sql.substring(0, sql.length() - 20);

        for (int i = 0; i < expected.size(); i++) {
            sql = sql.concat(")");
        }

        sql = sql.concat(" ORDER BY padik.course_id DESC,padik.interviewer_id,padik.status_id DESC," +
                " padik.id LIMIT " + limit + " offset " + offset);

        Collection<Candidate> listCandidates = new ArrayList<>();
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
                    Collection<InterviewResult> list = null;
                    try {
                        list = jdbcTemplate.query("SELECT ir.interviewer_id, ir.mark, ir.comment, r.value " +
                                        "FROM \"hr_system\".users u " +
                                        "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                                        "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
                                        "FULL OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
                                        "FULL OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
                                        "WHERE rol.role_id = 5 and candidate.id = " + candidate.getId()
                                , new RowMapper<InterviewResult>() {
                                    @Override
                                    public InterviewResult mapRow(ResultSet resultSet, int i) throws SQLException {
                                        InterviewResult interviewResult = new InterviewResult();
                                        interviewResult.setInterviewerId(resultSet.getInt("interviewer_id"));
                                        interviewResult.setMark(resultSet.getInt("mark"));
                                        interviewResult.setComment(resultSet.getString("comment"));
                                        interviewResult.setRecommendation(Recommendation.valueOf(resultSet.getString("value")));
                                        return interviewResult;
                                    }
                                });

                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
                    candidate.setInterviewResults(list);

                    return candidate;
                }
            });
            return listCandidates;
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return listCandidates;
    }

}
