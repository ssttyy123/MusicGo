package com.stary.musicgo;

import java.io.*;
import java.lang.*;

public class BilibiliDowner implements downAPIm{
    //https://www.bilibili.com/video/BV1sx411A778/ D:/BilibiliDown/ music
    // down url savedir filename
    //ser key jsonpath

    //#DownOver
    public boolean Start(String url, String dir, String name, String aut, String rootdir){
        File fp = new File(rootdir + "DownAPI/bilibiliapi.exe");
        File diruri = new File(dir);
        String cmdt = fp.getAbsolutePath() + " down" + " https://" + url + " " + diruri.getAbsolutePath() + "\\ " + name + " " +aut;
        Process process;
        int rtVal = 0;
        try {
            process = Runtime.getRuntime().exec(cmdt);
            rtVal = process.waitFor();
            if(rtVal != 0){
                throw new RuntimeException("执行失败");
            }
            return true;
        }
        catch (IOException | InterruptedException e){
            System.out.println("执行失败");
            e.printStackTrace();
        }
        return false;
    }

    //#SearchOver
    public boolean Search(String key, String jsonpath, String rootdir) {
        File fp = new File(rootdir + "DownAPI/bilibiliapi.exe");
        String cmdt = fp.getAbsolutePath() + " ser" + " " + key + " " + jsonpath;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdt);
            int rtVal = process.waitFor();
            if (rtVal != 0) {
                throw new RuntimeException("执行失败");
            }
            return true;
        } catch (IOException | InterruptedException e) {
            System.out.println("执行失败");
            e.printStackTrace();
        }
        return false;
    }
}
