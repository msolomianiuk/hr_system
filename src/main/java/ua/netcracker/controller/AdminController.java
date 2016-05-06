package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.EmailTemplate;
import ua.netcracker.model.entity.ReportQuery;
import ua.netcracker.model.service.EmailTemplateService;
import ua.netcracker.model.service.ReportService;
import ua.netcracker.model.service.impl.CandidateServiceImpl;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;

import java.util.Collection;


@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdminController {

    @Autowired
    private CourseSettingServiceImpl courseSettingService;

    @Autowired
    private CandidateServiceImpl candidateService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String mainPage() {
        return "admin";
    }

    @RequestMapping(value = "/candidate", method = RequestMethod.GET)
    public String mainPageStudentsList() {
        return "candidate";
    }

    @RequestMapping(value = "/service/inter", method = RequestMethod.GET)
    public String getInterviewDays(Model model) {
        return null;
    }
   /* @RequestMapping(value = "/service/inter", method = RequestMethod.GET)
    public String getInterviewDays() {
        return "inter_day";
    }*/

    @RequestMapping(value = "/interview_schedule", method = RequestMethod.GET)
    public String mainPageS() {

        return "interview_schedule";
    }

    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public String mainPageS2() {

        return "personal";
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String mainPageS21() {
        return "students";
    }

    @RequestMapping(value = "/admin_settings", method = RequestMethod.GET)
    public String mainPageSS() {
        return "admin_settings";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getAllUsers() {

        return "admin_setting";
    }

    @RequestMapping(value = "/admin_setting", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CourseSetting> setCourseSettingFromFromEnd(
            @RequestParam String registrationStartDate,
            @RequestParam String registrationEndDate,
            @RequestParam String interviewStartDate,
            @RequestParam String interviewEndDate,
            @RequestParam String courseStartDate,
            @RequestParam String interviewTimeForStudent,
            @RequestParam String studentForInterviewCount,
            @RequestParam String studentForCourseCount

    ) {

        CourseSetting courseSetting = courseSettingService.setCourseSetting
                (registrationStartDate,
                        registrationEndDate,
                        interviewStartDate,
                        interviewEndDate,
                        courseStartDate,
                        interviewTimeForStudent,
                        studentForInterviewCount,
                        studentForCourseCount);

        courseSettingService.saveOrUpdate(courseSetting);

        if (courseSetting.getCourseStartDate() == null) {
            return ResponseEntity.accepted().body(courseSetting);
        }
        return ResponseEntity.ok(courseSetting);
    }

    @RequestMapping(value = "/up_setting", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CourseSetting> getSetting() {
        return ResponseEntity.ok(courseSettingService.getLastSetting());
    }

    @RequestMapping(value = "/get_candidate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Candidate> setCandidate(
            @RequestParam String id
    ) {
        Candidate candidate = candidateService.getCandidateByID(Integer.parseInt(id));

        return ResponseEntity.ok(candidate);
    }

    @RequestMapping(value = "/service/inter/address", method = RequestMethod.GET)
    public String getAddressPage(Model model){return "address";}

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public String getEmailTemplatePage(){return "template";}

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String getReportPage(){return "report";}

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/service/getReportQuery", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<ReportQuery>> getAllReports() {
        Collection<ReportQuery> reports = reportService.getAllReports();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/setReportQuery", method = RequestMethod.GET)
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<Collection<Collection<String>>> getReport(@RequestParam String query) {
        Collection<Collection<String>> report = reportService.getReportByQuery(query);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Autowired
    private EmailTemplateService emailTemplateService;

    @RequestMapping(value = "/service/getEmailTemplates", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<EmailTemplate>> getAllEmailTemplates() {
        Collection<EmailTemplate> emailTemplates = emailTemplateService.getAllEmailTemplates();
        if (emailTemplates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(emailTemplates, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/setEmailTemplates", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EmailTemplate> setAllEmailTemplates(@RequestParam String id,
                                                              @RequestParam String description,
                                                              @RequestParam String template,
                                                              @RequestParam String status) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setTemplate(template);
        emailTemplate.setDescription(description);
        emailTemplate.setId(Integer.valueOf(id));
        if(emailTemplateService.manageEmailTemplate(emailTemplate,status)){

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
