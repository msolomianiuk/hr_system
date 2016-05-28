package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.QuestionForInterview;
import ua.netcracker.model.service.QuestionForInterviewService;

import java.util.Collection;

@Controller
public class QuestionForInterviewController {
    @Autowired
    private QuestionForInterviewService questionForInterviewService;

    @RequestMapping(value = "/service/getAllQuestionForInterview", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<QuestionForInterview>> getQuestionsForInterview(){
        Collection<QuestionForInterview> questionForInterview =
                questionForInterviewService.getAllSubjectAndQuestion();
        if (questionForInterview == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(questionForInterview, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/addQuestion", method = RequestMethod.GET)
    @ResponseBody
    public Integer addQuestion(@RequestParam Integer subjectId,
                               @RequestParam String questionValue
    ){
        questionForInterviewService.setQuestion(subjectId, questionValue);
        return questionForInterviewService.getLastQuestionId();
    }

    @RequestMapping(value = "/service/editQuestion", method = RequestMethod.GET)
    @ResponseBody
    public Boolean editQuestion(@RequestParam Integer questionId,
                               @RequestParam String questionValue,
                               @RequestParam Integer subjectId
    ){
        return questionForInterviewService.updateQuestion(questionId, questionValue, subjectId);
    }

    @RequestMapping(value = "/service/deleteQuestion", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteQuestion(@RequestParam Integer questionId){
        return questionForInterviewService.remove(questionId);
    }
}
