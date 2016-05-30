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
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.QuestionService;
import ua.netcracker.model.service.UserService;

import java.util.Collection;
import java.util.Map;

/**
 * Class for processing requests related to hr
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Controller
public class HRController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CourseSettingService courseSettingService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "hr/service/getCandidateById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Candidate> getCandidate(@RequestParam int id) throws NullPointerException {
        Candidate candidate = candidateService.getCandidateById(id);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/students_list", method = RequestMethod.GET)
    public String hrMainPage() {
        return "studentsList";
    }

    @RequestMapping(value = "hr/interview_page", method = RequestMethod.GET)
    public String hrInterviewPage() {
        return "interviewPage";
    }

    @RequestMapping(value = "hr/service/getCandidatsList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Candidate>> getCandidatesList() {
        Collection<Candidate> listCandidates = candidateService.getAllCandidates();
        if (listCandidates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCandidates, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/getQuestionViewList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Question>> getAllQuestionView() {
        Collection<Question> listQuestion = questionService.getAllIsView(courseSettingService.getLastSetting().getId());
        if (listQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listQuestion, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/getAllStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<Integer, String>> getAllStatus() {
        Map<Integer, String> listStatus = candidateService.getAllStatus();
        if (listStatus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/getAllMarked", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Candidate>> getAllMarked() {
        User user = userService.getAuthorizedUser();
        Collection<Candidate> candidateList = null;
        if (user != null) {
            candidateList = candidateService.getAllMarkedByCurrentInterviewer(user);
        }
        if (candidateList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(candidateList, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/getCandidatsListWithTo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Candidate>> getCandidatesListWithTo(@RequestParam Integer offset,
                                                                         @RequestParam Integer limit) {
        Collection<Candidate> listCandidates = candidateService.getPartCandidatesIsViewWithAnswer(offset, limit);
        if (listCandidates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCandidates, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/setCandidateStatus", method = RequestMethod.GET)
    @ResponseBody
    public boolean setCandidateStatus(@RequestParam Integer candidateId,
                                      @RequestParam Integer statusId
    ) {
        return candidateService.updateCandidateStatus(candidateId, Status.values()[statusId - 1]);
    }

    @RequestMapping(value = "hr/service/getCandidateCount", method = RequestMethod.GET)
    @ResponseBody
    public Integer getCandidateCount() throws NullPointerException {
        return candidateService.getCandidateCount();
    }

}