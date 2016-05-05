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



    public Collection<CourseSetting> findAll() {
        return null;
    }

  
    public CourseSetting find(int id) {
        CourseSetting courseSetting = null;
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".course_setting where id = " + id;

        try {
            courseSetting = jdbcTemplate.queryForObject(sql, new RowMapper<CourseSetting>() {
                        @Override
                        public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {

                            return getCourseSetting(resultSet);
                        }
                    }
            );
        } catch (Exception e){
            e.printStackTrace();
        }
        return courseSetting;
    }

    private CourseSetting getCourseSetting(ResultSet resultSet) throws SQLException {
        CourseSetting courseSetting = new CourseSetting();
        courseSetting.setId(resultSet.getInt("id"));
        courseSetting.setInterviewEndDate(resultSet.getString("interview_end"));
        courseSetting.setInterviewStartDate(resultSet.getString("interview_start"));
        courseSetting.setRegistrationStartDate(resultSet.getString("registration_start_date"));
        courseSetting.setRegistrationEndDate(resultSet.getString("registration_end_date"));
        courseSetting.setCourseStartDate(resultSet.getString("course_start_date"));
        courseSetting.setInterviewTime(resultSet.getInt("interview_time_for_student"));
        courseSetting.setStudentCourseCount(resultSet.getInt("student_for_courses_count"));
        courseSetting.setStudentInterviewCount(resultSet.getInt("student_for_interview_count"));
        return courseSetting;
    }

 
    public boolean insert(CourseSetting courseSetting) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("insert into \"hr_system\".course_setting" +
                        "( id, interview_end, interview_start, " +
                        "registration_start_date, registration_end_date, " +
                        "course_start_date, interview_time_for_student," +
                        " student_for_interview_count, student_for_courses_count) " +
                        "values (?,?,?,?,?,?,?,?,?)",
                courseSetting.getId(),
                courseSetting.getInterviewEndDate(),
                courseSetting.getInterviewStartDate(),
                courseSetting.getRegistrationStartDate(),
                courseSetting.getRegistrationEndDate(),
                courseSetting.getCourseStartDate(),
                courseSetting.getInterviewTime(),
                courseSetting.getStudentInterviewCount(),
                courseSetting.getStudentCourseCount());
        return true;
    }


   
    public boolean update(CourseSetting courseSetting) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("UPDATE \"hr_system\".course_setting" +
                " SET student_for_interview_count = " + courseSetting.getStudentInterviewCount() +
                ", student_for_courses_count = " + courseSetting.getStudentCourseCount() +
                ", interview_time_for_student = " + courseSetting.getInterviewTime() +
                ", interview_end = '" + courseSetting.getInterviewEndDate()  +"'"+
                ", interview_start =' " + courseSetting.getInterviewStartDate() +"'" +
                ", registration_start_date = '" + courseSetting.getRegistrationStartDate() +"'" +
                ", registration_end_date = '" + courseSetting.getRegistrationEndDate()  +"'"+
                ", course_start_date = '" + courseSetting.getCourseStartDate() +"'" +
                " WHERE " +
                "id = " + courseSetting.getId());
        return true;

    }


    public boolean remove(CourseSetting courseSetting) {
        return false;
    }


    @Override
    public CourseSetting getLastSetting() {

        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * from \"hr_system\".course_setting order by id desc limit 1";
        CourseSetting courseSetting = jdbcTemplate.queryForObject(sql, new RowMapper<CourseSetting>() {
            @Override
            public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {
                return getCourseSetting(resultSet);
            }
        });
        return courseSetting;
    }
}
