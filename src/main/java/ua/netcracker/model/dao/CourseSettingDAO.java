package ua.netcracker.model.dao;

import java.util.Collection;

/**
 * Created by Legion on 24.04.2016.
 */
public interface CourseSettingDAO<CourseSetting> extends DAO<CourseSetting> {

    public CourseSetting getLastSetting();

    Collection<Integer> getAllCourseId();

    Collection<Integer> getAllCourseIdDesk();
}
