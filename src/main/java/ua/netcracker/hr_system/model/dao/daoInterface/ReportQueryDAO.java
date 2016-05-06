package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.adminconfiguration.ReportQuery;

import java.util.Collection;

/**
 * Created by Владимир on 28.04.2016.
 */
public interface ReportQueryDAO extends DAO<Integer,ReportQuery> {
    Collection<String> getDescriptions();

    ReportQuery getReportQueryByDescription(String description);
}
