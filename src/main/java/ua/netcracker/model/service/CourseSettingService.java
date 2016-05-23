package ua.netcracker.model.service;


import ua.netcracker.model.entity.CourseSetting;

import java.util.Collection;

/**
 * Created by Legion on 26.04.2016.
 */
public interface CourseSettingService extends EntityService<CourseSetting> {

    public CourseSetting getLastSetting();

   public CourseSetting findById(int id);

    Collection<Integer> getAllCourseId();
    public CourseSetting setCourseSetting (String registrationStartDate,
                                           String registrationEndDate,
                                           String interviewStartDate,
                                           String interviewEndDate,
                                           String  courseStartDate,
                                           String interviewTimeForStudent,
                                           String studentForInterviewCount,
                                           String studentForCourseCount);

}


