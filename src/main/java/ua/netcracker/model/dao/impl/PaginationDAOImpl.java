package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.InterviewResultDAO;
import ua.netcracker.model.entity.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 28.05.2016.
 */
@Repository("PaginationDAOImpl")
public class PaginationDAOImpl {
    private static final Logger LOGGER = Logger.getLogger(PaginationDAOImpl.class);
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

    @Autowired
    private InterviewResultDAO interviewResultDAO;
    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

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

                            Collection<InterviewResult> list = interviewResultDAO.findResultsByCandidateId(candidate.getId());
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

    public Collection<Candidate> paginationCandidates(Integer elementPage, Integer fromElement) {

        Collection<Candidate> listCandidates = new ArrayList<>();
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
                    Collection<InterviewResult> list;
                    list = interviewResultDAO.findResultsByCandidateId(candidate.getId());
                    candidate.setInterviewResults(list);

                    return candidate;
                }
            });

        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return listCandidates;

    }

    public Collection<Candidate> filtration(List<Answer> expected, Integer limit, Integer offset) {
        if (expected.isEmpty()) {
            return paginationCandidates(limit, offset);
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
                    Collection<InterviewResult> list = new ArrayList<>();
                    try {
                        list.addAll(jdbcTemplate.query("SELECT ir.interviewer_id, ir.mark, ir.comment, r.value " +
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
                                interviewResult.setRecommendation(Recommendation.values()[resultSet.getInt("recommendation_id") - 1]);
                                return interviewResult;
                            }
                        }));

                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
                    candidate.setInterviewResults(list);

                    return candidate;
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error:" + e);
        }

        return listCandidates;
    }

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
    }

}
