package com.stary.musicgo;

import java.io.File;
import java.io.IOException;
import java.lang.*;

public class BilibiliDowner implements downAPIm{
    private String url = new String();
    private String dir = new String();
    private String name = new String();

    public BilibiliDowner(String url, String dir, String name){
        this.url = url;
        this.dir = dir;
        this.name = name;
    }
    //https://www.bilibili.com/video/BV1sx411A778/ D:/BilibiliDown/ music
    //url dirpath name
    public void Start(){
        File fp = new File("src/DownerAPI/bilibiliapi.exe");
        String cmdt = fp.getAbsolutePath() + " " + url + " " + dir + " " + name;
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