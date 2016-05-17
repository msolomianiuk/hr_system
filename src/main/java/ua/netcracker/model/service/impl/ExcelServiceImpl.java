package ua.netcracker.model.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ua.netcracker.model.service.ExcelService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Владимир on 15.05.2016.
 */
@Service()
public class ExcelServiceImpl implements ExcelService {

    @Override
    public byte[] toXLSX(Collection<Collection<String>> report, String reportName) throws IOException {
        Workbook book = new XSSFWorkbook(new FileInputStream(new File("template.xlsx")));
        Sheet sheet = book.createSheet("Report");
        CellStyle style = book.createCellStyle();
        addBorders(style);

        CellStyle styleTop = book.createCellStyle();
        addBorders(styleTop);
        styleTop.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleTop.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());

        int rowCounter = 0;
        for (Collection<String> rowReport : report) {
            Row row = sheet.createRow(rowCounter++);
            writeRow(rowReport, row, rowCounter == 1 ? styleTop : style);
        }
        for (int colNum = 0; colNum < sheet.getRow(0).getLastCellNum(); colNum++) {
            book.getSheetAt(1).autoSizeColumn(colNum);
        }
        changeCoverPage(book, style, reportName);
        FileOutputStream fos = new FileOutputStream(new File("report.xlsx"));
        book.write(fos);
        fos.close();
        book.close();
        return Files.readAllBytes(Paths.get("report.xlsx"));
    }

    private void changeCoverPage(Workbook book, CellStyle style, String reportName) {
        Sheet cover = book.getSheetAt(0);
        Row date = cover.createRow(18);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        writeCoverRow(date, style, "Document Date", dateFormat.format(Calendar.getInstance().getTime()));
        Row name = cover.createRow(19);
        writeCoverRow(name, style, "Report Name", reportName);
        cover.setActiveCell(new CellAddress(0,0));
    }


    private void addBorders(CellStyle style) {
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
    }

    private void writeCoverRow(Row date, CellStyle style, String description, Object value) {
        Cell descriptionCell = date.createCell(1);
        descriptionCell.setCellValue(description);
        descriptionCell.setCellStyle(style);
        Cell valueCell = date.createCell(2);
        valueCell.setCellValue(value.toString());
        valueCell.setCellStyle(style);
    }

    private void writeRow(Collection<String> rowReport, Row row, CellStyle style) {
        int cellCounter = 0;
        for (Object value : rowReport) {
            Cell cell = row.createCell(cellCounter++);
            cell.setCellStyle(style);
            if (value != null) {
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else {
                    cell.setCellValue(value.toString());
                }
            }
        }
    }
}

