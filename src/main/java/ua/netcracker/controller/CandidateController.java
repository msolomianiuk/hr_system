package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.GeneratePDFService;
import ua.netcracker.model.service.QuestionService;

import java.util.ArrayList;
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
    public String getProfilePage(Model model) {
        return "student";
    }


    @RequestMapping(value = "/service/getAllMandatoryQuestions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<Question>> getAllMandatoryQuestions() {
        Collection<Question> questions = questionService.
                getAllMandatory(courseSettingService.getLastSetting().getId());
        if (questions.isEmpty()) {
            return new ResponseEntity<Collection<Question>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Question>>(questions, HttpStatus.OK);
    }


    @RequestMapping(value = "/service/saveAnswers", method = RequestMethod.GET)
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

//    @RequestMapping(value = "/service/getPDF", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<byte[]> getPDF() {
//
//        //TODO:
//        //candidate = getCurrentCandidate();
//
//        //---------------------------------------------------------------------------------------
//        Candidate exampleCandidate = new Candidate();
//
//        User exampleUser = new User();
//        exampleUser.setName("Name");
//        exampleUser.setSurname("Surname");
//        exampleUser.setPatronymic("Patronymic");
//        exampleUser.setImage("src/main/webapp/static/images/user.png");
//        exampleCandidate.setUser(exampleUser);
//
//        Collection<Answer> exampleAnswers = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Answer exampleAnswer = new Answer();
//            exampleAnswer.setQuestionId(i);
//            exampleAnswer.setValue("Answer"+i);
//
//            exampleAnswers.add(exampleAnswer);
//        }
//        exampleCandidate.setAnswers(exampleAnswers);
//        //---------------------------------------------------------------------------------------
//
//        pdfService.generatePDF(exampleCandidate);
//        byte[] content = pdfService.convertToBytes();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("application/pdf"));
//        String filename = "form.pdf";
//        headers.setContentDispositionFormData(filename, filename);
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//        return new ResponseEntity<>(content, headers, HttpStatus.OK);
//    }
   /* @RequestMapping(value = "/student1", method = RequestMethod.POST)
    public String addImageFromForm(Candidate candidate, BindingResult bindingResult,
                                   @RequestParam(value = "image", required = false)
                                   MultipartFile image){
        if(bindingResult.hasErrors()){
            return "student";
        }
        try{
            if(!image.isEmpty()) {
                try {
                    File file = new File(webRootPath + candidate.getId());
                    FileUtils.writeByteArrayToFile(file, image.getBytes());
                } catch (IOException e) {
                    throw new IOException("Unable to save image", e);
                }
            }
        }catch (IOException e){
            bindingResult.reject(e.getMessage());
            return "student";
        }
        return "student";
    }*/
   @RequestMapping(value = "/service/getPDF", method = RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<byte[]> getPDF() {

       //---------------------------------------------------------------------------------------
       Candidate exampleCandidate = new Candidate();

       User exampleUser = new User();
       exampleUser.setName("Name");
       exampleUser.setSurname("Surname");
       exampleUser.setPatronymic("Patronymic");
       exampleUser.setImage("src/main/webapp/static/images/user.png");
       exampleCandidate.setUser(exampleUser);

       Collection<Answer> exampleAnswers = new ArrayList<>();
       for (int i = 0; i < questionService.getAllMandatory(courseSettingService.getLastSetting().getId()).size(); i++) {
           Answer exampleAnswer = new Answer();
           exampleAnswer.setQuestionId(i);
           exampleAnswer.setValue("Answer"+i);

           exampleAnswers.add(exampleAnswer);
       }
       exampleCandidate.setAnswers(exampleAnswers);
       //---------------------------------------------------------------------------------------

       pdfService.generatePDF(exampleCandidate);

       //TODO
       //pdfService.generatePDF(candidateService.getCurrentCandidate());

       byte[] content = pdfService.convertToBytes();

       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.parseMediaType("application/pdf"));
       String filename = "form.pdf";
       headers.setContentDispositionFormData(filename, filename);
       headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

       return new ResponseEntity<>(content, headers, HttpStatus.OK);
   }
}