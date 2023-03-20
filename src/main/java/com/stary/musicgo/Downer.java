package com.stary.musicgo;
//*  bili

public class Downer {
    String downerForm = new String();
    downAPIm dAPI;
    public Downer(String form){
        this.downerForm = form;
        if(form.equals("bili")){
            dAPI = new BilibiliDowner();
        }
    }
}
