package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.GeneratePDFService;
import ua.netcracker.model.service.QuestionService;

import java.util.Collection;
import java.util.List;


@Controller
public class CandidateController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CourseSettingService courseSettingService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private GeneratePDFService pdfService;


    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String getProfilePage() {
        return "student";
    }

    @RequestMapping(value = "/service/getAllMandatoryQuestions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Question>> getAllMandatoryQuestions() {
        Collection<Question> questions = questionService.
                getAllMandatory(courseSettingService.getLastSetting().getId());
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    @RequestMapping(value = "/service/saveAnswers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Candidate> setAnswers(@RequestParam String answersJsonString) {

        return ResponseEntity.ok(candidateService.saveAnswers(answersJsonString));
    }


    @RequestMapping(value = "/service/getAnswers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Answer>> getAnswers() {
        List<Answer> answers = (List<Answer>) candidateService.
                getAllCandidateAnswers(candidateService.getCurrentCandidate());
        if (answers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Answer>>(answers, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/getPDF", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getPDF() {
        pdfService.generatePDF(candidateService.getCurrentCandidate());
        byte[] content = pdfService.convertToBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "form.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}