package ua.netcracker.hr_system.model.dao.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.CourseSettingDAO;
import ua.netcracker.hr_system.model.entity.CourseSetting;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;


/**
 * Created by Legion on 24.04.2016.
 */
@Repository("courseSettingDao")
public class CourseSettingDAOImpl implements CourseSettingDAO<CourseSetting> {

    @Autowired()
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public Collection<CourseSetting> findAll() {
        return null;
    }

    @Override
    public CourseSetting find(int id) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".course_setting where id =" + id;

        CourseSetting courseSetting = jdbcTemplate.queryForObject(sql, new RowMapper<CourseSetting>() {
                    @Override
                    public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getCourseSetting(resultSet);
                    }
                }
        );
        return courseSetting;
    }
//1234567890
    private CourseSetting getCourseSetting(ResultSet resultSet) throws SQLException {
        CourseSetting courseSetting = new CourseSetting();
        courseSetting.setId(resultSet.getInt("id"));
        courseSetting.setInterviewEndDate(resultSet.getString("interview_end_date"));
        courseSetting.setInterviewStartDate(resultSet.getString("interview_start_date"));
        courseSetting.setRegistrationStartDate(resultSet.getString("registration_start_date"));
        courseSetting.setRegistrationEndDate(resultSet.getString("registration_end_date"));
        courseSetting.setCourseStartDate(resultSet.getString("course_start_date"));
        courseSetting.setInterviewTime(resultSet.getInt("interview_time_for_student"));
        courseSetting.setStudentCourseCount(resultSet.getInt("student_for_courses_count"));
        courseSetting.setStudentInterviewCount(resultSet.getInt("student_for_interview_count"));
        return courseSetting;
    }


    @Override
    public boolean insert(CourseSetting courseSetting) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("insert into \"hr_system\".course_setting" +
                        "( id, interview_end, interview_start, " +
                        "registration_start_date, registration_end_date, " +
                        "course_start_date, interview_time_for_student," +
                        " student_for_interview_count, student_for_courses_count) " +
                        "values (?,?,?,?,?,?,?,?,?)",
                courseSetting.getId(), null, null, null, null, null, 34,
                courseSetting.getStudentCourseCount(),
                courseSetting.getStudentInterviewCount());
        return true;
    }

    private String getNormaliDateInsert(String date) {
        return "'" + date + "'";
    }

    @Override
    public boolean update(CourseSetting courseSetting) {
        return true;

    }

    @Override
    public boolean remove(CourseSetting courseSetting) {
        return false;
    }



}
