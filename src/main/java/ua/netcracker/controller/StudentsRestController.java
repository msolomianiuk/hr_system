package ua.netcracker.controller;

/**
 * Created by Серый on 02.05.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.model.dao.CandidateDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.impl.Pagination;
import ua.netcracker.model.utils.JsonParsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
public class StudentsRestController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private Pagination pagination;

    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public ResponseEntity<List<Candidate>> listAllStudents() {


        List<Candidate> students = (List<Candidate>) candidateService.getAllCandidates();


        if (students.isEmpty()) {
            return new ResponseEntity<List<Candidate>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Candidate>>(students, HttpStatus.OK);
    }


    @RequestMapping(value = "/getStudents/filter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Candidate>> filterStudents(@RequestParam String answersJsonString, @RequestParam String status, @RequestParam String status2, @RequestParam Integer limit, @RequestParam Integer offset) {

        Collection<Answer> answers = JsonParsing.parseJsonString(answersJsonString);

        List<Answer> selected = new ArrayList<>();
        for (Answer answer : answers) {
            if (!(answer.getValue().isEmpty())) {
                selected.add(answer);
            }
        }

        List<Candidate> filtered = (List<Candidate>) candidateService.filterCandidates(selected, limit, offset);

        if (filtered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (!status.equals("Select status")) {
            Status st = Status.valueOf(status);
            for (Candidate candidate : filtered) {
                candidateService.updateCandidateStatus(candidate.getId(), st.getId());
            }
        }

        if (!status2.equals("Select status")) {
            List<Candidate> students = (List<Candidate>) candidateService.getAllCandidates();
            if (filtered.size() < students.size()) {
                Status st2 = Status.valueOf(status2);
                students.removeAll(filtered);
                for (Candidate student : students) {
                    candidateService.updateCandidateStatus(student.getId(), st2.getId());
                }
            }
        }
        
        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }

}