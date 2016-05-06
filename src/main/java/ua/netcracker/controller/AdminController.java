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
import ua.netcracker.model.entity.Address;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.InterviewDaysDetails;
import ua.netcracker.model.service.AddressService;
import ua.netcracker.model.service.InterviewDaysDetailsService;
import ua.netcracker.model.service.impl.AddressServiceImpl;
import ua.netcracker.model.service.impl.CandidateServiceImpl;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;
import ua.netcracker.model.service.impl.InterviewDaysDetailsServiceImpl;

import java.util.List;


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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String mainPage() {
        return "admin";
    }

    @RequestMapping(value = "/candidate", method = RequestMethod.GET)
    public String mainPageStudentsList() {
        return "candidate";
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

    //---Controllers for InterviewDaysDetails---

    @RequestMapping(value = "/service/interviewDetails", method = RequestMethod.GET)
    public String getInterviewDays() {
        return "interview_days_details";
    }

    //---REST Controllers for InterviewDaysDetails---

    @RequestMapping(value = "/interview_details_list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<InterviewDaysDetails>> getAll() {
        List<InterviewDaysDetails> interview = (List <InterviewDaysDetails>) interviewDaysDetailsService.findAllSetting();
        if (interview.isEmpty()) {
            return new ResponseEntity<List<InterviewDaysDetails>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<InterviewDaysDetails>>(interview, HttpStatus.OK);
    }

    @RequestMapping(value = "/interview_details_insert", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InterviewDaysDetails> setInterviewDaysDetails(
            @RequestParam String date,
            @RequestParam String start_time,
            @RequestParam String end_time,
            @RequestParam String address_id
    ) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails();
        interviewDaysDetails.setCourseId(1);
        interviewDaysDetails.setInterviewDate(date);
        interviewDaysDetails.setStartTime(start_time);
        interviewDaysDetails.setEndTime(end_time);
        interviewDaysDetails.setAddressId(Integer.parseInt(address_id));
        interviewDaysDetailsService.insert(interviewDaysDetails);
        if (interviewDaysDetails == null) {
            return ResponseEntity.accepted().body(interviewDaysDetails);
        }
        return ResponseEntity.ok(interviewDaysDetails);
    }

    @RequestMapping(value = "/interview_details_delete", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InterviewDaysDetails> removeInterviewController(
            @RequestParam String id
    ) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails();
        interviewDaysDetailsService.delete(Integer.parseInt(id));
        if (interviewDaysDetails == null) {
            return ResponseEntity.accepted().body(interviewDaysDetails);
        }
        return ResponseEntity.ok(interviewDaysDetails);
    }

    @RequestMapping(value = "/interview_details_update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InterviewDaysDetails> updateInterviewController(
            @RequestParam String id,
            @RequestParam String date,
            @RequestParam String start_time,
            @RequestParam String end_time,
            @RequestParam String address_id

    ) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails(
                Integer.parseInt(id),
                date,
                start_time,
                Integer.parseInt(address_id),
                end_time
        );
        interviewDaysDetailsService.saveOrUpdate(interviewDaysDetails);
        if (interviewDaysDetails == null) {
            return ResponseEntity.accepted().body(interviewDaysDetails);
        }
        return ResponseEntity.ok(interviewDaysDetails);
    }

    //---Controllers for Address---

    @RequestMapping(value = "/service/interviewDetails/address", method = RequestMethod.GET)
    public String getAddressPage(){return "address";}

    //---REST Controllers for Address---

    @RequestMapping(value = "/address_list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Address>> getAllAddressController() {
        List<Address> addressList = (List <Address>) addressService.findAllSetting();
        if (addressList.isEmpty()) {
            return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
    }

    @RequestMapping(value = "/address_insert", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Address> setAddressMethod(
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

    @RequestMapping(value = "/address_delete", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Address> removeAddress(
            @RequestParam String id
    ) {
        Address addressEntity = new Address();
        addressEntity.setId(Integer.parseInt(id));
        addressService.delete(addressEntity.getId());
        if (addressEntity == null) {
            return ResponseEntity.accepted().body(addressEntity);
        }
        return ResponseEntity.ok(addressEntity);
    }

    @RequestMapping(value = "/address_update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Address> updateAddressController(
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
}
