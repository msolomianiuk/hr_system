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

    private static final String UPDATE_SQL = "UPDATE hr_system.interview_days_details SET date=?, start_time=?, end_time=?, address_id=? WHERE id = ?";
    private static final String REMOVE_SQL = "DELETE FROM \"hr_system\".interview_days_details WHERE id=?";
    private static final String FIND_ALL_SQL = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details ORDER BY id";
    private static final String FIND_SQL = "SELECT id, course_id, date, start_time, end_time, address_id FROM \"hr_system\".interview_days_details WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO \"hr_system\".interview_days_details(course_id, date, start_time, end_time, address_id) VALUES (?, ?, ?, ?, ?);";


    @Override
    public InterviewDaysDetails find(int id) {
        InterviewDaysDetails interviewDaysDetails = null;
        interviewDaysDetails = jdbcTemplateFactory.getJdbcTemplate(dataSource).queryForObject(FIND_SQL,
                new RowMapper<InterviewDaysDetails>() {
                            public InterviewDaysDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                                return createInterviewWithResultSet(rs);
                            }
                        },
                id);
        return interviewDaysDetails;
    }

    @Override
    public boolean insert(InterviewDaysDetails interviewDaysDetails) {
        return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(INSERT_SQL,
                interviewDaysDetails.getCourseId(),
                interviewDaysDetails.getInterviewDate(),
                interviewDaysDetails.getStartTime(),
                interviewDaysDetails.getEndTime(),
                interviewDaysDetails.getAddressId()) > 0;
    }

    @Override
    public boolean update(InterviewDaysDetails interviewDaysDetails) {
        try {
            return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(UPDATE_SQL,
                    interviewDaysDetails.getInterviewDate(),
                    interviewDaysDetails.getStartTime(),
                    interviewDaysDetails.getEndTime(),
                    interviewDaysDetails.getAddressId(),
                    interviewDaysDetails.getId()) > 0;
        } catch (Exception e){
            LOGGER.error(e);
            return false;
        }
    }

    public boolean remove(int id) {
        return jdbcTemplateFactory.getJdbcTemplate(dataSource).update(REMOVE_SQL, id) > 0;
    }

    @Override
    public List<InterviewDaysDetails> findAll() {
        List<InterviewDaysDetails> interviewList = null;
        interviewList = jdbcTemplateFactory.getJdbcTemplate(dataSource).query(FIND_ALL_SQL,
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

}