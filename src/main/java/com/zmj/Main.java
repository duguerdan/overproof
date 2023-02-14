package com.zmj;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author zhangmingjian
 * @date ${DATE}
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello world!");
        
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\zgw4526\\Desktop\\overproof\\1111.csv");
        FileOperate.readExcel(fileInputStream);
        
    }
}