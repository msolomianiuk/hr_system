package ua.netcracker.hr_system.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.impl.CourseSettingDAOImpl;
import ua.netcracker.hr_system.model.entity.CourseSetting;
import ua.netcracker.hr_system.model.service.date.MyDate;
import ua.netcracker.hr_system.model.service.CourseSettingService;

import java.util.Collection;

/**
 * Created by Legion on 26.04.2016.
 */
@Service()
public class CourseSettingServiceImpl implements CourseSettingService {

    private MyDate date;

    @Autowired(required = false)
    private void setMyDate(MyDate date) {
        this.date = date;
    }

    @Autowired
    private CourseSettingDAOImpl courseSettingDAO;

    @Autowired(required = false)
    private void setCourseSettingDAO(CourseSettingDAOImpl courseSettingDAO) {
        this.courseSettingDAO = courseSettingDAO;
    }

    @Override
    public CourseSetting getLastSetting() {
        CourseSetting courseSetting = courseSettingDAO.getLastSetting();
        courseSetting.setCourseStartDate(courseSetting.getCourseStartDate());
        courseSetting.setInterviewEndDate(courseSetting.getInterviewEndDate());
        courseSetting.setInterviewStartDate(courseSetting.getInterviewStartDate());
        courseSetting.setRegistrationEndDate(courseSetting.getRegistrationEndDate());
        courseSetting.setRegistrationStartDate(courseSetting.getRegistrationStartDate());
        return courseSetting;
    }

    @Override
    public CourseSetting findById(int id) {
        return courseSettingDAO.find(id);
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
        CourseSetting courseSetting = new CourseSetting();
        courseSetting.setId(date.getCurrentYear() * 100 + date.getCurrentMonth());
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
            courseSettingDAO.insert(courseSetting);
        } else {
            courseSettingDAO.update(courseSetting);
        }
    }

    public boolean insert(CourseSetting courseSetting) {
        return courseSettingDAO.insert(courseSetting) == true ? true : false;
    }

    @Override
    public void delete(long id) {

    }


}
