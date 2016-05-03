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
    static final Logger LOGGER = Logger.getLogger(CandidateDAOImpl.class);
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

    public  boolean insert(Candidate candidate) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".candidate").
                usingColumns("user_id", "status_id", "course_id");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("user_id", candidate.getUserId());
        insertParameter.addValue("status_id", candidate.getStatusId());
        insertParameter.addValue("course_id", candidate.getCourseId());
        return simpleJdbcInsert.execute(insertParameter) == 5007? true : false;
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

    /**
     *
     * @param statusId
     * @return
     *
     */
    @Override

    public String findStatusById(Integer statusId) throws SQLException{
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".status WHERE id = " + statusId;
        final String status = jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("value");
                    }
                }
        );
        return null;
    }

    /**
     *
     * @param candidateId
     * @return
     */
    @Override
    public HashMap<Integer, Integer> getMark(Integer candidateId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        HashMap<Integer, Integer> mark = new HashMap<>();
        String sql = "Select mark, id_interviewer from \"hr_system\".interview_result Where candidate_id = " + candidateId;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows){
            mark.put((int)row.get("interviewer_id"), (int)row.get("mark"));
        }
            return mark;
        }




     @Override
    public HashMap<Integer, String> getRecommendation(Integer id) throws SQLException{
        jdbcTemplate = new JdbcTemplate(dataSource);
        HashMap<Integer, String> recommendation = new HashMap<>();
        String sql = "Select r.value, i.interviewer_id from \"hr_system\".interview_result i Inner JOIN \"hr_system\".recommendation r " +
                "ON i.recommendation_id = r.id Where i.candidate_id = " + id;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows){
            recommendation.put((int)row.get("interviewer_id"), (String)row.get("value"));
        }
        return recommendation;
    }

    @Override
    public HashMap<Integer, String> getResponse(Integer id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        HashMap<Integer, String> response = new HashMap<>();
        String sql = "Select response from \"hr_system\".interview_result Where i.candidate_id = " + id;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows){
            response.put((int)row.get("interviewer_id"), (String)row.get("value"));
        }
        return response;
    }

    @Override
    public int getInterviewDaysDetailsById(Integer id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "Select interview_days_details from \"hr_system\".candidate Where id = " + id;
        final Integer interviewDaysDetails = jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt("interview_days_details");
                    }
                }
        );
        return 0;
    }

    @Override
   public void insertAnswer(Candidate candidate) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        ArrayList<String> answerList;
        for (Map.Entry<Integer, Object> answer : candidate.getAnswerValue().entrySet()) {
            try {
                answerList = ((ArrayList<String>) answer.getValue());
                for (String answers : answerList){
                    executeInsertAnswer(candidate.getId(), answer.getKey(), answers);
                }
            }catch(ClassCastException e){
                executeInsertAnswer(candidate.getId(), answer.getKey(), (String)answer.getValue());
            }
        }
    }

    private void executeInsertAnswer(int candidateId, int questionId, String value){
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO \"hr_system\".candidate_answer(candidate_id, question_id, value) VALUES(" + candidateId +
                "," + questionId + ",'" + value + "')";
        jdbcTemplate.execute(sql);
    }

    @Override
    //not tested
    public void updateAnswer(Candidate candidate){
        deleteAnswer(candidate);
        insertAnswer(candidate);
    }

    @Override
    //not tested
    public void deleteAnswer(Candidate candidate){
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "DELETE FROM \"hr_system\".candidate_answer WHERE candidate_id = " + candidate.getId();
        jdbcTemplate.update(sql);
    }

    @Override
    public Collection<Candidate> findAll() {

        return null;
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
    public boolean update(Candidate candidate) {
        return false;
    }

    @Override
    public boolean remove(Candidate candidate) {
        return false;
    }
}
