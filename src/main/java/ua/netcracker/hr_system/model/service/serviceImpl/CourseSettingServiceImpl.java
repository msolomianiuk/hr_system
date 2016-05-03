package ua.netcracker.hr_system.model.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.daoImpl.CourseSettingDAOImpl;
import ua.netcracker.hr_system.model.entity.CourseSetting;
import ua.netcracker.hr_system.model.service.serviceInterface.CourseSettingService;

import java.util.Collection;

/**
 * Created by Legion on 26.04.2016.
 */
@Service("courseSettingService")
public class CourseSettingServiceImpl implements CourseSettingService {
    @Autowired
    private CourseSettingDAOImpl courseSettingDao;

    @Autowired(required = false)
    private void setCourseSettingDao(CourseSettingDAOImpl courseSettingDao) {
        this.courseSettingDao = courseSettingDao;
    }

    @Override
    public CourseSetting findById(int id) {
        return courseSettingDao.find(id);
    }

    @Override
    public Collection<CourseSetting> findAllSetting() {
        return courseSettingDao.findAll();
    }


    @Override
    public void saveOrUpdate(CourseSetting courseSetting) {
//        if (findById(courseSetting.getId()) == null) {
        courseSettingDao.insert(courseSetting);
//        } else {
//            courseSettingDAO.update(courseSetting);
//        }
    }

    public boolean insert(CourseSetting courseSetting) {
        return courseSettingDao.insert(courseSetting) == true ? true : false;
    }

    @Override
    public void delete(long id) {

    }


}
