package com.myproject.CNC;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    public String exportTable(JTable table, String hoten, String email, String sdt, double tongTienSP, double luongtong, double tongTienThue, double tongTienViPham, double luongCung, double tongTienThucNhan, String filename) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        TableModel model = table.getModel();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 14);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(cellFont);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        CellStyle orangeCellStyle = workbook.createCellStyle();
        orangeCellStyle.setFont(cellFont);
        orangeCellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        orangeCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        orangeCellStyle.setBorderBottom(BorderStyle.THIN);
        orangeCellStyle.setBorderTop(BorderStyle.THIN);
        orangeCellStyle.setBorderLeft(BorderStyle.THIN);
        orangeCellStyle.setBorderRight(BorderStyle.THIN);
        orangeCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font cellFontTitle = workbook.createFont();
        cellFontTitle.setFontHeightInPoints((short) 20);
        CellStyle CellStyleTitle = workbook.createCellStyle();
        CellStyleTitle.setFont(cellFontTitle);
        CellStyleTitle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        CellStyleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        CellStyleTitle.setBorderBottom(BorderStyle.THIN);
        CellStyleTitle.setBorderTop(BorderStyle.THIN);
        CellStyleTitle.setBorderLeft(BorderStyle.THIN);
        CellStyleTitle.setBorderRight(BorderStyle.THIN);
        CellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        CellStyle headerCellStyleCenter = workbook.createCellStyle();
        headerCellStyleCenter.setFont(headerFont);
        headerCellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        Row rowTitle = sheet.createRow(0);
        Cell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue("Công ty Helium Media");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        cellTitle.setCellStyle(CellStyleTitle);
        Row row1 = sheet.createRow(1);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Họ và tên");
        cell1.setCellStyle(cellStyle);
        Cell cell11 = row1.createCell(1);
        cell11.setCellValue(hoten);
        cell11.setCellStyle(cellStyle);
        Row row2 = sheet.createRow(2);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("Email");
        cell2.setCellStyle(cellStyle);
        Cell cell22 = row2.createCell(1);
        cell22.setCellValue(email);
        cell22.setCellStyle(cellStyle);
        Row row3 = sheet.createRow(3);
        Cell cell3 = row3.createCell(0);
        cell3.setCellValue("Số điện thoại");
        cell3.setCellStyle(cellStyle);
        Cell cell33 = row3.createCell(1);
        cell33.setCellValue(sdt);
        cell33.setCellStyle(cellStyle);
        int startRow = 4;
        Row row4 = sheet.createRow(startRow);

        int giaTienColumnIndex;
        for (giaTienColumnIndex = 0; giaTienColumnIndex < model.getColumnCount(); ++giaTienColumnIndex) {
            Cell cell = row4.createCell(giaTienColumnIndex);
            cell.setCellValue(model.getColumnName(giaTienColumnIndex));
            cell.setCellStyle(headerCellStyle);
        }

        giaTienColumnIndex = -1;

        for (int i = 0; i < model.getColumnCount(); ++i) {
            if ("Giá tiền".equals(model.getColumnName(i))) {
                giaTienColumnIndex = i;
                break;
            }
        }

        DecimalFormat currencyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setMaximumFractionDigits(0);
        currencyFormat.setGroupingUsed(true);
        currencyFormat.setPositiveSuffix(" đ");
        DecimalFormat currencyFormat2 = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat2.setMaximumFractionDigits(0);
        currencyFormat2.setGroupingUsed(true);

        int summaryStartRow;
        for (summaryStartRow = 0; summaryStartRow < model.getRowCount(); ++summaryStartRow) {
            Row row = sheet.createRow(summaryStartRow + 5);

            for (int j = 0; j < model.getColumnCount(); ++j) {
                Cell cell = row.createCell(j);
                Object value = model.getValueAt(summaryStartRow, j);
                if (value != null) {
                    if (j == giaTienColumnIndex) {
                        try {
                            double numericValue = Double.parseDouble(value.toString().replace("đ", "").replace(",", "").trim());
                            cell.setCellValue(currencyFormat.format(numericValue));
                        } catch (NumberFormatException var66) {
                            cell.setCellValue(value.toString());
                        }
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }

                cell.setCellStyle(cellStyle);
            }
        }

        summaryStartRow = model.getRowCount() + 6;
        String[] summaryLabels = new String[]{"Tổng tiền sản phẩm", "Lương cứng", "Lương tổng", "Tiền thuế", "Tiền vi phạm", "Tiền thực nhận"};
        Object[] summaryValues = new Object[]{tongTienSP, luongCung, luongtong, tongTienThue, tongTienViPham, tongTienThucNhan};

        int i;
        for (i = 0; i < summaryLabels.length; ++i) {
            Row row = sheet.createRow(summaryStartRow + i);
            Cell labelCell = row.createCell(0);
            labelCell.setCellValue(summaryLabels[i]);
            labelCell.setCellStyle(headerCellStyle);
            Cell valueCell = row.createCell(1);
            if ("Tiền thực nhận".equals(summaryLabels[i])) {
                valueCell.setCellStyle(orangeCellStyle);
                valueCell.setCellValue(currencyFormat.format(summaryValues[i]));

            } else {
                valueCell.setCellStyle(headerCellStyle);
                valueCell.setCellValue(currencyFormat.format(summaryValues[i]));
            }
        }

        for (i = 0; i < model.getColumnCount(); ++i) {
            sheet.autoSizeColumn(i);
        }

        String filePath = filename;
        if (!filename.endsWith(".xlsx")) {
            filePath = filename + ".xlsx";
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);

            try {
                workbook.write(fileOut);
                System.out.println("Tệp đã được ghi tại: " + filePath);
            } catch (Throwable var63) {
                try {
                    fileOut.close();
                } catch (Throwable var62) {
                    var63.addSuppressed(var62);
                }

                throw var63;
            }

            fileOut.close();
        } catch (IOException var64) {
            var64.printStackTrace();
            return "err";
        } finally {
            workbook.close();
        }

        return filePath;

    }
}
