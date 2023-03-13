package com.zmj;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhangmingjian
 * @date 2023/3/8
 */
public class CSVUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);
    
    public static boolean readCSV(InputStream inputStream, String fileName, BigDecimal[][] data) throws IOException, ParseException, CsvException {
        
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        try (
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withCSVParser(csvParser).build();
        ) {
            
            List<String[]> list = csvReader.readAll();
            int size = list.size();
            String dateStr = list.get(size - 1)[0];
            String timeStr = list.get(size - 1)[1];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            long date = simpleDateFormat.parse(dateStr + " " + timeStr).getTime();
            long now = new Date().getTime();
            long millis = now - date;
            // logger.info(date + " - " + now + " = " + millis);
            if (millis > 12 * 1000) {
                logger.info("文件{},无新数据", fileName);
                return false;
            }
            
            logger.info("读取文件{},最新一行行号:{}", fileName, size);
            data[0] = readRow(list.get(1));
            data[1] = readRow(list.get(2));
            data[2] = readRow(list.get(3));
            data[3] = readRow(list.get(size - 1));
            
        } catch (Exception e) {
            logger.error("文件读取错误,{}", e.getMessage());
            throw e;
        }
        return true;
    }
    
    public static BigDecimal[] readRow(String[] row) {
        BigDecimal[] rowData = new BigDecimal[row.length - 2];
        for (int i = 2; i < row.length; i++) {
            String val = row[i];
            if (StringUtils.isNotBlank(val)) {
                try {
                    rowData[i - 2] = new BigDecimal(val);
                } catch (Exception ignored) {
                }
            }
        }
        return rowData;
    }
    
}
