package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.ReportQueryDAO;
import ua.netcracker.model.entity.ReportQuery;
import ua.netcracker.model.service.ExcelService;
import ua.netcracker.model.service.ReportService;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Владимир on 04.05.2016.
 */
@Service()
public class ReportServiceImpl implements ReportService {

    static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportQueryDAO reportQueryDao;
    @Autowired
    private ExcelService excelService;

    @Autowired
    private DataSource dataSource;

    private Collection<Collection<String>> report;
    private String description;

    @Override
    public Collection<Collection<String>> getReportByQuery(String sql, String description) {
        if (!checkSQL(sql)) {
            return new ArrayList<>();
        }
        this.description = description;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            report = new ArrayList<>();
            report.add(rows.get(0).keySet());
            for (Map row : rows) {
                report.add(row.values());
            }
            return report;
        } catch (DataAccessException ex) {
            LOGGER.trace("User request ", ex);
            return new ArrayList<>();
        }
    }

    private boolean checkSQL(String sql) {
        if (sql == null) return false;
        sql = sql.toLowerCase();
        if (sql.contains("create")) return false;
        if (sql.contains("drop")) return false;
        if (sql.contains("delete")) return false;
        if (sql.contains("update")) return false;
        if (sql.contains("alter")) return false;
        return true;
    }

    @Override
    public byte[] getXLSX() throws IOException {
        return excelService.toXLSX(report, description);
    }

    @Override
    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        String date = getCurrentDate();
        String filename = description + " " + date+".xlsx";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(Calendar.getInstance().getTime());
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
                return reportQueryDao.update(reportQuery);
            case "insert":
                return reportQueryDao.insert(reportQuery);
            case "update":
                return reportQueryDao.update(reportQuery);
            case "new":
                return true;
            default:
                return false;
        }
    }
}
