package ua.netcracker.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.entity.*;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.securiry.UserAuthenticationDetails;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.GeneratePDFService;
import ua.netcracker.model.service.CourseSettingService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * Created by ksenzod on 02.05.16.
 */

@RestController
public class AnswersRestController {
    private Integer userId;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private Candidate candidate;
    @Autowired
    private GeneratePDFService pdfService;
    @Autowired
    private CourseSettingService courseSettingService;

    @RequestMapping(value = "/service/saveAnswers", method = RequestMethod.GET)
    public ResponseEntity<Candidate> setAnswers(@RequestParam String answersJsonString) {
        List<Answer> listAnswers = new ArrayList<>();

        JSONObject obj = new JSONObject(answersJsonString);
        Iterator<?> keys = obj.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (obj.get(key) instanceof JSONArray) {
                JSONArray array = (JSONArray) obj.get(key);
                for (int i = 0; i < array.length(); i++) {
                    Answer answer = new Answer();
                    answer.setQuestionId(Integer.valueOf(key.replace("question-", "")));
                    answer.setValue(array.getString(i));
                    listAnswers.add(answer);
                }
                continue;
            }
            Answer answer = new Answer();
            answer.setQuestionId(Integer.valueOf(key.replace("question-", "")));
            answer.setValue((String) obj.get(key));
            listAnswers.add(answer);

        }
        candidate = getCurrentCandidate();
        if (candidate.getId() == 0) {
            candidate.setUserId(userId);
            candidate.setStatusId(Status.NEW.getId());
            candidate.setCourseId(courseSettingService.getLastSetting().getId());
            candidateService.saveCandidate(candidate);
            candidate = candidateService.getCandidateById(userId);
        }

        candidate.setAnswers(listAnswers);
        candidateService.saveOrUpdate(candidate);

        return ResponseEntity.ok(candidate);
    }

    private Candidate getCurrentCandidate() {
        userId = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserAuthenticationDetails userDetails =
                    (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = userDetails.getUserId();
        }

        return candidateService.getCandidateById(userId);
    }

    @RequestMapping(value = "/service/getAnswers", method = RequestMethod.GET)
    public ResponseEntity<List> getAnswers() {

        candidate = getCurrentCandidate();

        List<Answer> answers = (List<Answer>) candidateService.getAllCandidateAnswers(candidate);

        if (answers.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List>(answers, HttpStatus.OK);
    }

    @RequestMapping(value = "/service/getPDF", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF() {

        //TODO:
        //candidate = getCurrentCandidate();

        //---------------------------------------------------------------------------------------
        Candidate exampleCandidate = new Candidate();

        User exampleUser = new User();
        exampleUser.setName("Name");
        exampleUser.setSurname("Surname");
        exampleUser.setPatronymic("Patronymic");
        exampleUser.setImage("src/main/webapp/static/images/user.png");
        exampleCandidate.setUser(exampleUser);

        Collection<Answer> exampleAnswers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Answer exampleAnswer = new Answer();
            exampleAnswer.setQuestionId(i);
            exampleAnswer.setValue("Answer"+i);

            exampleAnswers.add(exampleAnswer);
        }
        exampleCandidate.setAnswers(exampleAnswers);
        //---------------------------------------------------------------------------------------

        pdfService.generatePDF(exampleCandidate);
        byte[] content = pdfService.convertToBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "form.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
