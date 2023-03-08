package com.zmj;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhangmingjian
 * @date 2023/3/8
 */
public class CSVUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);
    
    public static boolean readCSV(InputStream inputStream, String fileName, BigDecimal[][] data) {
        
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        try (
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withCSVParser(csvParser).build();
        ) {
            printInfo(csvReader);
            
            List<String[]> list = csvReader.readAll();
            list.forEach(row -> {
                for (String cell : row) {
                    System.out.print(cell + "\t\t");
                }
                System.out.println();
            });
            printInfo(csvReader);
            
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void printInfo(CSVReader csvReader) {
        long linesRead = csvReader.getLinesRead();
        System.out.println("linesRead:" + linesRead);
        boolean verifyReader = csvReader.verifyReader();
        System.out.println("verifyReader:" + verifyReader);
        int multilineLimit = csvReader.getMultilineLimit();
        System.out.println("multilineLimit:" + multilineLimit);
        long recordsRead = csvReader.getRecordsRead();
        System.out.println("recordsRead:" + recordsRead);
        int skipLines = csvReader.getSkipLines();
        System.out.println("skipLines:" + skipLines);
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("C:\\Users\\admin\\Desktop\\test.csv");
        boolean b = readCSV(inputStream, null, null);
    }
}
