package ua.netcracker.model.dao;

import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Владимир on 26.05.2016.
 */
public interface ReportDAO {
    Collection<Collection<String>> getReport(String sql);

    Collection<Collection<String>> getStudentsByCourseId(int courseId);

    Collection<Collection<String>>  getStudentsByCourseIdAndStatusId(int courseId, int statusId);
}
