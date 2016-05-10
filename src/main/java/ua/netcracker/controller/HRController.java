package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.QuestionService;

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
    private UserDAO userDAO;

    @RequestMapping(value = "hr/service/getCandidateById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Candidate> getCandidate(@RequestParam int id) {
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
        Collection<Candidate> listCandidates = candidateService.getAllCandidatesIsView();
        if (listCandidates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Candidate>>(listCandidates, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/getQuestionViewList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Question>> getAllQuestionView() {
        Collection<Question> listQuestion = questionService.getAllIsView(courseSettingService.getLastSetting().getId());
        if (listQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Question>>(listQuestion, HttpStatus.OK);
    }

    @RequestMapping(value = "hr/service/getAllStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<Integer, String>> getAllStatus(){
        Map<Integer, String> listStatus = candidateService.getAllStatus();
        if (listStatus.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Map<Integer, String>>(listStatus, HttpStatus.OK);
    }

}