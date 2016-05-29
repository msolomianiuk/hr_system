package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.dao.CourseSettingDAO;
import ua.netcracker.model.dao.ReportDAO;
import ua.netcracker.model.dao.ReportQueryDAO;
import ua.netcracker.model.entity.ReportQuery;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.service.ExcelService;
import ua.netcracker.model.service.ReportService;

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
    private CandidateDAO candidate;
    @Autowired
    private CourseSettingDAO courseSetting;
    @Autowired
    private ReportDAO reportDao;

    private static final String FILE_FORMAT = ".xls";
    private static final String HEADER_EXCEL_MEDIA_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String HEADER_MEDIA_TYPE = "must-revalidate, post-check=0, pre-check=0";

    private Collection<Collection<String>> report;
    private String description;

    @Override
    public Collection<Collection<String>> getStudentsByCourseId(int courseId) {
        report = reportDao.getStudentsByCourseId(courseId);
        return report;
    }

    @Override
    public Collection<Collection<String>> getStudentsByCourseIdAndStatus(int courseId, String status) {
        report = reportDao.getStudentsByCourseIdAndStatusId(courseId, Status.valueOf(status).getId());
        return report;
    }

    @Override
    public Collection<Collection<String>> getReportByQuery(String sql, String description) {
        if (!checkSQL(sql)) {
            return new ArrayList<>();
        }
        this.description = description;
        report = reportDao.getReport(sql);
        return report;
    }

    private boolean checkSQL(String sql) {
        if (sql == null) return false;
        if (sql.isEmpty()) return false;
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
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(HEADER_EXCEL_MEDIA_TYPE));
        String date = getCurrentDate();
        String filename = description + " " + date + FILE_FORMAT;
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl(HEADER_MEDIA_TYPE);
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
    public boolean updateReportQuery(ReportQuery reportQuery) {
        return reportQueryDao.update(reportQuery);
    }

    @Override
    public boolean deleteReportQuery(ReportQuery reportQuery) {
        return reportQueryDao.delete(reportQuery);
    }

    @Override
    public boolean insertReportQuery(ReportQuery reportQuery) {
        return reportQueryDao.insert(reportQuery);
    }

    @Override
    public Collection<Integer> getCourses() {
        return courseSetting.getAllCourseIdDesk();
    }

    @Override
    public Map<Integer, String> getStatuses() {
        return candidate.findAllStatus();
    }
}
