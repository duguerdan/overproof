package com.zmj;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangmingjian
 * @date 2023/2/14
 */
public class FileOperate {
    
    /**
     * 读取excel
     */
    public static void readExcel(InputStream inputStream) {
        
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            // 最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            System.out.println(lastRowNum);
            for (int i = 0; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                System.out.println(row.getLastCellNum());
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    System.out.print(row.getCell(j) + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
}
