package ua.netcracker.hr_system.model.dao;

import ua.netcracker.hr_system.model.entity.ReportQuery;

import java.util.Collection;

/**
 * Created by Владимир on 28.04.2016.
 */
public interface ReportQueryDAO extends DAO {
    boolean remove(ReportQuery reportQuery);

    Collection<String> getDescriptions();

    ReportQuery getReportQueryByDescription(String description);
}
