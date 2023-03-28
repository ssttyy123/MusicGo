package com.stary.musicgo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller{
    public void onclick_search(TextField enterBox, TableView<ListFileCell> tableView, String rootdir) {
        new Thread(()->{
            String key = enterBox.getText();
            Downer downer = new Downer("bili");
            File fp = new File("C:/ProgramData/MusicGo/resources/jsonF/bilisearch.json");
            if(downer.Search(key, fp.getAbsolutePath(), rootdir)){
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
        }).start();
    }

    public void onclick_play(AudioPlayer audioPlayer, Button playb, String rootdir) throws FileNotFoundException {
        if(audioPlayer.isplay()){
            //new FileInputStream(rootdir + "img/Previous track.png")
            ImageView stopIm = new ImageView(new Image(new FileInputStream(rootdir + "img/play.png")));
            stopIm.setFitHeight(15.0);
            stopIm.setFitWidth(15.0);
            audioPlayer.stop();
            playb.setGraphic(stopIm);
        }
        else {
            ImageView playIm = new ImageView(new Image(new FileInputStream(rootdir + "img/stop.png")));
            playIm.setFitHeight(15.0);
            playIm.setFitWidth(15.0);
            audioPlayer.play();
            playb.setGraphic(playIm);
        }
    }

    public void onclick_other_play(AudioPlayer audioPlayer, Button playb, String rootdir) throws FileNotFoundException {
        if(audioPlayer.isplay()){
            audioPlayer.play();
        }
        else {
            ImageView playIm = new ImageView(new Image(new FileInputStream(rootdir + "img/stop.png")));
            playIm.setFitHeight(15.0);
            playIm.setFitWidth(15.0);
            audioPlayer.play();
            playb.setGraphic(playIm);
        }
    }

    public void onclick_down(String url, String dir, String name, String aut, PlayList playList, TableView<ListFileCell> tableView, String rootdir){
        new Thread(()->{
            Downer downer = new Downer("bili");
            downer.DownStart(url, dir, name, aut, playList, rootdir);
            tableView.setItems(FXCollections.observableArrayList(playList.getFileList()));
        }).start();

    }

    public void onclick_del(String uri, AudioPlayer audioPlayer, PlayList playList, TableView<ListFileCell> tableView){
        new Thread(()->{
            if(audioPlayer.isThisPlay(uri)){
                audioPlayer.stop();
            }
            File file = new File(uri);
            if (file.isFile() && file.exists()) {
                boolean rt = file.delete();
            }
            playList.reFlushList();
            tableView.setItems(FXCollections.observableArrayList(playList.getFileList()));
        }).start();
    }

    public void onclick_setting(){

    }
}