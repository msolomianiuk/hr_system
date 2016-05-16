package ua.netcracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class for processing requests related to ba
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class BAController {
//    @Autowired
//    private CandidateService candidateService;

    @RequestMapping(value = "ba/interview_page", method = RequestMethod.GET)
    public String baMainPage() {
        return "interviewPage";
    }

//    @RequestMapping(value = "ba/getStudent", method = RequestMethod.GET)
//    public ResponseEntity<Candidate> getCandidate(@RequestParam int id) {
//        Candidate candidate = candidateService.getCandidateById(id);
//        if (candidate == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(candidate, HttpStatus.OK);
//    }
}
