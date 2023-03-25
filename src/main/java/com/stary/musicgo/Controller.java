package com.stary.musicgo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller{
    public void onclick_search(TextField enterBox, TableView<ListFileCell> tableView) {
        String key = enterBox.getText();
        Downer downer = new Downer("bili");
        File fp = new File("src/main/resources/jsonF/bilisearch.json");
        if(downer.Search(key, fp.getAbsolutePath())){
            try{
                ObservableList<SearchList> orlist = downer.secList();
                List<ListFileCell> lists = new ArrayList<ListFileCell>();
                for(SearchList i : orlist){
                    lists.add(new ListFileCell(i.getName(), i.getAut(), i.getUrl()));
                }
                tableView.setItems(FXCollections.observableList(lists));
            }
            catch (IOException e){
                System.out.println("loading serlist err");
                e.printStackTrace();
            }
        }
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

    public void onclick_down(String url, String dir, String name, String aut, PlayList playList, TableView<ListFileCell> tableView){
        Downer downer = new Downer("bili");
        downer.DownStart(url, dir, name, aut, playList);
        tableView.setItems(FXCollections.observableArrayList(playList.getFileList()));
    }

    public void onclick_setting(){

    }
}