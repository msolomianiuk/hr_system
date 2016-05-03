package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.dao.daoImpl.CandidateDAOImpl;
import ua.netcracker.hr_system.model.dao.daoImpl.QuestionDAOImpl;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.Question;

import java.util.List;


@RestController
public class CandidateController {
    @Autowired
    private QuestionDAOImpl questionDao;

    @Autowired
    private CandidateDAOImpl c;

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> getAllMandatoryQuestions() {
        Candidate cand = new Candidate(101, 3, 1, 1);

       /* Map<Integer, Object> answers = new HashMap<>();
        List<String> fewAnswers = new ArrayList<>();
        fewAnswers.add("ololo10");
        fewAnswers.add("ololo20");
        answers.put(1, fewAnswers);
        answers.put(2, "asdad11");
        cand.setAnswer(answers);
        c.insertAnswer(cand);*/

        List<Question> questions = (List<Question>) questionDao.findAllMandatory();
        if(questions.isEmpty()){
            return new ResponseEntity<List<Question>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }
}