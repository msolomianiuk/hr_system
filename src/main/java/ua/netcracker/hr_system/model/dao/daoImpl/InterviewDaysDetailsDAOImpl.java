package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.InterviewDaysDetailsDAO;
import ua.netcracker.hr_system.model.entity.InterviewDaysDetails;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * Created by MaXim on 01.05.2016.
 */
@Repository("interviewDao")
public class InterviewDaysDetailsDAOImpl implements InterviewDaysDetailsDAO {
    static final Logger LOGGER = Logger.getLogger(InterviewDaysDetailsDAOImpl.class);
    private InterviewDaysDetails interviewDaysDetails;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired(required = false)
    private void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Collection<InterviewDaysDetails> findAll() {
        return null;
    }


    public InterviewDaysDetails find(int id) {
        return null;
    }

    @Override
    public boolean insert(InterviewDaysDetails interviewDaysDetails) {
        String sql = "INSERT INTO \"hr_system\".interview_days_details(course_id,address_id,"
                +"employee_max_count,candidate_max_count,date) VALUES(?,?,?,?,?)";
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".interview_days_details").
                usingColumns("course_id","date","address_id","employee_max_count","candidate_max_count");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("course_id", interviewDaysDetails.getCourseId());
        insertParameter.addValue("date", interviewDaysDetails.getInterviewDate());
        insertParameter.addValue("address_id", interviewDaysDetails.getAddressId());
        insertParameter.addValue("employee_max_count", interviewDaysDetails.getEmployeesMaxCount());
        insertParameter.addValue("candidate_max_count", interviewDaysDetails.getCandidateMaxCount());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    @Override
    public boolean update(InterviewDaysDetails entity) {
        return false;
    }


    public boolean remove(InterviewDaysDetails elem) {
        return false;
    }


    public String getStartTimeInterview(){
        String time = "";
        return time;
    }
    public String getEndTimeInterview(){
        String time = "";
        return time;
    }
    public String getDateofInterview(){
        String date = "";
        return date;
    }

}
