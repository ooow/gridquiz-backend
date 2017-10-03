package com.griddynamics.gridquiz.core.services.common;

import com.griddynamics.gridquiz.api.models.UserResultsModel;
import com.griddynamics.gridquiz.core.services.QuizResultService;
import com.griddynamics.gridquiz.core.services.ReportService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import static com.griddynamics.gridquiz.core.services.result.DefaultQuizResultService.getResultTime;
import static com.griddynamics.gridquiz.core.services.result.DefaultQuizResultService.toMinutes;
import static org.jooq.lambda.Seq.seq;

@Service
public class DefaultReportService implements ReportService {
    private XSSFWorkbook book;

    @Autowired
    private QuizResultService resultService;

    @Override
    public byte[] generateReport() throws IOException {
        book = new XSSFWorkbook();

        XSSFSheet sheet = book.createSheet("GridQuiz Report + " + LocalDate.now());
        createHeader(sheet);

        seq(resultService.getUserResults())
                .zipWithIndex()
                .forEach(ri -> {
                            int index = Math.toIntExact(ri.v2) + 1;
                            UserResultsModel ur = ri.v1;
                            XSSFRow row = sheet.createRow(index);


                            int j = 0;
                            XSSFCell id = row.createCell(j);
                            id.setCellValue(ur.getUser().getId());
                            XSSFCellStyle idStyle = defaultStyle();
                            idStyle.setAlignment(HorizontalAlignment.RIGHT);
                            idStyle.setFont(numberFont());
                            id.setCellStyle(idStyle);
                            j++;

                            XSSFCell name = row.createCell(j);
                            name.setCellValue(ur.getUser().getName());
                            XSSFCellStyle nameStyle = defaultStyle();
                            nameStyle.setAlignment(HorizontalAlignment.RIGHT);
                            name.setCellStyle(nameStyle);
                            j++;

                            XSSFCell email = row.createCell(j);
                            email.setCellValue(ur.getUser().getEmail());
                            XSSFCellStyle emailStyle = defaultStyle();
                            emailStyle.setAlignment(HorizontalAlignment.RIGHT);
                            email.setCellStyle(emailStyle);
                            j++;

                            XSSFCell phone = row.createCell(j);
                            phone.setCellValue(ur.getUser().getPhone());
                            XSSFCellStyle phoneStyle = defaultStyle();
                            phoneStyle.setAlignment(HorizontalAlignment.RIGHT);
                            phoneStyle.setFont(numberFont());
                            phone.setCellStyle(phoneStyle);
                            j++;

                            XSSFCell cell1 = row.createCell(j);
                            cell1.setCellStyle(nameStyle);
                            j++;

                            XSSFCell cell2 = row.createCell(j);
                            cell2.setCellStyle(nameStyle);
                            j++;

                            XSSFCell cell3 = row.createCell(j);
                            cell3.setCellStyle(nameStyle);
                            j++;

                            j -= 3;

                            int generalJ = j;
                            seq(ur.getResults())
                                    .filter(r -> "General".equals(r.getQuiz().getName()))
                                    .findFirst()
                                    .ifPresent(r -> {
                                                XSSFCell general = row.createCell(generalJ);
                                                general.setCellValue(r.getPoints() + "\t[" + toMinutes(getResultTime(r.getStartTime(), r.getEndTime())) + "]");
                                                XSSFCellStyle generalStyle = defaultStyle();
                                                generalStyle.setFont(general());
                                                general.setCellStyle(generalStyle);
                                            }
                                    );
                            j++;
                            int javaJ = j;
                            seq(ur.getResults())
                                    .filter(r -> "Java".equals(r.getQuiz().getName()))
                                    .findFirst()
                                    .ifPresent(r -> {
                                                XSSFCell java = row.createCell(javaJ);
                                                java.setCellValue(r.getPoints() + "\t[" + toMinutes(getResultTime(r.getStartTime(), r.getEndTime())) + "]");
                                                XSSFCellStyle javaStyle = defaultStyle();
                                                javaStyle.setFont(java());
                                                java.setCellStyle(javaStyle);
                                            }
                                    );
                            j++;
                            int devopsJ = j;
                            seq(ur.getResults())
                                    .filter(r -> "DevOps".equals(r.getQuiz().getName()))
                                    .findFirst()
                                    .ifPresent(r -> {
                                                XSSFCell devops = row.createCell(devopsJ);
                                                devops.setCellValue(r.getPoints() + "\t[" + toMinutes(getResultTime(r.getStartTime(), r.getEndTime())) + "]");
                                                XSSFCellStyle devopsStyle = defaultStyle();
                                                devopsStyle.setFont(devops());
                                                devops.setCellStyle(devopsStyle);
                                            }
                                    );
                        }
                );

        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i, true);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        book.write(bos);
        book.close();
        return bos.toByteArray();
    }

    private void createHeader(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle defaultStyle = defaultStyle();

        XSSFCell id = row.createCell(0);
        id.setCellValue("id");
        id.setCellStyle(defaultStyle);

        XSSFCell name = row.createCell(1);
        name.setCellValue("Name");
        name.setCellStyle(defaultStyle);

        XSSFCell email = row.createCell(2);
        email.setCellValue("Email");
        email.setCellStyle(defaultStyle);

        XSSFCell phone = row.createCell(3);
        phone.setCellValue("Phone");
        phone.setCellStyle(defaultStyle);

        XSSFCell general = row.createCell(4);
        general.setCellValue("General");
        defaultStyle.setFont(general());
        general.setCellStyle(defaultStyle);

        XSSFCell java = row.createCell(5);
        java.setCellValue("Java");
        defaultStyle.setFont(java());
        java.setCellStyle(defaultStyle);

        XSSFCell devops = row.createCell(6);
        devops.setCellValue("DevOps");
        defaultStyle.setFont(devops());
        devops.setCellStyle(defaultStyle);
    }

    private XSSFCellStyle defaultStyle() {
        XSSFCellStyle style = book.createCellStyle();

        style.setWrapText(true);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        XSSFColor borderColor = new XSSFColor(new Color(0, 0, 0));

        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, borderColor);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, borderColor);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, borderColor);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, borderColor);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(new XSSFColor(new Color(43, 43, 43)));
        style.setFont(standardFont());

        return style;
    }

    private XSSFFont standardFont() {
        XSSFFont f = book.createFont();
        f.setFontName("Helvetica");
        f.setFontHeightInPoints((short) 10);
        f.setColor(new XSSFColor(new Color(169, 183, 198)));
        return f;
    }

    private XSSFFont numberFont() {
        XSSFFont f = book.createFont();
        f.setFontName("Helvetica");
        f.setFontHeightInPoints((short) 10);
        f.setColor(new XSSFColor(new Color(104, 151, 187)));
        return f;
    }

    private XSSFFont general() {
        XSSFFont f = book.createFont();
        f.setFontName("Helvetica");
        f.setFontHeightInPoints((short) 10);
        f.setColor(new XSSFColor(new Color(106, 168, 79)));
        return f;
    }

    private XSSFFont java() {
        XSSFFont f = book.createFont();
        f.setFontName("Helvetica");
        f.setFontHeightInPoints((short) 10);
        f.setColor(new XSSFColor(new Color(109, 158, 235)));
        return f;
    }

    private XSSFFont devops() {
        XSSFFont f = book.createFont();
        f.setFontName("Helvetica");
        f.setFontHeightInPoints((short) 10);
        f.setColor(new XSSFColor(new Color(255, 217, 102)));
        return f;
    }
}
