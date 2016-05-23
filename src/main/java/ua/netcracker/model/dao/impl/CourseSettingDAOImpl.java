package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.netcracker.model.dao.CourseSettingDAO;
import ua.netcracker.model.entity.CourseSetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by Legion on 24.04.2016.
 */
@Repository("courseSettingDao")
public class CourseSettingDAOImpl implements CourseSettingDAO<CourseSetting> {

    private static final String SELECT_ALL = "SELECT * FROM \"hr_system\".course_setting";

    private static final String FIND_BY_ID = "SELECT * FROM \"hr_system\".course_setting WHERE id = ";

    private static final String INSERT = "INSERT INTO \"hr_system\".course_setting" +
            "( id, interview_end, interview_start, " +
            "registration_start_date, registration_end_date, " +
            "course_start_date, interview_time_for_student," +
            " student_for_interview_count, student_for_courses_count) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE = "UPDATE \"hr_system\".course_setting" +
            " SET student_for_interview_count = ? , " +
            "student_for_courses_count = ? , interview_time_for_student = ? , " +
            "interview_end = ? , interview_start = ? , " +
            "registration_start_date = ? , registration_end_date = ?" +
            ", course_start_date = ? WHERE id = ?";

    private static final String LAST_SETTING = "SELECT * FROM \"hr_system\".course_setting ORDER BY id DESC limit 1";

    private static final String FIND_ALL_COURSE_ID = "SELECT id FROM \"hr_system\".course_setting";
    static final Logger LOGGER = Logger.getLogger(CourseSettingDAOImpl.class);

    @Autowired()
    private JdbcTemplate jdbcTemplate;

    @Override
    public Collection<CourseSetting> findAll() {

        Collection<CourseSetting> allSetting = null;

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


        CourseSetting courseSetting = jdbcTemplate.queryForObject(LAST_SETTING, new RowMapper<CourseSetting>() {
            @Override
            public CourseSetting mapRow(ResultSet resultSet, int i) throws SQLException {
                return getSetting(resultSet);
            }
        });

        return courseSetting;
    }

    @Override
    public Collection<Integer> getAllCourseId() {
        List<Integer> courseId = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(FIND_ALL_COURSE_ID);
        try {
            for (Map row : rows) {
                courseId.add((int) row.get("id"));
            }
        }catch (Exception e){
            LOGGER.error(e);
            return courseId;
        }
        return courseId;
    }

    @Override
    public boolean insert(CourseSetting courseSetting) {

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
