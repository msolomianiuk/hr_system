package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.CandidateService;

/**
 * Class for processing requests related to dev
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class DevController {
    @Autowired
    private CandidateService candidateService;

    @RequestMapping(value = "dev/interview_page", method = RequestMethod.GET)
    public String devMainPage() {
        return "interviewPage";
    }

    @RequestMapping(value = "dev/getStudent", method = RequestMethod.GET)
    public ResponseEntity<Candidate> getCandidate(@RequestParam int id) {
        Candidate candidate = candidateService.getCandidateById(id);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }
}