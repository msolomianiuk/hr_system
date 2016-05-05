package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.CandidateDAO;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("candidateDao")
public class CandidateDAOImpl implements CandidateDAO {
    private static final Logger LOGGER = Logger.getLogger(CandidateDAOImpl.class);

    private User user;

    @Autowired
    private DataSource dataSource;
    @Override
    public Candidate getCandidateByID(Integer candidateID){
        if (candidateID > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "select * from \"hr_system\".candidate WHERE id = " + candidateID;
                Candidate candidate = jdbcTemplate.queryForObject(sql, new RowMapper<Candidate>() {
                            @Override
                            public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
                               Candidate candidate = new Candidate();
                                candidate.setID(rs.getInt("id"));
                                candidate.setUserID(rs.getInt("user_id"));
                                candidate.setStatusID(rs.getInt("status_id"));
                                candidate.setInterviewDaysDetails(rs.getInt("interview_days_details_id"));
                                candidate.setCourseID(rs.getInt("course_id"));
                                return candidate;
                            }
                        }
                );
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return null;

    }
    @Override
    public List<Candidate> getAllProfiles() {
        List list = null;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT u.id , u.name , u.email, u.surname, u.patronymic " +
                    "FROM \"hr_system\".users u " +
                    "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                    "WHERE rol.role_id=" + 5;
            List<Candidate> listOfCandidate = jdbcTemplate.query(sql, new RowMapper<Candidate>() {

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
            list = listOfCandidate;
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }

        return list;
    }
    @Override
    public String getStatusById(Integer statusID) {
        if (statusID > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "select * from \"hr_system\".status WHERE id = " + statusID;
                final String status = jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
                            @Override
                            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                                return rs.getString("value");
                            }
                        }
                );
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return null;
    }
    @Override
    public HashMap<Integer, Integer> getMarks(Integer candidateID) {
        HashMap<Integer, Integer> mark = new HashMap<>();
        if (candidateID > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "Select mark, id_interviewer from \"hr_system\".interview_result " +
                        "Where candidate_id = " + candidateID;
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
                for (Map row : rows) {
                    mark.put((int) row.get("interviewer_id"), (int) row.get("mark"));
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return mark;
    }
    @Override
    public HashMap<Integer, String> getRecommendations(Integer ID) {
        HashMap<Integer, String> recommendation = new HashMap<>();
        if (ID > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "Select r.value, i.interviewer_id from \"hr_system\".interview_result i " +
                        "Inner JOIN \"hr_system\".recommendation r " +
                        "ON i.recommendation_id = r.id Where i.candidate_id = " + ID;
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
                for (Map row : rows) {
                    recommendation.put((int) row.get("interviewer_id"), (String) row.get("value"));
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return recommendation;
    }
    @Override
    public HashMap<Integer, String> getResponses(Integer ID) {
        HashMap<Integer, String> response = new HashMap<>();
        if (ID > 0) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "Select response from \"hr_system\".interview_result Where i.candidate_id = " + ID;
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map row : rows) {
                response.put((int) row.get("interviewer_id"), (String) row.get("value"));
            }
        }
        return response;
    }
    @Override
    public int getInterviewDayDetailsByID(Integer ID) {
        if (ID > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "Select interview_days_details from \"hr_system\".candidate Where id = " + ID;
                Integer interviewDaysDetails = jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
                            @Override
                            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                                return rs.getInt("interview_days_details");
                            }
                        }
                );
                return interviewDaysDetails;
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return 0;
    }
    @Override
    public void deleteAnswers(Candidate candidate) {
        if (candidate != null) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "DELETE FROM \"hr_system\".candidate_answer WHERE candidate_id = " + candidate.getID();
                jdbcTemplate.update(sql);
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
    }
    public Candidate getCandidateByUserID(Integer userID) {
        Candidate candidate = new Candidate();
        if (userID > 0) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "Select * from \"hr_system\".candidate Where user_id = " + userID;
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
                for (Map row : rows) {
                    candidate.setID((int) row.get("id"));
                    candidate.setUserID((int) row.get("user_Id"));
                    candidate.setInterviewDaysDetails((int) row.get("interview_days_details_id"));
                    candidate.setStatusID((int) row.get("status_id"));
                    candidate.setCourseID((int) row.get("course_id"));
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return candidate;
    }
    public Map<Integer, String> getAllCandidateAnswers(Candidate candidate) {
        Map<Integer, String> listAnswers = new HashMap<Integer, String>();
        if (candidate != null) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "select * from \"hr_system\".candidate_answer where candidate_id = " + candidate.getID();
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
                for (Map row : rows) {
                    listAnswers.put((int) row.get("question_id"), (String) row.get("value"));
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
        return listAnswers;
    }

    public void insertAnswers(Candidate candidate) {
        if (candidate != null) {
            try {
                ArrayList<String> answerList;
                for (Map.Entry<Integer, Object> answer : candidate.getAnswers().entrySet()) {
                    try {
                        answerList = ((ArrayList<String>) answer.getValue());
                        for (String answers : answerList) {
                            executeInsertAnswer(candidate.getID(), answer.getKey(), answers);
                        }
                    } catch (ClassCastException e) {
                        LOGGER.debug(e.getStackTrace());
                        LOGGER.info(e.getMessage());
                        executeInsertAnswer(candidate.getID(), answer.getKey(), (String) answer.getValue());
                    }
                }
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
    }
    public boolean insertCandidate(Candidate candidate) {
        if (candidate != null) {
            try {
                SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                        withTableName("\"hr_system\".candidate").
                        usingColumns("user_id", "status_id","course_id")
                        .usingGeneratedKeyColumns("id");;
                MapSqlParameterSource insertParameter = new MapSqlParameterSource();
                insertParameter.addValue("user_id", candidate.getUserID());
                insertParameter.addValue("status_id", candidate.getStatusID());
                insertParameter.addValue("course_id", candidate.getCourseID());
                simpleJdbcInsert.execute(insertParameter);

            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
            return true;
        }
        return false;
    }


    private void executeInsertAnswer(int candidateID, int questionID, String value) {
        if ((candidateID > 0) && (questionID > 0) && (value != null)) {
            try {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                String sql = "INSERT INTO \"hr_system\".candidate_answer(candidate_id, question_id, value) " +
                        "VALUES(" + candidateID +
                        "," + questionID + ",'" + value + "')";
                jdbcTemplate.execute(sql);
            } catch (Exception e) {
                LOGGER.debug(e.getStackTrace());
                LOGGER.info(e.getMessage());
            }
        }
    }

    @Override
    public List<User> getInterviewers(Candidate candidate) {
        return null;
    }

    @Override
    public String getCandidateAnswer(Integer candidateID, Integer questionID) {
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
    public boolean insert(Candidate entity) {
        return false;
    }

    @Override
    public boolean update(Candidate entity) {
        return false;
    }


}
