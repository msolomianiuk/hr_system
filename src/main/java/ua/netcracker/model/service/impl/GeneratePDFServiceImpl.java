package ua.netcracker.model.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
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
    public static final String FONT = "arial.ttf";
    @Value("/static/")
    private String staticPath;
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
    private static final String FONT_PATH = "static/fonts/";
    private String absolutePathImage;
    private String absolutePathFont;


    @Autowired
    public void setAbsolutePath(ServletContext servletContext) {
        this.absolutePathImage = servletContext.getRealPath("/") + PHOTO_PATH;
        this.absolutePathFont = servletContext.getRealPath("/") + FONT_PATH;
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
        image.scaleAbsoluteHeight(40);
        image.setAbsolutePosition(0, image.getAbsoluteY());
        document.add(paragraphFree);
        document.add(image);
        document.add(paragraphFree);
        document.add(tableQuestionAndTitle);
        createPreLastForm(document, tableQuestionAndTitle);
        createLastForm(document, paragraphFree);

    }

    private void createPreLastForm(Document document, PdfPTable tableQuestionAndTitle) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        Paragraph paragraphPreLast = new Paragraph("I consent to the storage, processing and use of my personal data for possible training and employment in the company NETCRACKER now and in the future.");
        paragraphPreLast.setAlignment(tableQuestionAndTitle.getHorizontalAlignment());
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(paragraphPreLast);
        PdfPTable tablePreLast = new PdfPTable(2);
        PdfPCell cellFree = new PdfPCell(new Paragraph("  "));
        cellFree.setBorder(Rectangle.NO_BORDER);
        tablePreLast.addCell(cellFree);
        tablePreLast.addCell(cellFree);
        PdfPCell cellDate = new PdfPCell(new Paragraph(new Date().toString()));
        PdfPCell cellSignature = new PdfPCell(new Paragraph("           (Signature)_______________"));
        cellSignature.setBorder(Rectangle.NO_BORDER);
        cellDate.setBorder(Rectangle.NO_BORDER);
        tablePreLast.addCell(cellDate);
        tablePreLast.addCell(cellSignature);
        cell.addElement(tablePreLast);
        table.addCell(cell);
        document.add(table);
//        document.add(paragraphPreLast);
//        document.add(tablePreLast);
    }

    private void createLastForm(Document document, Paragraph paragraphFree) throws DocumentException {
        Paragraph paragraphLast = new Paragraph("Interview (Interviewers fill)");
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

    private PdfPTable createQuestionTable() throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont(absolutePathFont + FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontQuestion = new Font(baseFont, 14, Font.BOLDITALIC);
        Font fontAnswers = new Font(baseFont, 10);
        PdfPTable tableQuestions = new PdfPTable(1);
        ArrayList<Question> listQuestions = (ArrayList<Question>) questionService.
                getAllMandatory(courseSettingService.getLastSetting().getId());
        ArrayList<Answer> listAnswers = (ArrayList<Answer>) candidateService.
                getAllCandidateAnswers(candidateService.getCurrentCandidate());
        if (listQuestions.size() != 0 && listAnswers.size() != 0) {
            for (int i = 0; i < listQuestions.size(); i++) {
                Question question = listQuestions.get(i);
                String answerString = " ";
                PdfPCell cellQuestion = null;
                PdfPCell cellAnswer = null;
                cellQuestion = new PdfPCell(new Paragraph(question.getCaption(),fontQuestion));

                ArrayList<Answer> answers =
                        (ArrayList<Answer>) candidateService.getAnswerByQuestionId(
                                candidateService.getCurrentCandidate(), question.getId());

                switch (question.getType()){
                    case "Text":
                    case "Number":{
                        cellAnswer = getSimpleAnswer(answers, fontAnswers);
                        break;
                    }
                    case "Select":
                    case "Checkboxes":{
                        cellAnswer = getSelectedAnswer(answers, question, fontAnswers);
                        break;
                    }

                    case "Select or text":{
                        cellAnswer = getSelectedOrTextAnswer(answers,question, fontAnswers);
                        break;
                    }
                }
                cellQuestion.setBorder(Rectangle.NO_BORDER);
                cellAnswer.setBorder(Rectangle.NO_BORDER);
                PdfPCell cellFree = new PdfPCell(new Paragraph("  "));
                cellFree.setBorder(Rectangle.NO_BORDER);
                PdfPTable tableQuestion = new PdfPTable(1);
                tableQuestion.addCell(cellQuestion);
                tableQuestion.addCell(cellFree);
                tableQuestion.addCell(cellAnswer);
                tableQuestion.addCell(cellFree);
                PdfPCell cell = new PdfPCell(tableQuestion);
                cell.setBorder(Rectangle.NO_BORDER);
                tableQuestions.addCell(cell);
            }

        }
        return tableQuestions;
    }

    private PdfPTable addCells(int n, PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        for (int i = 0; i<n; i++){
            table.addCell(cell);
        }
        return table;
    }

    private PdfPCell getSimpleAnswer(ArrayList<Answer> answers, Font font) throws IOException, DocumentException {

        return new PdfPCell(new Paragraph(answers.get(0).getValue(), font));
//                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
    }

    private PdfPCell getSelectedAnswer(ArrayList<Answer> answers, Question question, Font font) throws DocumentException {
        PdfPTable tableAnswers = new PdfPTable(3);

        for (String variant : question.getAnswerVariants()){
            PdfPCell cellCheck = new PdfPCell();
            for (Answer answer : answers) {
                if (answer.getValue().equals(variant)) {
                    cellCheck.setBackgroundColor(new BaseColor(66, 167, 206));
                }
            }
            PdfPTable tableVariant = new PdfPTable(2);
            tableVariant.setWidths(new int[]{3, 30});
            tableVariant.setTotalWidth(99);
            Paragraph p = new Paragraph("", font);
            p.setAlignment(Element.ALIGN_CENTER);
            cellCheck.addElement(p);
            PdfPCell cellValue = new PdfPCell(new Paragraph(variant, font));
            cellValue.setBorder(Rectangle.NO_BORDER);
            tableVariant.addCell(cellCheck);
            tableVariant.addCell(cellValue);
            PdfPCell cellVariant = new PdfPCell(tableVariant);
            cellVariant.setBorder(Rectangle.NO_BORDER);
            tableAnswers.addCell(cellVariant);
        }
        tableAnswers = addCells(3-question.getAnswerVariants().size()%3,tableAnswers);
        return new PdfPCell(tableAnswers);
    }

    private PdfPCell getSelectedOrTextAnswer(ArrayList<Answer> answers, Question question, Font font) throws DocumentException {
        PdfPTable tableAnswers = new PdfPTable(1);

        tableAnswers.addCell(getSelectedAnswer(answers,question, font));

        String textAnswer = "";
        if (!question.getAnswerVariants().contains(answers.get(0).getValue())){
            textAnswer = answers.get(0).getValue();
        }
        PdfPCell cellText = new PdfPCell(new Paragraph(textAnswer, font));
        tableAnswers.addCell(cellText);

        return new PdfPCell(tableAnswers);
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

    private PdfPTable createTitleName(User user) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont(absolutePathFont + FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12);
        PdfPTable tableName = new PdfPTable(1);
        PdfPCell cellName = new PdfPCell(new Paragraph("Name : " + user.getName(), font));
        PdfPCell cellSurname = new PdfPCell(new Paragraph("Surname : " + user.getSurname(), font));
        PdfPCell cellPatronymic = new PdfPCell(new Paragraph("Patronymic : " + user.getPatronymic(), font));
        PdfPCell cellEmail = new PdfPCell(new Paragraph("E-mail : " + user.getEmail(), font));
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
