package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
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

    private static final String SELECT_ALL = "select * from \"hr_system\".course_setting";

    private static final String FIND_BY_ID = "select * from \"hr_system\".course_setting where id = ";

    private static final String INSERT = "insert into \"hr_system\".course_setting" +
                                         "( id, interview_end, interview_start, " +
                                         "registration_start_date, registration_end_date, " +
                                         "course_start_date, interview_time_for_student," +
                                         " student_for_interview_count, student_for_courses_count) " +
                                         "values (?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE = "update \"hr_system\".course_setting" +
                                         " SET student_for_interview_count = ? , " +
                                         "student_for_courses_count = ? , interview_time_for_student = ? , " +
                                         "interview_end = '?', interview_start = '?' , " +
                                         "registration_start_date = '?' , registration_end_date = '?'" +
                                         ", course_start_date = '?' WHERE id = ?";

    private static final String LAST_SETTING = "SELECT * from \"hr_system\".course_setting order by id desc limit 1";

    static final Logger LOGGER = Logger.getLogger(CourseSettingDAOImpl.class);

    @Autowired()
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Override
    public Collection<CourseSetting> findAll() {

        Collection<CourseSetting> allSetting = null;
        jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            allSetting = jdbcTemplate.query(SELECT_ALL, new RowMapper<CourseSetting>() {
                        @Override
                        public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {
                            return getSetting(resultSet);
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return allSetting;
    }

    @Override
    public CourseSetting find(int id) {

        CourseSetting courseSetting = null;
        jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            courseSetting = jdbcTemplate.queryForObject(FIND_BY_ID + id, new RowMapper<CourseSetting>() {
                        @Override
                        public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {
                            return getSetting(resultSet);
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return courseSetting;
    }

    @Override
    public CourseSetting getLastSetting() {

        jdbcTemplate = new JdbcTemplate(dataSource);

        CourseSetting courseSetting = jdbcTemplate.queryForObject(LAST_SETTING, new RowMapper<CourseSetting>() {
            @Override
            public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {
                return getSetting(resultSet);
            }
        });
        return courseSetting;
    }

    @Override
    public boolean insert(CourseSetting courseSetting) {

        jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            jdbcTemplate.update(INSERT,
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
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return false;
    }

    @Override
    public boolean update(CourseSetting courseSetting) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.update(UPDATE,
                    courseSetting.getStudentInterviewCount(),
                    courseSetting.getStudentCourseCount(),
                    courseSetting.getInterviewTime(),
                    courseSetting.getInterviewEndDate(),
                    courseSetting.getInterviewStartDate(),
                    courseSetting.getRegistrationStartDate(),
                    courseSetting.getRegistrationEndDate(),
                    courseSetting.getCourseStartDate(),
                    courseSetting.getId());
            return true;
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return false;

    }

    private CourseSetting getSetting(ResultSet resultSet) throws SQLException {
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

}
