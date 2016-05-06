package ua.netcracker.controller;

/**
 * Created by Серый on 05.05.2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.impl.QuestionServiceImpl;


import java.util.List;

@RestController
public class GetTypeOfQuestionsRestController {

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @RequestMapping(value = "/getTypeOfQuestions", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> typeAllQuestions() {

        List<Question> questions = questionServiceImpl.getTypeOfQuestion();

        return new ResponseEntity(questions, HttpStatus.OK);
    }

    @RequestMapping(value = "/getQuantityQuestions", method = RequestMethod.GET)
    public ResponseEntity<Integer> getQuantityQuestions() {

        int QuantityQuestions = questionServiceImpl.getQuantityQuestions();

        return new ResponseEntity(QuantityQuestions, HttpStatus.OK);
    }

    @RequestMapping(value = "/setQuestion", method = RequestMethod.GET)
    public ResponseEntity<Question> setQuestion(
            @RequestParam String caption,
            @RequestParam int curse_id,
            @RequestParam String typeValue,
            @RequestParam List<String> additionValue,
            @RequestParam boolean isMandatory,
            @RequestParam int orderNumber
    ) {



        Question question = new Question();
        question.setOrderNumber(orderNumber);
        question.setCaption(caption);
        question.setCourseID(curse_id);
        question.setType(typeValue);
        question.setMandatory(isMandatory);
        question.setAnswerVariants(questionServiceImpl.parseListJson(additionValue));

        questionServiceImpl.setQuestion(question);

        return new ResponseEntity(question, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCurseId", method = RequestMethod.GET)
    public ResponseEntity<Integer> getCurseId() {

        int curseID = questionServiceImpl.getCurseId();

        return new ResponseEntity(curseID, HttpStatus.OK);
    }

}
