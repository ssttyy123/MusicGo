package com.stary.musicgo;
//bili

public class Downer {
    String downerForm = new String();
    private downAPIm bilid;
    public Downer(String form){
        this.downerForm = form;
        if(form.equals("bili")){
            bilid = new BilibiliDowner();
        }
    }

}
