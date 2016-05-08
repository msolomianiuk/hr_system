package ua.netcracker.model.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Answer;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Question;
import ua.netcracker.model.service.GeneratePDFService;
import ua.netcracker.model.service.QuestionService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service("pdf service")
public class GeneratePDFServiceImpl implements GeneratePDFService{

    private static final Logger LOGGER = Logger.getLogger(GeneratePDFServiceImpl.class);
    private static final String NAME_PDF= "form.pdf";

    @Autowired
    private QuestionService questionService;

    @Override
    public Document generatePDF(Candidate candidate) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(NAME_PDF));
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA);
            PdfPTable table = new PdfPTable(2);
            PdfPCell cellName = new PdfPCell();

            Paragraph paragraphName = new Paragraph();
            paragraphName.add(new Phrase(candidate.getUser().getName() + " "));
            paragraphName.add(new Phrase(candidate.getUser().getSurname() + " "));
            paragraphName.add(new Phrase(candidate.getUser().getPatronymic()));

            cellName.addElement(paragraphName);


            PdfPCell cellImage = new PdfPCell(
                    //new Phrase("PHOTO"));
                    Image.getInstance(new URL("https://upload.wikimedia.org/wikipedia/commons/0/0e/Lakeyboy_Silhouette.PNG")),true);
                    //Image.getInstance(candidate.getUser().getImage()));

            cellName.setBorder(Rectangle.NO_BORDER);
            cellImage.setBorder(Rectangle.NO_BORDER);

            table.addCell(cellName);
            table.addCell(cellImage);
            float[] columnWidths = new float[]{30f, 12f};
            table.setWidthPercentage(100f);
            table.setWidths(columnWidths);

            document.add(table);

//            TODO:
//            get questions caption and answer
//            map = getQuestionCaptionAndAnswers;

            document.add(new Paragraph());

            Map<String,String> answersWithQuestionsCaptions
                    = getQuestionCaptionAndAnswers(candidate.getAnswers());

            for (Answer answer : candidate.getAnswers()) {
                Paragraph paragraphQuestion = new Paragraph(new Phrase(String.valueOf(answer.getQuestionId())));
                document.add(paragraphQuestion);

                Paragraph paragraphAnswer = new Paragraph(new Phrase(String.valueOf(answer.getValue()) + "\n\n"));
                document.add(paragraphAnswer);
            }

            document.close();
        } catch (DocumentException | IOException e) {
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
        return document;
    }

    private Map getQuestionCaptionAndAnswers(Collection<Answer> answers){
        Map<String,String> answersWithQuestionsCaptions = new HashMap<>();
        return answersWithQuestionsCaptions;
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
