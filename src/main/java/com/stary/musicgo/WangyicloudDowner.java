package com.stary.musicgo;

import java.io.File;
import java.io.IOException;

public class WangyicloudDowner implements downAPIm{
//# down('1410647903', "D:/MusicGodown/test.mp3")
//# search("带我去找夜生活", 0, 0)
    @Override
    public boolean Start(String url, String dir, String name, String aut, String rootdir) {
        File fp = new File(rootdir + "DownAPI/wangyicloudapi.exe");
        File diruri = new File(dir);
        String[] cmdt = {fp.getAbsolutePath(), "down", url, diruri.getAbsolutePath() + "\\", "\""+name+"\"", "\""+aut+"\""};
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

    @Override
    public boolean Search(String key, String jsonpath, String rootdir, HistroySave histroySave) {
        File fp = new File(rootdir + "DownAPI/wangyicloudapi.exe");
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
