package ua.netcracker.model.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.util.Date;

@Service("pdf service")
public class GeneratePDFServiceImpl implements GeneratePDFService {

    private static final Logger LOGGER = Logger.getLogger(GeneratePDFServiceImpl.class);
    private static final String NAME_PDF = "form.pdf";
    @Value("${userPhotoFolder}")
    private String absolutePath;
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
        PdfPTable tableName = createTitleName(user);
        PdfPTable tableImage = createImageTable(candidate, userDetails);
        PdfPTable table = new PdfPTable(2);
        PdfPCell cellTableName = new PdfPCell();
        PdfPCell cellTableImage = new PdfPCell();
        PdfPTable tableQuestions = createQuestionTable();
        PdfPTable tableQuestionAndTitle = new PdfPTable(1);
        Paragraph paragraphFree = new Paragraph("             ");
        cellTableName.addElement(tableName);
        cellTableImage.addElement(tableImage);
        cellTableName.setBorder(Rectangle.NO_BORDER);
        cellTableImage.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellTableName);
        table.addCell(cellTableImage);
        Image image = Image.getInstance(String.valueOf(new File(absolutePathImage + "fone.png")));
        BaseColor baseColor = new BaseColor(149, 178, 215);
        PdfPCell cellQuestions = new PdfPCell(tableQuestions);
        cellQuestions.setBorder(Rectangle.NO_BORDER);
        cellQuestions.setBorderColor(baseColor);
        tableQuestionAndTitle.addCell(cellQuestions);
        document.add(table);
        image.scaleAbsoluteHeight(70);
        image.setAbsolutePosition(0, image.getAbsoluteY());
        document.add(paragraphFree);
        document.add(image);
        document.add(paragraphFree);
        document.add(tableQuestionAndTitle);
        Paragraph paragraphPreLast = new Paragraph("I consent to the storage, processing and use of my personal data for possible training and employment in the company NETCRACKER now and in the future.");
        paragraphPreLast.setAlignment(tableQuestionAndTitle.getHorizontalAlignment());
        PdfPTable tablePreLast = new PdfPTable(2);
        PdfPCell cellDate = new PdfPCell(new Paragraph(new Date().toString()));
        PdfPCell cellSignature = new PdfPCell(new Paragraph("           (Signature)_______________"));
        cellSignature.setBorder(Rectangle.NO_BORDER);
        cellDate.setBorder(Rectangle.NO_BORDER);
        tablePreLast.addCell(cellDate);
        tablePreLast.addCell(cellSignature);
        document.add(paragraphPreLast);
        document.add(tablePreLast);
        Paragraph paragraphLast = new Paragraph("Interview (Filled interviewers)");
        paragraphLast.setAlignment(Element.ALIGN_CENTER);
        paragraphLast.setLeading(25.0f);
        PdfPTable tableLast = new PdfPTable(2);
        Paragraph paragraphHR = new Paragraph("HR or BA (Surname)");
        paragraphHR.setAlignment(Element.ALIGN_CENTER);
        Paragraph paragraphDEV = new Paragraph("DEV (Surname)");
        paragraphDEV.setAlignment(Element.ALIGN_CENTER);
        tableLast.addCell(new PdfPCell(paragraphHR));
        tableLast.addCell(new PdfPCell(paragraphDEV));
        PdfPTable tableSubFirst = new PdfPTable(1);
        PdfPTable tableSubLast = new PdfPTable(1);
        for (int i = 0; i < 2; i++) {
            PdfPCell cell = new PdfPCell(paragraphFree);
            cell.setBorder(Rectangle.NO_BORDER);
            tableSubFirst.addCell(cell);
            tableSubLast.addCell(cell);
        }
        document.add(paragraphLast);
        document.add(paragraphFree);
        tableLast.addCell(tableSubFirst);
        tableLast.addCell(tableSubLast);
        document.add(tableLast);

    }

    private PdfPTable createQuestionTable() throws BadElementException, IOException {
        PdfPTable tableQuestions = new PdfPTable(1);
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
                PdfPCell cellFree = new PdfPCell(new Paragraph("  "));
                cellFree.setBorder(Rectangle.NO_BORDER);
                tableQuestions.addCell(cellQuestion);
                tableQuestions.addCell(cellAnswer);
                tableQuestions.addCell(cellFree);
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
        cellImage.setFixedHeight(100);
        cellImage.setBorder(Rectangle.NO_BORDER);
        cellId.setBorder(Rectangle.NO_BORDER);

        tableImage.addCell(cellImage);
        tableImage.addCell(cellId);
        return tableImage;

    }

    private PdfPTable createTitleName(User user) throws BadElementException, IOException {
        PdfPTable tableName = new PdfPTable(1);
        PdfPCell cellName = new PdfPCell(new Paragraph("Name : " + user.getName()));
        PdfPCell cellSurname = new PdfPCell(new Paragraph("Surname : " + user.getSurname()));
        PdfPCell cellPatronymic = new PdfPCell(new Paragraph("Patronymic : " + user.getPatronymic()));
        PdfPCell cellEmail = new PdfPCell(new Paragraph("E-mail : " + user.getEmail()));
        PdfPCell cellImage = new PdfPCell(Image.getInstance(String.valueOf(new File(absolutePathImage + "netcracker.png"))));
        cellEmail.setBorder(Rectangle.NO_BORDER);
        cellImage.setBorder(Rectangle.NO_BORDER);
        cellName.setBorder(Rectangle.NO_BORDER);
        cellSurname.setBorder(Rectangle.NO_BORDER);
        cellPatronymic.setBorder(Rectangle.NO_BORDER);
        cellImage.setFixedHeight(40);
        tableName.addCell(cellImage);
        tableName.addCell(cellName);
        tableName.addCell(cellSurname);
        tableName.addCell(cellPatronymic);
        tableName.addCell(cellEmail);
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
