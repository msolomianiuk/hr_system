package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.CandidateService;

/**
 * Created by Alex on 11.05.2016.
 */
@Controller
public class InterviewController  {
    @Autowired
    private CandidateService candidateService;

    @RequestMapping(value = "/service/getStudent", method = RequestMethod.GET)
    public ResponseEntity<Candidate> getCandidate(@RequestParam int id) {
        Candidate candidate = candidateService.getCandidateById(id);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/setStudentInterviewResult", method = RequestMethod.GET)
    @ResponseBody
    public boolean setStudentInterviewResult(@RequestParam Integer candidateId,
                                             @RequestParam Integer interviewerId,
                                             @RequestParam Integer mark,
                                             @RequestParam String recommendation,
                                             @RequestParam String comment
    ) {
        return candidateService.saveInterviewResult(candidateId, interviewerId, mark, recommendation, comment);
    }
}
