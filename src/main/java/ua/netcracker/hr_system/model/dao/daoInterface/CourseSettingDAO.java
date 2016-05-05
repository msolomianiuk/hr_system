package ua.netcracker.hr_system.model.dao.daoInterface;

/**
 * Created by Legion on 24.04.2016.
 */
public interface CourseSettingDAO<CourseSetting> extends DAO<Long, CourseSetting> {

    public int getLastIdSetting();

}
