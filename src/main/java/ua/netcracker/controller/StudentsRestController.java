package ua.netcracker.controller;

/**
 * Created by Серый on 02.05.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.filtering.SimpleFilter;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.impl.Pagination;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
public class StudentsRestController {

    @Autowired
    private CandidateService candidateService;

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


    @RequestMapping(value = "/getStudents/filter")
    public ResponseEntity<List<Candidate>> filterStudents(@RequestParam List<Answer> list) {

        List<Candidate> students = (List<Candidate>) candidateService.getAllCandidates();
        List<Candidate> filtered = students;

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (!list.isEmpty()) {
            SimpleFilter filter = new SimpleFilter();
            filter.setAnswerList(list);
            filtered = filter.filter(students);
        }

        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }

}