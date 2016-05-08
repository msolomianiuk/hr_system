package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.ReportQueryDAO;
import ua.netcracker.model.entity.ReportQuery;
import ua.netcracker.model.service.ReportService;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Владимир on 04.05.2016.
 */
@Service()
public class ReportServiceImpl implements ReportService {

    static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportQueryDAO reportQueryDao;

    @Override
    public Collection<Collection<String>> getReportByQuery(String sql) {
        try {
            DataSource dataSource = (DataSource) new InitialContext().lookup("jdbc/HRSystemPool");
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            return convertToJSON(rs);
        } catch (Exception e) {
            LOGGER.trace("Error in user request", e);
            return null;
        }
    }

    public Collection<Collection<String>> convertToJSON(ResultSet rs) throws SQLException {
        Collection<Collection<String>> rows = new ArrayList<>();
        Collection<String> row = new ArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getMetaData().getColumnName(i));
        }
        rows.add(row);
        while (rs.next()) {
            row = new ArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            rows.add(row);
        }
        return rows;
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
