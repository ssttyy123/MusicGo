package com.stary.musicgo;

import java.io.File;
import java.io.IOException;

public class BilibiliDowner implements downAPIm{
    //https://www.bilibili.com/video/BV1sx411A778/ D:/BilibiliDown/ music
    // down url savedir filename
    //ser key jsonpath

    //#DownOver
    public boolean Start(String url, String dir, String name, String aut, String rootdir){
        File fp = new File(rootdir + "DownAPI/bilibiliapi.exe");
        File diruri = new File(dir);
        //String cmdt = fp.getAbsolutePath() + " down" + " https://" + url + " " + diruri.getAbsolutePath() + "\\ " + name + " " +aut;
        String[] cmdt = {fp.getAbsolutePath(), "down", "https://" + url, diruri.getAbsolutePath() + "\\", "\""+name+"\"", "\""+aut+"\""};
        Process process;
        int rtVal;
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
    public boolean Search(String key, String jsonpath, String rootdir, HistroySave histroySave) {
        File fp = new File(rootdir + "DownAPI/bilibiliapi.exe");
        //String cmdt = fp.getAbsolutePath() + " ser" + " " + key + " " + jsonpath + " " + histroySave.getSaveo().getKeyMajusculeForm() + " " + histroySave.getSaveo().getSearchReForm();
        String[] cmdt = {fp.getAbsolutePath(),
                "ser",
                "\"" + key + "\"",
                jsonpath,
                String.valueOf(histroySave.getSaveo().getKeyMajusculeForm()),
                String.valueOf(histroySave.getSaveo().getSearchReForm())};
        Process process;
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
