package ua.netcracker.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.securiry.UserAuthenticationDetails;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;

import java.util.ArrayList;
import java.util.Iterator;
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

//    @RequestMapping(value = "/service/getPDF", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> getPDF() {
//
//        Candidate candidate = new Candidate();
//
//        //TODO:
//        //Candidate candidate = AnswersRestController.getCurrentCandidate();
//
//        candidate.setUser(new User("email", "password", "UserName", "UserSurname", "UserPatronymic", Arrays.asList(Role.STUDENT)));
//
//        Map<Integer, Object> answers = new HashMap();
//        answers.put(1, "answer1");
//        answers.put(2, "answer2");
//        answers.put(3, "answer3");
//        answers.put(4, "answer4");
//        answers.put(5, "answer5");
//        candidate.setAnswer(answers);
//
//        // generate pdf file with candidate answers
//        generatePDF(candidate);
//
//        byte[] contents = new byte[0];
//        try {
//            contents = Files.readAllBytes(Paths.get("src/form.pdf"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("application/pdf"));
//        String filename = "form.pdf";
//        headers.setContentDispositionFormData(filename, filename);
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
//    }

    /**
     * Generate pdf document
     *
     * @param candidate
     * @return form.pdf
     */
//    private Document generatePDF(Candidate candidate) {
//        Document document = new Document();
//
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream("src/form.pdf"));
//            document.open();
//
//            Font font = FontFactory.getFont(FontFactory.HELVETICA);
//
//            PdfPTable table = new PdfPTable(2);
//            PdfPCell cellName = new PdfPCell();
//
//            Paragraph paragraphName = new Paragraph();
//            paragraphName.add(new Phrase(candidate.getUser().getName() + " "));
//            paragraphName.add(new Phrase(candidate.getUser().getSurname() + " "));
//            paragraphName.add(new Phrase(candidate.getUser().getPatronymic()));
//
//            cellName.addElement(paragraphName);
//
//
//            PdfPCell cellImage = new PdfPCell(
//                    Image.getInstance("src/res/user.png"), true);
//            //TODO:
//            //Image.getInstance(candidate.getUser().getImage(),true);
//
//            cellName.setBorder(Rectangle.NO_BORDER);
//            cellImage.setBorder(Rectangle.NO_BORDER);
//
//            table.addCell(cellName);
//            table.addCell(cellImage);
//            float[] columnWidths = new float[]{30f, 12f};
//            table.setWidthPercentage(90f);
//            table.setWidths(columnWidths);
//
//            document.add(table);
//
//            Map<String,Object> map = new HashMap<>();
//            //TODO:
//            //get questions caption and answer
//            //map = getQuestionCaptionAndAnswers;
//
//            //Map.Entry<Integer, Object> === Map.Entry<String, Object>
//            for (Map.Entry<Integer, Object> entry : candidate.getAnswerValue().entrySet()) {
//                Paragraph paragraphQuestion = new Paragraph(new Phrase(String.valueOf(entry.getKey())));
//                document.add(paragraphQuestion);
//
//                Paragraph paragraphAnswer = new Paragraph(new Phrase(String.valueOf(entry.getValue()) + "\n\n"));
//                document.add(paragraphAnswer);
//            }
//
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document;
//    }
}