package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.InterviewDaysDetailsDAO;
import ua.netcracker.model.entity.InterviewDaysDetails;
import ua.netcracker.model.utils.JdbcTemplateFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by MaXim on 01.05.2016.
 */
@Repository("interviewDao")
public class InterviewDaysDetailsDAOImpl implements InterviewDaysDetailsDAO {
    static final Logger LOGGER = Logger.getLogger(InterviewDaysDetailsDAOImpl.class);

    @Autowired
    JdbcTemplateFactory jdbcTemplateFactory;

    @Autowired
    private DataSource dataSource;

    private static final String UPDATE_SQL = "UPDATE hr_system.interview_days_details SET start_time=?, end_time=?, address_id=?, count_students=?, count_personal=? WHERE id = ?";
    private static final String REMOVE_SQL = "DELETE FROM \"hr_system\".interview_days_details WHERE id=?";
    private static final String FIND_ALL_SQL = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details ORDER BY id";
    private static final String FIND_SQL = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details WHERE id = ?";
    private static final String FIND_SQL_BY_DATE = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details WHERE date = ?";
    private static final String INSERT_SQL = "INSERT INTO \"hr_system\".interview_days_details(course_id, date, start_time, end_time, address_id, count_students, count_personal) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_DATE_SQL = "INSERT INTO \"hr_system\".interview_days_details(course_id, date) VALUES (?, ?);";
    private static final String FIND_BY_ID = "SELECT id FROM hr_system.interview_days_details WHERE date = ";
    @Override
    public InterviewDaysDetails find(int id) {
        InterviewDaysDetails interviewDaysDetails = null;
        try {
            interviewDaysDetails = jdbcTemplateFactory.getJdbcTemplate(dataSource).queryForObject(FIND_SQL,
                    new RowMapper<InterviewDaysDetails>() {
                        public InterviewDaysDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return createInterviewWithResultSet(rs);
                        }
                    },
                    id);
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return interviewDaysDetails;
    }

    @Override
    public InterviewDaysDetails findByDate(String date) {
        InterviewDaysDetails interviewDaysDetails = null;
        try {
            interviewDaysDetails = jdbcTemplateFactory.getJdbcTemplate(dataSource).queryForObject(FIND_SQL_BY_DATE,
                    new RowMapper<InterviewDaysDetails>() {
                        public InterviewDaysDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return createInterviewWithResultSet(rs);
                        }
                    },
                    date);
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return interviewDaysDetails;
    }

    @Override
    public boolean insert(InterviewDaysDetails interviewDaysDetails) {
        try{
        return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(INSERT_SQL,
                interviewDaysDetails.getCourseId(),
                interviewDaysDetails.getInterviewDate(),
                interviewDaysDetails.getStartTime(),
                interviewDaysDetails.getEndTime(),
                interviewDaysDetails.getAddressId(),
                interviewDaysDetails.getCountStudents(),
                interviewDaysDetails.getCountPersonal()
        ) > 0;
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        try {
            return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(REMOVE_SQL, id) > 0;
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return false;
    }

    @Override
    public boolean update(InterviewDaysDetails interviewDaysDetails) {
        try {
            return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(UPDATE_SQL,
                    interviewDaysDetails.getStartTime(),
                    interviewDaysDetails.getEndTime(),
                    interviewDaysDetails.getAddressId(),
                    interviewDaysDetails.getCountStudents(),
                    interviewDaysDetails.getCountPersonal(),
                    interviewDaysDetails.getId()
            ) > 0;
        } catch (Exception e){
            LOGGER.error(e);
            return false;
        }
    }

    @Override
    public boolean insertDate(InterviewDaysDetails interviewDaysDetails) {
        try {
            return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(INSERT_DATE_SQL,
                    interviewDaysDetails.getCourseId(),
                    interviewDaysDetails.getInterviewDate()
            ) > 0;
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return false;
    }

    @Override
    public List<InterviewDaysDetails> findAll() {
        List<InterviewDaysDetails> interviewList = null;
        try{
        interviewList = jdbcTemplateFactory.getJdbcTemplate(dataSource).query(FIND_ALL_SQL,
                new RowMapper<InterviewDaysDetails>() {
                    public InterviewDaysDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createInterviewWithResultSet(rs);
                    }
                });
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return interviewList;
    }

    @Override
    public int getIdbyDate(String date){
        String sql = FIND_SQL_BY_DATE + "\'" + date + "\'";
        try {
            return (Integer) jdbcTemplateFactory.getJdbcTemplate(dataSource).queryForObject(sql, Integer.class);
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return 0;
    }

    private InterviewDaysDetails createInterviewWithResultSet(ResultSet rs) throws SQLException {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails();
        interviewDaysDetails.setId(rs.getInt("id"));
        interviewDaysDetails.setCourseId(rs.getInt("course_id"));
        interviewDaysDetails.setInterviewDate(rs.getString("date"));
        interviewDaysDetails.setStartTime(rs.getString("start_time"));
        interviewDaysDetails.setEndTime(rs.getString("end_time"));
        interviewDaysDetails.setAddressId(rs.getInt("address_id"));
        return interviewDaysDetails;
    }

}