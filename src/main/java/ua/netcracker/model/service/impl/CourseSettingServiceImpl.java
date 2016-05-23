package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.impl.CourseSettingDAOImpl;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.date.DateService;

import java.util.Collection;

/**
 * Created by Legion on 26.04.2016.
 */
@Service("course setting service")
public class CourseSettingServiceImpl implements CourseSettingService {

    private static final Logger LOGGER = Logger.getLogger(CourseSettingServiceImpl.class);

    @Autowired
    private DateService dateService;

    @Autowired
    private CourseSettingDAOImpl courseSettingDAO;

    @Autowired(required = false)
    private void setCourseSettingDAO(CourseSettingDAOImpl courseSettingDAO) {
        this.courseSettingDAO = courseSettingDAO;
    }

    @Override
    public CourseSetting getLastSetting() {
        return courseSettingDAO.getLastSetting();
    }

    @Override
    public CourseSetting findById(int id) {
        return courseSettingDAO.find(id);
    }

    @Override
    public Collection<Integer> getAllCourseId() {
        return courseSettingDAO.getAllCourseId();
    }

    @Override
    public CourseSetting setCourseSetting(String registrationStartDate,
                                          String registrationEndDate,
                                          String interviewStartDate,
                                          String interviewEndDate,
                                          String courseStartDate,
                                          String interviewTimeForStudent,
                                          String studentForInterviewCount,
                                          String studentForCourseCount) {
        return validDate(registrationStartDate, registrationEndDate,
                interviewStartDate, interviewEndDate, courseStartDate,
                interviewTimeForStudent, studentForInterviewCount, studentForCourseCount);
    }

    private CourseSetting validDate(String registrationStartDate, String registrationEndDate, String interviewStartDate, String interviewEndDate, String courseStartDate, String interviewTimeForStudent, String studentForInterviewCount, String studentForCourseCount) {
        try {
            if (dateValidator(registrationStartDate) && dateValidator(registrationEndDate) &&
                    dateValidator(interviewStartDate) && dateValidator(interviewEndDate) &&
                    dateValidator(courseStartDate)) {
                if (interviewEndDate.equals(interviewStartDate) || registrationEndDate.equals(registrationStartDate) ||
                        interviewEndDate.equals(registrationEndDate) || interviewStartDate.equals(registrationStartDate) ||
                        interviewStartDate.equals(registrationStartDate) || interviewStartDate.equals(registrationEndDate) ||
                        courseStartDate.equals(registrationStartDate) || courseStartDate.equals(registrationEndDate) ||
                        courseStartDate.equals(interviewStartDate) || courseStartDate.equals(interviewEndDate)
                        ) {
                    return null;
                } else {
                    if (dateService.getDate(courseStartDate).isAfter(dateService.getDate(interviewEndDate)) &&
                            dateService.getDate(courseStartDate).isAfter(dateService.getDate(interviewStartDate)) &&
                            dateService.getDate(courseStartDate).isAfter(dateService.getDate(registrationEndDate)) &&
                            dateService.getDate(courseStartDate).isAfter(dateService.getDate(registrationStartDate)) &&

                            dateService.getDate(interviewEndDate).isAfter(dateService.getDate(interviewStartDate)) &&
                            dateService.getDate(interviewEndDate).isAfter(dateService.getDate(registrationEndDate)) &&
                            dateService.getDate(interviewEndDate).isAfter(dateService.getDate(registrationStartDate)) &&

                            dateService.getDate(interviewStartDate).isAfter(dateService.getDate(registrationEndDate)) &&
                            dateService.getDate(interviewStartDate).isAfter(dateService.getDate(registrationStartDate)) &&

                            dateService.getDate(registrationEndDate).isAfter(dateService.getDate(registrationStartDate))) {

                        return getCourseSetting(registrationStartDate, registrationEndDate,
                                interviewStartDate, interviewEndDate,
                                courseStartDate, interviewTimeForStudent,
                                studentForInterviewCount, studentForCourseCount);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    private CourseSetting getCourseSetting(String registrationStartDate, String registrationEndDate, String interviewStartDate, String interviewEndDate, String courseStartDate, String interviewTimeForStudent, String studentForInterviewCount, String studentForCourseCount) {
        CourseSetting courseSetting = new CourseSetting();
        courseSetting.setId(dateService.getCurrentYear() * 100 + dateService.getCurrentMonth());

        courseSetting.setRegistrationStartDate(registrationStartDate);
        courseSetting.setInterviewEndDate(interviewEndDate);
        courseSetting.setInterviewStartDate(interviewStartDate);
        courseSetting.setRegistrationEndDate(registrationEndDate);
        courseSetting.setCourseStartDate(courseStartDate);

        courseSetting.setInterviewTime(Integer.parseInt(interviewTimeForStudent));
        courseSetting.setStudentCourseCount(Integer.parseInt(studentForCourseCount));
        courseSetting.setStudentInterviewCount(Integer.parseInt(studentForInterviewCount));
        return courseSetting;
    }

    @Override
    public Collection<CourseSetting> findAllSetting() {
        return courseSettingDAO.findAll();
    }

    @Override
    public void saveOrUpdate(CourseSetting courseSetting) {
        if (findById(courseSetting.getId()) == null) {
            insert(courseSetting);
        } else {
            update(courseSetting);
        }
    }

    private void insert(CourseSetting courseSetting) {
        courseSettingDAO.insert(courseSetting);
    }

    private void update (CourseSetting courseSetting){
        courseSettingDAO.update(courseSetting);
    }

    @Override
    public void delete(long id) {

    }

    public boolean dateValidator(String date) {
        String[] d = date.split("-");
        return dateService.isDateValid(d[0], d[1], d[2]);
    }

}
