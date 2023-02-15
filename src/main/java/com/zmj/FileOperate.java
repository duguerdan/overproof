package com.zmj;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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
        
        BigDecimal[][] data = new BigDecimal[4][15];
        
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            // 最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                for (int j = 2; j < row.getLastCellNum(); j++) {
                    data[i - 1][j - 2] = BigDecimal.valueOf(row.getCell(j).getNumericCellValue());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean flag = false;
        for (int i = 0; i < data[0].length; i++) {
            BigDecimal val1 = data[0][i].add(data[1][i]);
            BigDecimal val2 = data[0][i].subtract(data[2][i]);
            if (val1.compareTo(data[3][i]) > 0 || val2.compareTo(data[3][i]) < 0) {
                flag = true;
                logger.warn("文件" + fileName + " 第 " + (i + 2) + " 列超差!");
            }
        }
        if (flag) {
            Media.play();
        }
    }
}
