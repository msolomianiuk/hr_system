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
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.utils.JdbcTemplateFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    CourseSettingService courseSettingService;

    private static final String UPDATE_SQL = "UPDATE hr_system.interview_days_details SET start_time=?, end_time=?, address_id=?, count_students=?, count_personal=? WHERE id = ?";
    private static final String REMOVE_SQL = "DELETE FROM \"hr_system\".interview_days_details WHERE id=?";
    private static final String FIND_ALL_SQL = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details ORDER BY id";
    private static final String FIND_SQL = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details WHERE id = ?";
    private static final String FIND_SQL_BY_DATE = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details WHERE date = ?";
    private static final String INSERT_SQL = "INSERT INTO \"hr_system\".interview_days_details(course_id, date, start_time, end_time, address_id, count_students, count_personal) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_DATE_SQL = "INSERT INTO \"hr_system\".interview_days_details(course_id, date) VALUES (?, ?);";
    private static final String REMOVE_SQL_BY_COURSE_ID = "DELETE FROM \"hr_system\".interview_days_details WHERE course_id = ?";
    private static final String INTERVIEW_DETAILS_ADDRESS_SQL =
            "SELECT hr_system.interview_days_details.id, date, start_time, end_time, hr_system.address.address, hr_system.address.room_capacity, count_students, count_personal" +
                    " FROM hr_system.interview_days_details" +
                    " LEFT JOIN hr_system.address" +
                    " ON hr_system.interview_days_details.address_id=hr_system.address.id" +
                    " WHERE course_id = ? " +
                    " ORDER BY hr_system.interview_days_details.date ";
    private static final String INTERVIEW_DETAILS_ADDRESS_BY_ID_SQL =
            "SELECT hr_system.interview_days_details.id, date, start_time, end_time, hr_system.address.address, hr_system.address.room_capacity, count_students, count_personal" +
                    " FROM hr_system.interview_days_details" +
                    " LEFT JOIN hr_system.address" +
                    " ON hr_system.interview_days_details.address_id=hr_system.address.id" +
                    " WHERE hr_system.interview_days_details.id = ? " +
                    " ORDER BY hr_system.interview_days_details.date ";

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

    public void removeByCourseId(int course_id) {
        try {
        jdbcTemplateFactory.getJdbcTemplate(dataSource).update(REMOVE_SQL_BY_COURSE_ID, course_id);
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
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
    public List<Map<String, Object>> findAllInterviewDetailsAddress() {
        List list = new ArrayList();
        try{
        list = jdbcTemplateFactory.getJdbcTemplate(dataSource).queryForList(INTERVIEW_DETAILS_ADDRESS_SQL, courseSettingService.getLastSetting().getId());
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return list;
    }

    @Override
    public Map<String, Object> findInterviewDetailsAddressById(Integer id) {
        Map map = new HashMap();
        try {
            map = jdbcTemplateFactory.getJdbcTemplate(dataSource).queryForMap(INTERVIEW_DETAILS_ADDRESS_BY_ID_SQL, id);
        } catch (Exception e){
            LOGGER.error("Error: " + e);
        }
        return map;
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