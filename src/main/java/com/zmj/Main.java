package com.zmj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;

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
        // if (!complete.exists()) {
        //     complete.mkdirs();
        // }
        int interval = 10;
        logger.info("请将文件放到以下目录中, 程序每{}秒扫描一次此目录\n{}", interval, source.getCanonicalPath());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    scanningDirectory(source);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }, 0, interval * 1000);
        // logger.info("End...");
    }
    
    private static void scanningDirectory(File source) throws IOException {
        File[] files = source.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv") && !name.startsWith("~$");
            }
        });
        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                // 读文件计算
                FileOperate.readFile(Files.newInputStream(file.toPath()), fileName);
                // 移动文件
                // String fileNameOrigin = fileName.substring(0, fileName.lastIndexOf("."));
                // FileUtils.moveFile(file, new File("complete/" + fileNameOrigin + "_" + System.currentTimeMillis() + ".csv"));
            }
        } else {
            logger.info("没有扫描到文件, 请将文件放到" + source.getCanonicalPath() + "目录下!");
        }
    }
}