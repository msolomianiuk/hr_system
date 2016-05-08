package ua.netcracker.model.service.impl;


import com.itextpdf.text.Document;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.service.GeneratePDFService;

@Service("pdf service")
public class GeneratePDFServiceImpl implements GeneratePDFService{
    @Override
    public Document generatePDF(Candidate candidate) {
        return null;
    }

    @Override
    public byte[] convertToBytes() {
        return new byte[0];
    }
//
//    private static final Logger LOGGER = Logger.getLogger(GeneratePDFServiceImpl.class);
//    private static final String NAME_PDF= "form.pdf";
//
//    @Autowired
//    private QuestionService questionService;
//    @Autowired
//    private CourseSettingService courseSettingService;
//
//    @Override
//    public Document generatePDF(Candidate candidate) {
//
//        Document document = new Document();
//
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(NAME_PDF));
//            document.open();
//
//            Font font = FontFactory.getFont(FontFactory.HELVETICA);
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
//                    //new Phrase("PHOTO"));
//                    Image.getInstance(new URL("https://upload.wikimedia.org/wikipedia/commons/0/0e/Lakeyboy_Silhouette.PNG")),true);
//                    //Image.getInstance(candidate.getUser().getImage()));
//
//            cellName.setBorder(Rectangle.NO_BORDER);
//            cellImage.setBorder(Rectangle.NO_BORDER);
//
//            table.addCell(cellName);
//            table.addCell(cellImage);
//            float[] columnWidths = new float[]{30f, 12f};
//            table.setWidthPercentage(100f);
//            table.setWidths(columnWidths);
//
//            document.add(table);
//
//            document.add(new Paragraph());
//
//            Map<String,String> answersWithQuestionsCaptions
//                    = getQuestionCaptionAndAnswers(candidate.getAnswers());
//
//            for (Map.Entry<String, String> entry : answersWithQuestionsCaptions.entrySet()) {
//                Paragraph paragraphQuestion = new Paragraph(new Phrase(String.valueOf(entry.getKey())));
//                document.add(paragraphQuestion);
//
//                Paragraph paragraphAnswer = new Paragraph(new Phrase(String.valueOf(entry.getValue()) + "\n\n"));
//                document.add(paragraphAnswer);
//            }
//
//            document.close();
//        } catch (DocumentException | IOException e) {
//            LOGGER.debug(e.getStackTrace());
//            LOGGER.info(e.getMessage());
//        }
//        return document;
//    }
//
//    private Map getQuestionCaptionAndAnswers(Collection<Answer> candidateAnswers){
//        Map<String,String> answersWithQuestionsCaptions = new LinkedHashMap<>();
//        //Collection doesn't have method get!!!
//        ArrayList<Answer> answers = (ArrayList<Answer>) candidateAnswers;
//        ArrayList<Question> questions = (ArrayList<Question>)
//                questionService.getAllMandatory(courseSettingService.getLastSetting().getId());
//
//        for (int i = 0; i < questions.size(); i++) {
//            answersWithQuestionsCaptions.put(questions.get(i).getCaption(),answers.get(i).getValue());
//        }
//        return answersWithQuestionsCaptions;
//    }
//
//    @Override
//    public byte[] convertToBytes() {
//        byte[] content = new byte[0];
//        try {
//            content = Files.readAllBytes(Paths.get(NAME_PDF));
//        } catch (IOException e) {
//            LOGGER.debug(e.getStackTrace());
//            LOGGER.info(e.getMessage());
//        }
//        return content;
//    }
}
