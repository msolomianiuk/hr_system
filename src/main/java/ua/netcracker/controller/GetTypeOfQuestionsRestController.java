package ua.netcracker.controller;

/**
 * Created by Серый on 05.05.2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.impl.QuestionServiceImpl;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GetTypeOfQuestionsRestController {

    @Autowired
    private QuestionServiceImpl questionServiceImpl;
    @ResponseBody
    @RequestMapping(value = "/getTypeOfQuestions", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> typeAllQuestions() {

        List<Question> questions = questionServiceImpl.getTypeOfQuestion();

        return new ResponseEntity(questions, HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/getQuantityQuestions", method = RequestMethod.GET)
    public ResponseEntity<Integer> getQuantityQuestions() {

        int QuantityQuestions = questionServiceImpl.getQuantityQuestions();

        return new ResponseEntity(QuantityQuestions, HttpStatus.OK);
    }

    @RequestMapping(value = "/setQuestion" , method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<Question> setQuestion(
            @RequestParam  int curse_id,
            @RequestParam  String caption,
            @RequestParam  String typeValue,
            @RequestParam  List<String> additionValue,
            @RequestParam  boolean isMandatory,
            @RequestParam  int orderNumber
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


    @RequestMapping(value = "/getCurseId", method = RequestMethod.POST)
    public ResponseEntity<Integer> getCurseId() {
        int courseID = questionServiceImpl.getCourseId();

        return new ResponseEntity(courseID, HttpStatus.OK);
    }

}
