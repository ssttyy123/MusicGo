package com.stary.musicgo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;

public class Controller{
    public void onclick_search(TextField enterBox){
        String key = enterBox.getText();
        Downer downer = new Downer("bili");
        File fp = new File("src/main/resources/jsonF/test.json");
        downer.dAPI.Search(key, fp.getAbsolutePath());
    }

    public void onclick_play(AudioPlayer audioPlayer){
        audioPlayer.play();
        System.out.println(audioPlayer.getEndTime());
    }

    public  void onclick_down(String url, String dir, String name){
        Downer downer = new Downer("bili");
        downer.dAPI.Start(url, dir, name);
    }
    public void onclick_setting(){

    }
}