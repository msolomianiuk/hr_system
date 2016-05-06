package ua.netcracker.hr_system.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.InterviewDaysDetailsDAO;
import ua.netcracker.hr_system.model.entity.InterviewDaysDetails;

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
    private static final String updateSql = "UPDATE hr_system.interview_days_details SET date=?, start_time=?, end_time=?, address_id=? WHERE id = ?";
    private static final String removeSql = "DELETE FROM \"hr_system\".interview_days_details WHERE id=?";
    private static final String getAllSql = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details";

    private InterviewDaysDetails interviewDaysDetails;

    @Autowired
    private DataSource dataSource;

//    @Override
//    public Collection<InterviewDaysDetails> findAll() {
//        return null;
//    }

    @Override
    public InterviewDaysDetails find(int id) {
        return null;
    }

    @Override
    public boolean insert(InterviewDaysDetails interviewDaysDetails) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".interview_days_details").
                usingColumns("course_id","date","start_time","end_time","address_id");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("course_id",interviewDaysDetails.getCourseId());
        insertParameter.addValue("date", interviewDaysDetails.getInterviewDate());
        insertParameter.addValue("start_time",interviewDaysDetails.getStartTime());
        insertParameter.addValue("end_time",interviewDaysDetails.getEndTime());
        insertParameter.addValue("address_id", interviewDaysDetails.getAddressId());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    @Override
    public boolean update(InterviewDaysDetails interviewDaysDetails) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return (jdbcTemplate.update(updateSql,interviewDaysDetails.getInterviewDate(),interviewDaysDetails.getStartTime(),
                interviewDaysDetails.getEndTime(),interviewDaysDetails.getAddressId(),interviewDaysDetails.getId())>0);
    }

    public boolean remove(long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update(removeSql, id)>0;
    }


    public boolean remove(InterviewDaysDetails elem) {
        return false;
    }

    @Override
    public List<InterviewDaysDetails> findAll(){
        List<InterviewDaysDetails> interviewList = null;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        interviewList = jdbcTemplate.query(getAllSql,
                new RowMapper<InterviewDaysDetails>() {
                    public InterviewDaysDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createInterviewWithResultSet(rs);
                    }
                });
        return interviewList;
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

    public String getStartTimeInterview(InterviewDaysDetails interviewDaysDetails){
        return interviewDaysDetails.getStartTime();
    }

    public String getEndTimeInterview(InterviewDaysDetails interviewDaysDetails){
        return interviewDaysDetails.getEndTime();
    }

    public String getDateofInterview(InterviewDaysDetails interviewDaysDetails){
        return interviewDaysDetails.getInterviewDate();
    }

}