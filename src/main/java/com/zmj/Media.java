package com.zmj;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URI;

/**
 * @author zhangmingjian
 * @date 2023/2/15
 */
public class Media {
    
    public static void play() {
        // String context = Objects.requireNonNull(Media.class.getClassLoader().getResource("")).getPath();
        String path = "prompt.wav";
        File file = new File(path);
        URI uri = file.toURI();
        AudioClip audioClip = new AudioClip(uri.toString());
        // audioClip.setCycleCount(2);// 播放次数
        audioClip.play();
    
        /*while (audioClip.isPlaying()) {
            try {
                // 延迟，不关闭main用于播放音乐
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
    }
    
    public static void main(String[] args) {
        play();
    }
}
