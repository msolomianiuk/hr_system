package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.entity.ReportQuery;
import ua.netcracker.hr_system.model.service.ReportService;

import java.util.Collection;

/**
* Created by Владимир on 04.05.2016.
*/
@RestController
public class AdminReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/service/getReportQuery", method = RequestMethod.GET)
    public ResponseEntity<Collection<ReportQuery>> getAllReports() {
        Collection<ReportQuery> reports = reportService.getAllReports();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/setReportQuery", method = RequestMethod.GET)
    public ResponseEntity<ReportQuery> setAllReports(@RequestParam String id,
                                                     @RequestParam String description,
                                                     @RequestParam String query,
                                                     @RequestParam String status) {
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setQuery(query);
        reportQuery.setDescription(description);
        reportQuery.setId(Integer.valueOf(id));
        if (reportService.manageReportQuery(reportQuery, status)) {
            return new ResponseEntity<>(reportQuery, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/service/createReport", method = RequestMethod.GET)
    public ResponseEntity<Collection<Collection<String>>> getReport(@RequestParam String query) {
        Collection<Collection<String>> report = reportService.getReportByQuery(query);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
