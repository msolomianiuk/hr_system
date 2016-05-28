package ua.netcracker.model.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Legion on 08.05.2016.
 */
@Service
public class PaginationServiceImp {
    private static final Logger LOGGER = Logger.getLogger(PaginationServiceImp.class);
    private static final String PAGINATION = "WITH cand AS " +
            "(SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
            "FROM \"hr_system\".users u " +
            "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
            "JOIN \"hr_system\".candidate candidate ON candidate.user_id = u.id " +
            "LEFT OUTER JOIN \"hr_system\".interview_result ir on candidate.id = ir.candidate_id " +
            "LEFT OUTER JOIN \"hr_system\".recommendation r on ir.recommendation_id = r.id " +
            "WHERE rol.role_id = 5 ) " +
            "SELECT  cand.id,cand.name,cand.email ,cand.surname, cand.patronymic,cand.status_id, cand.course_id , cand.interviewer_id, cand.mark, cand.comment, cand.value " +
            "FROM cand ";

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
                    "WITH cand AS (SELECT  DISTINCT ON(candidate.id) candidate.id,u.name,u.email ,u.surname, u.patronymic,candidate.status_id, candidate.course_id , ir.interviewer_id, ir.mark, ir.comment, r.value " +
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

                            Collection<InterviewResult> list = null;
                            try {
                                list = jdbcTemplate.query("SELECT * FROM \"hr_system\".users u " +
                                                "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                                                "FULL OUTER JOIN \"hr_system\".interview_result ir on ir.interviewer_id = u.id " +
                                                "where ir.candidate_id = " + candidate.getId()
                                        , new RowMapper<InterviewResult>() {
                                            @Override
                                            public InterviewResult mapRow(ResultSet resultSet, int i) throws SQLException {
                                                InterviewResult interviewResult = new InterviewResult();
                                                interviewResult.setInterviewerId(resultSet.getInt("role_id"));
                                                interviewResult.setMark(resultSet.getInt("mark"));
                                                interviewResult.setComment(resultSet.getString("comment"));
                                                interviewResult.setRecommendation(Recommendation.values()[resultSet.getInt("recommendation_id") - 1]);
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

    public Collection<Candidate> filterCandidates(List<Answer> expected, Integer limit, Integer offset) {

        int element = 0;
        if ((offset - 1) != 0) {
            element = (offset - 1) * limit;
        }

        return filtration(expected, limit, element);
    }

    public Collection<Candidate> pagination(Integer limitRows, Integer fromElement) {
        int element = 0;
        if ((fromElement - 1) != 0) {
            element = (fromElement - 1) * limitRows;
        }
        return paginationCandidates(limitRows, element);
    }

    public List paginationList(List entity, String nextPage, PagedListHolder pagedListHolder) {


        ArrayList list = (ArrayList) entity;

        Comparator<Candidate> comp = new Comparator<Candidate>() {
            @Override
            public int compare(Candidate p1, Candidate p2) {
                return p1.getUser().getName().compareTo(p2.getUser().getName());
            }
        };

        Collections.sort(list, comp);

        pagedListHolder.setSource(list);
        pagedListHolder.getSort();
        if (nextPage.equals("next")) {
            pagedListHolder.nextPage();
        } else if (nextPage.equals("previous")) {
            pagedListHolder.previousPage();
        } else {
            pagedListHolder.setPage(Integer.parseInt(nextPage));
        }

        return pagedListHolder.getPageList();

    }


    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;


    public Collection<Candidate> paginationCandidates(Integer elementPage, Integer fromElement) {

        Collection<Candidate> listCandidates;
        try {
            String sql = PAGINATION + "ORDER BY cand.course_id DESC,cand.interviewer_id,cand.status_id DESC, cand.id LIMIT ";
            jdbcTemplate = new JdbcTemplate(dataSource);
            listCandidates = jdbcTemplate.query(sql + elementPage + " offset " + fromElement, new RowMapper<Candidate>() {
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
                        list = jdbcTemplate.query("SELECT * FROM \"hr_system\".users u " +
                                        "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                                        "FULL OUTER JOIN \"hr_system\".interview_result ir on ir.interviewer_id = u.id " +
                                        "where ir.candidate_id = " + candidate.getId()
                                , new RowMapper<InterviewResult>() {
                                    @Override
                                    public InterviewResult mapRow(ResultSet resultSet, int i) throws SQLException {
                                        InterviewResult interviewResult = new InterviewResult();
                                        interviewResult.setInterviewerId(resultSet.getInt("role_id"));
                                        interviewResult.setMark(resultSet.getInt("mark"));
                                        interviewResult.setComment(resultSet.getString("comment"));
                                        interviewResult.setRecommendation(Recommendation.values()[resultSet.getInt("recommendation_id") - 1]);
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

    private Collection<Candidate> filtration(List<Answer> expected, Integer limit, Integer offset) {
        if (expected.isEmpty()) {
            return paginationCandidates(limit, offset);
        }

        String sql = PAGINATION.concat(" WHERE cand.id IN ");

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

        sql = sql.concat(" ORDER BY cand.course_id DESC,cand.interviewer_id,cand.status_id DESC," +
                " cand.id LIMIT " + limit + " offset " + offset);

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
                                        interviewResult.setRecommendation(Recommendation.values()[resultSet.getInt("recommendation_id") - 1]);
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

    public Long getRows(List<Answer> expected) {
        String sql = "SELECT count(*) FROM \"hr_system\".candidate";
        if (!expected.isEmpty()) {
            sql +=" WHERE candidate.id IN ";

            for (Answer answer : expected) {
                sql = sql.concat("(SELECT candidate_id " +
                        " FROM \"hr_system\".candidate_answer" +
                        " WHERE question_id = " + answer.getQuestionId() + " AND value LIKE '%" +
                        answer.getValue() + "%' AND candidate_id IN ");
            }

            //cut off last "AND candidate_id IN "
            sql = sql.substring(0, sql.length() - 20);

            for (int i = 0; i < expected.size(); i++) {
                sql = sql.concat(")");
            }

        }

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