package ua.netcracker.hr_system.model.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.daoImpl.CourseSettingDAOImpl;
import ua.netcracker.hr_system.model.entity.CourseSetting;
import ua.netcracker.hr_system.model.service.date.MyDate;
import ua.netcracker.hr_system.model.service.serviceInterface.CourseSettingService;

import java.util.Collection;

/**
 * Created by Legion on 26.04.2016.
 */
@Service()
public class CourseSettingServiceImpl implements CourseSettingService {

    private CourseSetting courseSetting;
    @Autowired(required = false)
    private void setCourseSetting(CourseSetting courseSetting){
        this.courseSetting = courseSetting;
    }

    private MyDate date;

    @Autowired(required = false)
    private void setMyDate (MyDate date){
        this.date = date;
    }

    @Autowired
    private CourseSettingDAOImpl courseSettingDAO;

    @Autowired(required = false)
    private void setCourseSettingDAO(CourseSettingDAOImpl courseSettingDAO) {
        this.courseSettingDAO = courseSettingDAO;
    }

    @Override
    public CourseSetting getIdLastSetting() {
        courseSetting = courseSettingDAO.find(courseSettingDAO.getLastIdSetting());
        courseSetting.setCourseStartDate(date.getSFTime(courseSetting.getCourseStartDate()));
        courseSetting.setInterviewEndDate(date.getSFTime(courseSetting.getInterviewEndDate()));
        courseSetting.setInterviewStartDate(date.getSFTime(courseSetting.getInterviewStartDate()));
        courseSetting.setRegistrationEndDate(date.getSFTime(courseSetting.getRegistrationEndDate()));
        courseSetting.setRegistrationStartDate(date.getSFTime(courseSetting.getRegistrationStartDate()));
        return  courseSetting ;
    }

    @Override
    public CourseSetting findById(int id) {
        return courseSettingDAO.find(id);
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
