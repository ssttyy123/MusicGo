package com.stary.musicgo;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

public class Controller{
    public void onclick_search(TextField enterBox){
        String key = enterBox.getText();
        Downer downer = new Downer("bili");
        File fp = new File("src/main/resources/jsonF/test.json");
        downer.dAPI.Search(key, fp.getAbsolutePath());
    }

    public void onclick_play(AudioPlayer audioPlayer, Button playb){
        if(audioPlayer.isplay()){
            ImageView stopIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/play.png"))));
            stopIm.setFitHeight(15.0);
            stopIm.setFitWidth(15.0);
            audioPlayer.stop();
            playb.setGraphic(stopIm);
        }
        else {
            ImageView playIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/stop.png"))));
            playIm.setFitHeight(15.0);
            playIm.setFitWidth(15.0);
            audioPlayer.play();
            playb.setGraphic(playIm);
        }
    }

    public void onclick_other_play(AudioPlayer audioPlayer, Button playb){
        if(audioPlayer.isplay()){
            audioPlayer.play();
        }
        else {
            ImageView playIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/stop.png"))));
            playIm.setFitHeight(15.0);
            playIm.setFitWidth(15.0);
            audioPlayer.play();
            playb.setGraphic(playIm);
        }
    }

    public void onclick_down(String url, String dir, String name, PlayList playList){
        Downer downer = new Downer("bili");
        downer.DownStart(url, dir, name, playList);
    }

    public void onclick_setting(){

    }
}