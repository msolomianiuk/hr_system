package ua.netcracker.model.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.securiry.UserAuthenticationDetails;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.GeneratePDFService;
import ua.netcracker.model.service.QuestionService;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service("pdf service")
public class GeneratePDFServiceImpl implements GeneratePDFService {

    private static final Logger LOGGER = Logger.getLogger(GeneratePDFServiceImpl.class);
    private static final String NAME_PDF = "form.pdf";
    private static final String URL = "localhost:8080/hr_system-1.0-SNAPSHOT/static/images/netcracker.png";
    @Value("${userPhotoFolder}")
    private String absolutePath;
    @Value("/static/")
    private String imagePath;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CourseSettingService courseSettingService;
    @Autowired
    private UserDAO userDAO;


    private static final String PHOTO_PATH = "static/images/";
    private String absolutePathImage;

    @Autowired
    public void setAbsolutePath(ServletContext servletContext) {
        this.absolutePathImage = servletContext.getRealPath("/") + PHOTO_PATH;
    }

    @Override
    public Document generatePDF(Candidate candidate) {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(NAME_PDF));
            document.open();
            UserAuthenticationDetails userDetails =
                    (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDAO.find(candidate.getUserId());
            createDocument(document, candidate, user, userDetails);

            document.close();
        } catch (DocumentException | IOException e) {
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
        return document;
    }

    private void createDocument(Document document, Candidate candidate, User user,
                                UserAuthenticationDetails userDetails) throws IOException, DocumentException {
        PdfPTable tableName = createTitleName(candidate, user);
        PdfPTable tableImage = createImageTable(candidate, userDetails);
        PdfPTable table = new PdfPTable(2);
        PdfPCell cellTableName = new PdfPCell();
        PdfPCell cellTableImage = new PdfPCell();
        PdfPTable tableQuestions = createQuestionTable();
        PdfPTable tableQuestionAndTitle = new PdfPTable(1);
        cellTableName.addElement(tableName);
        cellTableImage.addElement(tableImage);
        cellTableName.setBorder(Rectangle.NO_BORDER);
        cellTableImage.setBorder(Rectangle.NO_BORDER);
        PdfObject obj = document.getAccessibleAttribute(new PdfName("Background"));
        table.addCell(cellTableName);
        table.addCell(cellTableImage);
        Image image = Image.getInstance(String.valueOf(new File(absolutePathImage + "fone.png")));
        PdfPCell titleQuestions = new PdfPCell(new Paragraph("Questions:"));
        titleQuestions.setBorderColor(BaseColor.BLUE);
        BaseColor baseColor = new BaseColor(149, 178, 215);
        titleQuestions.setBackgroundColor(baseColor);
        PdfPCell cellQuestions = new PdfPCell(tableQuestions);
        cellQuestions.setBorderColor(baseColor);
        tableQuestionAndTitle.addCell(titleQuestions);
        tableQuestionAndTitle.addCell(cellQuestions);
        document.add(table);
        image.scaleAbsoluteHeight(70);
        image.setAbsolutePosition(0, image.getAbsoluteY());
        document.add(image);
        document.add(new Paragraph(""));
        document.add(tableQuestionAndTitle);
    }

    private PdfPTable createQuestionTable() throws BadElementException, IOException {
        PdfPTable tableQuestions = new PdfPTable(2);
        ArrayList<Question> listQuestions = (ArrayList<Question>) questionService.
                getAllMandatory(courseSettingService.getLastSetting().getId());
        ArrayList<Answer> listAnswers = (ArrayList<Answer>) candidateService.
                getAllCandidateAnswers(candidateService.getCurrentCandidate());
        if (listQuestions.size() != 0 && listAnswers.size() != 0) {
            for (int i = 0; i < listQuestions.size(); i++) {
                Question question = listQuestions.get(i);
                String answerString = " ";
                for (int j = 0; j < listAnswers.size(); j++) {
                    Answer answer = listAnswers.get(j);
                    if (question.getId() == answer.getQuestionId()) {
                        answerString = answerString + answer.getValue() + " ";
                    }
                }
                PdfPCell cellQuestion = new PdfPCell(new Paragraph(question.getCaption()));
                PdfPCell cellAnswer = new PdfPCell(new Paragraph(answerString));
                cellQuestion.setBorder(Rectangle.NO_BORDER);
                cellAnswer.setBorder(Rectangle.NO_BORDER);
                tableQuestions.addCell(cellQuestion);
                tableQuestions.addCell(cellAnswer);
            }
        }
        return tableQuestions;
    }

    private PdfPTable createImageTable(Candidate candidate, UserAuthenticationDetails userDetails)
            throws BadElementException, IOException {
        PdfPTable tableImage = new PdfPTable(1);
        PdfPCell cellId = new PdfPCell(new Paragraph("ID candidate = " + candidate.getId()));
        File fileImage = new File(absolutePath + userDetails.getUser().getImage());
        if (fileImage.exists() == false) {
            fileImage = new File(absolutePathImage + "anonymouse.png");
        }
        PdfPCell cellImage = new PdfPCell(Image.getInstance(String.valueOf(fileImage)));
        cellImage.setFixedHeight(80);
        cellImage.setBorder(Rectangle.NO_BORDER);
        cellId.setBorder(Rectangle.NO_BORDER);

        tableImage.addCell(cellImage);
        tableImage.addCell(cellId);
        return tableImage;

    }

    private PdfPTable createTitleName(Candidate candidate, User user) throws BadElementException, IOException {
        PdfPTable tableName = new PdfPTable(1);
        PdfPCell cellName = new PdfPCell(new Paragraph("Name : " + user.getName()));
        PdfPCell cellSurname = new PdfPCell(new Paragraph("Surname : " + user.getSurname()));
        PdfPCell cellPatronymic = new PdfPCell(new Paragraph("Patronymic : " + user.getPatronymic()));
        PdfPCell cellImage = new PdfPCell(Image.getInstance(String.valueOf(new File(absolutePathImage + "netcracker.png"))));
        cellImage.setBorder(Rectangle.NO_BORDER);
        cellName.setBorder(Rectangle.NO_BORDER);
        cellSurname.setBorder(Rectangle.NO_BORDER);
        cellPatronymic.setBorder(Rectangle.NO_BORDER);
        cellImage.setFixedHeight(40);
        tableName.addCell(cellImage);
        tableName.addCell(cellName);
        tableName.addCell(cellSurname);
        tableName.addCell(cellPatronymic);
        return tableName;
    }

    @Override
    public byte[] convertToBytes() {
        byte[] content = new byte[0];
        try {
            content = Files.readAllBytes(Paths.get(NAME_PDF));
        } catch (IOException e) {
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
        return content;
    }
}
