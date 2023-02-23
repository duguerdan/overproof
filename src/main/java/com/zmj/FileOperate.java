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
import java.time.LocalTime;

/**
 * @author zhangmingjian
 * @date 2023/2/14
 */
public class FileOperate {
    private static final Logger logger = LoggerFactory.getLogger(FileOperate.class);
    
    /**
     * 读取excel
     */
    public static void readExcel(InputStream inputStream, String fileName) {
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream)
        ) {
            BigDecimal[][] data = new BigDecimal[4][15];
            Sheet sheet = workbook.getSheetAt(0);
            // 最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            Cell cell = sheet.getRow(lastRowNum).getCell(1);
            LocalTime localTime = cell.getLocalDateTimeCellValue().toLocalTime();
            Duration duration = Duration.between(localTime, LocalTime.now());
            long millis = duration.toMillis();
            if (millis > 15 * 1000) {
                logger.info("文件{},无新数据", fileName);
                return;
            }
            // logger.info("==========读取文件: {}", fileName);
    
            logger.info("读取文件{},最新一行行号:{}", fileName, lastRowNum + 1);
            data[0] = readRow(sheet.getRow(1));
            data[1] = readRow(sheet.getRow(2));
            data[2] = readRow(sheet.getRow(3));
            data[3] = readRow(sheet.getRow(lastRowNum));
    
            boolean flag = false;
            for (int i = 0; i < data[0].length; i++) {
                BigDecimal val1 = data[0][i].add(data[1][i]);
                BigDecimal val2 = data[0][i].add(data[2][i]);
                if (data[3][i].compareTo(val1) > 0 || data[3][i].compareTo(val2) < 0) {
                    flag = true;
                    // logger.info(val1 + "====" + val2 + "====" + data[3][i]);
                    logger.warn("文件" + fileName + " 第 " + (i + 2) + " 列超差!");
                }
            }
            if (flag) {
                Media.play();
            }
        } catch (Exception e) {
            logger.error("文件读取错误,{}", e.getMessage());
        }
    }
    
    private static BigDecimal[] readRow(Row row) {
        BigDecimal[] rowData = new BigDecimal[15];
        for (int j = 2; j < row.getLastCellNum(); j++) {
            rowData[j - 2] = BigDecimal.valueOf(row.getCell(j).getNumericCellValue());
        }
        return rowData;
    }
}
