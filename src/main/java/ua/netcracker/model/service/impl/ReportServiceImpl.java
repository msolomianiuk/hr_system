package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.ReportQueryDAO;
import ua.netcracker.model.entity.ReportQuery;
import ua.netcracker.model.service.ReportService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Владимир on 04.05.2016.
 */
@Service()
public class ReportServiceImpl implements ReportService {

    static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportQueryDAO reportQueryDao;
    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<Collection<String>> getReportByQuery(String sql) {
        if(!checkSQL(sql)){
            return new ArrayList<>();
        }
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            Collection<Collection<String>> report = new ArrayList<>();
            report.add(rows.get(0).keySet());
            for (Map row : rows) {
                report.add(row.values());
            }
            return report;
        }
        catch(DataAccessException ex){
            LOGGER.trace("User request ", ex);
            return new ArrayList<>();
        }
    }

    private boolean checkSQL(String sql) {
        if(sql ==null)return false;
        sql = sql.toLowerCase();
        if(sql.contains("create")) return false;
        if(sql.contains("drop")) return false;
        if(sql.contains("delete")) return false;
        if(sql.contains("update")) return false;
        if(sql.contains("alter")) return false;
        return true;
    }

    @Override
    public Collection<ReportQuery> getAllShowReports() {

        return reportQueryDao.findAllByImportant(true);
    }

    @Override
    public Collection<ReportQuery> getAllReports() {
        return reportQueryDao.findAll();
    }

    @Override
    public boolean manageReportQuery(ReportQuery reportQuery, String status) {
        switch (status) {
            case "delete":
                reportQueryDao.updateImportance(reportQuery,false);
                break;
            case "insert":
                reportQueryDao.insert(reportQuery);
                //reportQueryDao.updateImportance(reportQuery,true);
                break;
            case "update":
                reportQueryDao.update(reportQuery);
                break;
            default:
                return false;
        }
        return true;
    }
}
