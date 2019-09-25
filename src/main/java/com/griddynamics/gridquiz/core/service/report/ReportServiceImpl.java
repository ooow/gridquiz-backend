package com.griddynamics.gridquiz.core.service.report;

import static com.griddynamics.gridquiz.repository.model.Role.Enum.ADMIN;
import static java.util.stream.Collectors.toMap;

import com.griddynamics.gridquiz.repository.QuizRepository;
import com.griddynamics.gridquiz.repository.ResultRepository;
import com.griddynamics.gridquiz.repository.RoleRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Quiz;
import com.griddynamics.gridquiz.repository.model.Result;
import com.griddynamics.gridquiz.repository.model.Role;
import com.griddynamics.gridquiz.repository.model.User;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    private XSSFWorkbook book;
    private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.UK)
            .withZone(ZoneId.systemDefault());

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public byte[] generateReport() {
        book = new XSSFWorkbook();

        // Create sheet
        XSSFSheet sheet = book.createSheet(String.format("GridQuiz Report $s", LocalDate.now()));

        // Add header row
        createHeader(sheet);

        // Get quizzes map<ID, Name>
        Role admin = roleRepository.findByRole(ADMIN).get();
        Map<String, String> quizzes =
                quizRepository.findAll().stream().collect(toMap(Quiz::getId, Quiz::getName));

        // Find all users results
        Map<User, List<Result>> reports = userRepository.findAll()
                .stream()
                .filter(u -> !u.getRoles().contains(admin))
                .collect(toMap(u -> u, u -> resultRepository.findAllByUserId(u.getId())));

        populateRows(sheet, reports, quizzes);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            book.write(bos);
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    private void populateRows(XSSFSheet sheet,
                              Map<User, List<Result>> reports,
                              Map<String, String> quizzes) {
        int columnsSize = 0; // Columns size depends on passed quizzes and results.
        int index = 1; // Start after header row.
        for (Map.Entry<User, List<Result>> report : reports.entrySet()) {
            XSSFRow row = sheet.createRow(index + 1);
            User user = report.getKey();
            List<Result> results = report.getValue();
            fillCell(row, 0, user.getId());
            fillCell(row, 1, user.getName());
            fillCell(row, 2, user.getEmail());
            fillCell(row, 3, user.getPhone());
            int j = 4;
            for (Result result : results) {
                String quizName = quizzes.get(result.getQuizId());
                String dateTime = formatter.format(result.getStartTime());
                String r = String.format("%s [%s: %s/%s]", dateTime, quizName, result.getPoints(),
                                         result.getOutOf());
                fillCell(row, j, r);
                j++;
                if (j > columnsSize) {
                    columnsSize = j;
                }
            }
            index++;
        }

        for (int i = 0; i < columnsSize; i++) {
            sheet.autoSizeColumn(i, true);
        }
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
        general.setCellValue("Results");
        general.setCellStyle(defaultStyle);
    }

    private void fillCell(XSSFRow row, int column, String value) {
        XSSFCell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(defaultStyle());
    }

    private XSSFCellStyle defaultStyle() {
        XSSFCellStyle style = book.createCellStyle();

        style.setWrapText(true);

        style.setAlignment(HorizontalAlignment.LEFT);
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
}
