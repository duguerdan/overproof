package com.zmj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author zhangmingjian
 * @date 2021-2-15
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) throws IOException {
        logger.info("Run...");
        File source = new File("source");
        File complete = new File("complete");
        if (!source.exists()) {
            source.mkdirs();
        }
        if (!complete.exists()) {
            complete.mkdirs();
        }
        File[] files = source.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.equals(".gitkeep");
            }
        });
        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                // 读文件计算
                FileOperate.readExcel(Files.newInputStream(file.toPath()), fileName);
                // 移动文件
                // String fileNameOrigin = fileName.substring(0, fileName.lastIndexOf("."));
                // FileUtils.moveFile(file, new File("complete/" + fileNameOrigin + "_" + System.currentTimeMillis() + ".csv"));
            }
        } else {
            logger.info("没有目录或文件, 要将文件放到程序根目录下的source目录下!");
        }
        logger.info("End...");
    }
}