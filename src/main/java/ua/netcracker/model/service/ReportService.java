package ua.netcracker.model.service;


import org.springframework.http.HttpHeaders;
import ua.netcracker.model.entity.ReportQuery;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Владимир on 06.05.2016.
 */
public interface ReportService {
    Collection<Collection<String>> getStudentsByCourseId(int courseId);

    Collection<Collection<String>> getStudentsByCourseIdAndStatus(int courseId, String status);

    Collection<Collection<String>> getReportByQuery(String sql, String description);

    byte[] getXLSX() throws IOException;

    HttpHeaders getHeaders();

    Collection<ReportQuery> getAllShowReports();

    Collection<ReportQuery> getAllReports();

    boolean updateReportQuery(ReportQuery reportQuery);

    boolean deleteReportQuery(ReportQuery reportQuery);

    boolean insertReportQuery(ReportQuery reportQuery);

    Collection<Integer> getCourses();

    Map<Integer, String> getStatuses();

    Collection<ReportQuery> getDeletedReports();

    boolean checkQuery(String sql);
}
