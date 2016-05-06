package ua.netcracker.hr_system.model.service.serviceInterface;


import ua.netcracker.hr_system.model.entity.CourseSetting;

/**
 * Created by Legion on 26.04.2016.
 */
public interface CourseSettingService extends EntityService<CourseSetting> {

    public CourseSetting getLastSetting();

   public CourseSetting findById(int id);

    public CourseSetting setCourseSetting (String registrationStartDate,
                                           String registrationEndDate,
                                           String interviewStartDate,
                                           String interviewEndDate,
                                           String  courseStartDate,
                                           String interviewTimeForStudent,
                                           String studentForInterviewCount,
                                           String studentForCourseCount);
}
