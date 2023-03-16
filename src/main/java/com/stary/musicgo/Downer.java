package com.stary.musicgo;
//bili

public class Downer {
    String downerForm = new String();
    private BilibiliDowner bilid;
    public Downer(String form, String url, String dir, String name){
        this.downerForm = form;
        if(form.equals("bili")){
            bilid = new BilibiliDowner(url, dir, name);
        }
    }

}
