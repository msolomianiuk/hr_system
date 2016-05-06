package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.CourseSetting;
import ua.netcracker.hr_system.model.service.serviceImpl.CandidateServiceImpl;
import ua.netcracker.hr_system.model.service.serviceImpl.CourseSettingServiceImpl;


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
    public String getInterviewDays() {
        return "inter_day";
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
    public String adminSetting() {
        return "admin_settings";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getAllUsers() {

        return "admin_setting";
    }

    @RequestMapping(value = "/admin_setting", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CourseSetting> setCourseSetting(
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


}
