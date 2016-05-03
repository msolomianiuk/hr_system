package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.dao.daoImpl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.hr_system.model.entity.InterviewDaysDetails;

/**
 * Created by MaXim on 28.04.2016.
 */
@RestController
public class InterviewDaysDetailsRestController {

//    @Autowired(required = false)
    private InterviewDaysDetails interviewDaysDetails;

    @Autowired(required = false)
    private void setCourseSetting(InterviewDaysDetails interviewDaysDetails){
        this.interviewDaysDetails = interviewDaysDetails;
    }

    private InterviewDaysDetailsDAOImpl interviewDaysDetailsDao;

    @Autowired
    private void setInterviewDaysDetailsDao(InterviewDaysDetailsDAOImpl interviewDaysDetailsDao){
        this.interviewDaysDetailsDao = interviewDaysDetailsDao;
    }

    @RequestMapping(value = "/inter_day_req", method = RequestMethod.GET)
    public ResponseEntity<InterviewDaysDetails> setInterviewDaysDetails(
            @RequestParam String course_id,
            @RequestParam String interviewDate,
            @RequestParam String addressId,
            @RequestParam String employeesMaxCount,
            @RequestParam String candidateMaxCount
    ) {
        interviewDaysDetails.setCourseId(Integer.parseInt(course_id));
        interviewDaysDetails.setInterviewDate(interviewDate);
        interviewDaysDetails.setAddressId(Integer.parseInt(addressId));
        interviewDaysDetails.setEmployeesMaxCount(Integer.parseInt(employeesMaxCount));
        interviewDaysDetails.setCandidateMaxCount(Integer.parseInt(candidateMaxCount));
        interviewDaysDetailsDao.insert(interviewDaysDetails);
        if (interviewDaysDetails == null) {
            return ResponseEntity.accepted().body(interviewDaysDetails);
        }
        return ResponseEntity.ok(interviewDaysDetails);
    }

}
