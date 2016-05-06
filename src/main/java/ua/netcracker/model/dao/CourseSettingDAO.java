package ua.netcracker.model.dao;

/**
 * Created by Legion on 24.04.2016.
 */
public interface CourseSettingDAO<CourseSetting> extends DAO<CourseSetting> {

    public CourseSetting getLastSetting();

}
