package ua.netcracker.hr_system.model.service.serviceInterface;

import java.util.Collection;

/**
 * Created by Владимир on 06.05.2016.
 */
public interface ReportService {
    Collection<Collection<String>> getReportByQuery(String sql);
    Collection<ReportQuery> getAllReports();
    boolean manageReportQuery(ReportQuery reportQuery, String status);
}
