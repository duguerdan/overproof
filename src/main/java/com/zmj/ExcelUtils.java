package com.zmj;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author zhangmingjian
 * @date 2023/3/8
 */
public class ExcelUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    
    public static boolean readExcel(InputStream inputStream, String fileName, BigDecimal[][] data) {
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            // 最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            Cell cell0 = sheet.getRow(lastRowNum).getCell(0);
            LocalDate localDate = cell0.getLocalDateTimeCellValue().toLocalDate();
            Cell cell1 = sheet.getRow(lastRowNum).getCell(1);
            LocalTime localTime = cell1.getLocalDateTimeCellValue().toLocalTime();
            
            LocalDateTime localDateTime = localDate.atTime(localTime);
            
            Duration duration = Duration.between(localDateTime, LocalDateTime.now());
            long millis = duration.toMillis();
            logger.info(LocalDateTime.now() + " - " + localDateTime + " = " + millis);
            if (millis > 12 * 1000) {
                logger.info("文件{},无新数据", fileName);
                return false;
            }
            // logger.info("==========读取文件: {}", fileName);
            
            logger.info("读取文件{},最新一行行号:{}", fileName, lastRowNum + 1);
            data[0] = readRow(sheet.getRow(1));
            data[1] = readRow(sheet.getRow(2));
            data[2] = readRow(sheet.getRow(3));
            data[3] = readRow(sheet.getRow(lastRowNum));
            
        } catch (Exception e) {
            logger.error("文件读取错误,{}", e.getMessage());
        }
        return true;
    }
    
    public static BigDecimal[] readRow(Row row) {
        BigDecimal[] rowData = new BigDecimal[15];
        for (int j = 2; j < row.getLastCellNum(); j++) {
            rowData[j - 2] = BigDecimal.valueOf(row.getCell(j).getNumericCellValue());
        }
        return rowData;
    }
}
