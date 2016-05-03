package ua.netcracker.hr_system.model.service.serviceInterface;


import ua.netcracker.hr_system.model.entity.CourseSetting;

/**
 * Created by Legion on 26.04.2016.
 */
public interface CourseSettingService extends EntityService<CourseSetting> {

    CourseSetting findById(int id);
}
