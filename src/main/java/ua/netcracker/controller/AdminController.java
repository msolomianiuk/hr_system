package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.*;
import ua.netcracker.model.service.*;
import ua.netcracker.model.service.date.DateService;
import ua.netcracker.model.service.impl.CandidateServiceImpl;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdminController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private InterviewDaysDetailsService interviewDaysDetailsService;

    @Autowired
    private CourseSettingServiceImpl courseSettingService;

    @Autowired
    private CandidateServiceImpl candidateService;

    @Autowired
    private DateService dateService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String mainPage() {
        return "admin";
    }

    @RequestMapping(value = "/candidate", method = RequestMethod.GET)
    public String mainPageStudentsList() {
        return "candidate";
    }

    @RequestMapping(value = "/candidate/update_status")
    public void updateCandidateStatus(@RequestParam Integer candidateID, @RequestParam String status) {

        Candidate candidate = candidateService.getCandidateById(candidateID);
        candidate.setStatus(Status.valueOf(status));
        candidateService.updateCandidate(candidate);
    }

    @RequestMapping(value = "/add_role")
    public void addUserRole(@RequestParam Integer userID, @RequestParam String role) {

        User user = userService.get(userID);

        userService.addUserRole(user, Role.valueOf(role));
    }

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
        interviewDaysDetailsService.removeByCourseId(courseSetting.getId());
        interviewDaysDetailsService.addDateList(courseSetting);
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

    @RequestMapping(value = "/answer_candidate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Answer>> answerCandidate(
            @RequestParam String id
    ) {
        Collection<Answer> answers =
                candidateService.getAllCandidateAnswers(
                        candidateService.getCandidateById(Integer.parseInt(id)));
        if (answers.isEmpty()) {
            return (ResponseEntity<Collection<Answer>>) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(answers);
    }

    @RequestMapping(value = "/registration_period", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> period() {

        return ResponseEntity.ok(dateService.registrationPeriod());
    }


    @RequestMapping(value = "/get_candidate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Candidate> setCandidate(
            @RequestParam String id
    ) {

        Candidate candidate = candidateService.getCandidateById(Integer.parseInt(id));

        return ResponseEntity.ok(candidate);
    }

    @RequestMapping(value = "/getInterviewDetailsAddressList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getInterviewDetailsAddressList() {
        return ResponseEntity.ok(interviewDaysDetailsService.findAllInterviewDetailsAddress());
    }

    @RequestMapping(value = "/getInterviewDetailsByDate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InterviewDaysDetails> getInterviewDetailsByDate(
            @RequestParam String id
    ) {
        InterviewDaysDetails interviewDaysDetails = null;
        interviewDaysDetails = interviewDaysDetailsService.findById(interviewDaysDetailsService.getIdbyDate(id));
        if (interviewDaysDetails != null) {
            return ResponseEntity.ok(interviewDaysDetails);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getInterviewDetailsById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InterviewDaysDetails> getInterviewDetailsById(
            @RequestParam String id
    ) {
        InterviewDaysDetails interviewDaysDetails = null;
        interviewDaysDetails = interviewDaysDetailsService.findById(Integer.parseInt(id));
        if (interviewDaysDetails != null) {
            return ResponseEntity.ok(interviewDaysDetails);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/interview_details_update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InterviewDaysDetails> updateInterviewDaysDetails(
            @RequestParam String id,
            @RequestParam String start_time,
            @RequestParam String end_time,
            @RequestParam String address_id
    ) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails();
//        InterviewDaysDetails interviewDaysDetails = interviewDaysDetailsService.setInterviewDateDetails(
//                id,
//                start_time,
//                end_time,
//                addressService.findByAddress(address_id).getId()
//        );

        interviewDaysDetails.setId(Integer.parseInt(id));
        interviewDaysDetails.setStartTime(dateService.validTime(start_time));
        interviewDaysDetails.setEndTime(dateService.validTime(end_time));
        interviewDaysDetails.setAddressId(addressService.findByAddress(address_id).getId());
        if (interviewDaysDetails.getStartTime() != null && interviewDaysDetails.getEndTime() != null) {
            interviewDaysDetails.setCountStudents(dateService.quantityStudent(interviewDaysDetails));
            interviewDaysDetails.setCountPersonal(dateService.getPersonal(interviewDaysDetails));
            interviewDaysDetailsService.update(interviewDaysDetails);
            return ResponseEntity.ok(interviewDaysDetails);
        } else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    //---REST Controllers for Address---

    @RequestMapping(value = "/address_list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Address>> getAllAddress() {
        List<Address> addressList = (List<Address>) addressService.findAllSetting();
        if (addressList.isEmpty()) {
            return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
    }

    @RequestMapping(value = "/address_getAddress", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Address> getAddress(
            @RequestParam String id
    ) {
        Address address = addressService.findById(Integer.parseInt(id));
        if (address == null) {
            return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Address>(address, HttpStatus.OK);
    }

    @RequestMapping(value = "/address_insert", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Address> setAddress(
            @RequestParam String address,
            @RequestParam String roomCapacity
    ) {
        Address addressEntity = new Address();
        addressEntity.setAddress(address);
        addressEntity.setRoomCapacity(Integer.parseInt(roomCapacity));
        addressService.insert(addressEntity);
        if (addressEntity == null) {
            return ResponseEntity.accepted().body(addressEntity);
        }
        return ResponseEntity.ok(addressEntity);
    }

    @RequestMapping(value = "/address_update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Address> updateAddress(
            @RequestParam String id,
            @RequestParam String address,
            @RequestParam String roomCapacity
    ) {
        Address addressEntity = new Address(
                Integer.parseInt(id),
                address,
                Integer.parseInt(roomCapacity)
        );
        addressService.saveOrUpdate(addressEntity);
        if (addressEntity == null) {
            return ResponseEntity.accepted().body(addressEntity);
        }
        return ResponseEntity.ok(addressEntity);
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public String getEmailTemplatePage() {
        return "template";
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String getReportPage() {
        return "report";
    }

    @RequestMapping(value = "/service/getReportQuery", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<ReportQuery>> getAllShowReports() {
        Collection<ReportQuery> reports = reportService.getAllShowReports();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/setReportQuery", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ReportQuery> setReport(@RequestParam String id,
                                                 @RequestParam String description,
                                                 @RequestParam String query,
                                                 @RequestParam String show,
                                                 @RequestParam String status) {
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setId(Integer.valueOf(id));
        reportQuery.setQuery(query);
        reportQuery.setDescription(description);
        reportQuery.setShow(Boolean.valueOf(show));
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

    @RequestMapping(value = "/service/getAllReportQuery", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<ReportQuery>> getAllReports() {
        Collection<ReportQuery> report = reportService.getAllReports();
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

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
        if (emailTemplateService.manageEmailTemplate(emailTemplate, status)) {

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
