package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.hr_system.model.entity.CourseSetting;
import ua.netcracker.hr_system.model.service.serviceImpl.CourseSettingServiceImpl;

import java.util.Random;

/**
 * Class for processing requests related to administrator
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdminController {

    private CourseSettingServiceImpl courseSettingService;
    private CourseSetting courseSetting;

    @Autowired(required = false)
    private void setCourseSettingService(CourseSettingServiceImpl courseSettingService) {
        this.courseSettingService = courseSettingService;
    }

    @Autowired(required = false)
    private void setCourseSetting(CourseSetting courseSetting) {
        this.courseSetting = courseSetting;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String mainPage(Model model) {
        return "admin";
    }

    @RequestMapping(value = "/candidate", method = RequestMethod.GET)
    public String mainPageStudentsList(Model model) {
        return "candidate";
    }

    @RequestMapping(value = "admin/service/inter", method = RequestMethod.GET)
    public String getInterviewDays(Model model) {
        return "inter_day";
    }

    @RequestMapping(value = "/interview_schedule", method = RequestMethod.GET)
    public String mainPageS(Model model) {

        return "interview_schedule";
    }

    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public String mainPageS2(Model model) {

        return "personal";
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String mainPageS21(Model model) {
        return "students";
    }

    @RequestMapping(value = "/admin_settings", method = RequestMethod.GET)
    public String mainPageSS(Model model) {
        return "admin_settings";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getAllUsers(Model model) {

        return "course_setting";
    }

    @RequestMapping(value = "/course_setting", method = RequestMethod.GET)
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
        Random rand = new Random();

        int randomNum = rand.nextInt((100 - 10) + 1) + 23;
        courseSetting.setId(randomNum + 5000);
        courseSetting.setRegistrationStartDate(registrationStartDate);
        courseSetting.setInterviewEndDate(interviewEndDate);
        courseSetting.setInterviewStartDate(interviewStartDate);
        courseSetting.setRegistrationEndDate(registrationEndDate);
        courseSetting.setCourseStartDate(courseStartDate);
        courseSetting.setInterviewTime (Integer.parseInt(interviewTimeForStudent));
        courseSetting.setStudentCourseCount (Integer.parseInt(studentForCourseCount));
        courseSetting.setStudentInterviewCount (Integer.parseInt(studentForInterviewCount));

        courseSettingService.saveOrUpdate(courseSetting);

        if (courseSetting.getCourseStartDate() == null) {
            return ResponseEntity.accepted().body(courseSetting);
        }
        return ResponseEntity.ok(courseSetting);
    }
}
