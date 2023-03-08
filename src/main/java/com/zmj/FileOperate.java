package com.zmj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static void readFile(InputStream inputStream, String fileName) {
        BigDecimal[][] data = new BigDecimal[4][15];
    
        if (!ExcelUtils.readExcel(inputStream, fileName, data)) {
            return;
        }
    
        calculate(fileName, data);
    }
    
    public static void calculate(String fileName, BigDecimal[][] data) {
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
    }
    
    
}
