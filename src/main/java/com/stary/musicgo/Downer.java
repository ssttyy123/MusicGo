package com.stary.musicgo;
//*  bili

import java.io.File;
import java.io.OutputStream;

public class Downer extends Thread{
    private String downerForm;
    private OutputStream downerout;
    downAPIm dAPI;
    public Downer(String form){
        this.downerForm = form;
        if(form.equals("bili")){
            dAPI = new BilibiliDowner();
        }
    }

    public void DownStart(String url, String dir, String name, PlayList playList){
        boolean rt = dAPI.Start(url, dir, name);
        if(rt){
            playList.reFlushList();
        }
    }


}
