package com.stary.musicgo;

import java.io.File;
import java.io.IOException;
import java.lang.*;

public class BilibiliDowner implements downAPIm{
    //https://www.bilibili.com/video/BV1sx411A778/ D:/BilibiliDown/ music
    // down url savedir filename
    //ser key jsonpath
    public void Start(String url, String dir, String name){
        File fp = new File("src/DownerAPI/bilibiliapi.exe");
        String cmdt = fp.getAbsolutePath() + "down" + " " + url + " " + dir + " " + name;
        Process process = null;
        int rtVal = 0;
        try {
            process = Runtime.getRuntime().exec(cmdt);
            rtVal = process.waitFor();
        }
        catch (IOException | InterruptedException e){
            System.out.println("执行失败");
            e.printStackTrace();
        }
        if(rtVal != 0){
            throw new RuntimeException("执行失败");
        }
    }

    public void Search(String key, String jsonpath){
        File fp = new File("src/DownerAPI/bilibiliapi.exe");
        String cmdt = fp.getAbsolutePath() + "ser" + " " + key + " " + jsonpath;
        Process process = null;
        int rtVal = 0;
        try {
            process = Runtime.getRuntime().exec(cmdt);
            rtVal = process.waitFor();
        }
        catch (IOException | InterruptedException e){
            System.out.println("执行失败");
            e.printStackTrace();
        }
        if(rtVal != 0){
            throw new RuntimeException("执行失败");
        }
    }
}