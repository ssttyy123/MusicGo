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
    public void onclick_search(TextField enterBox, TableView<ListFileCell> tableView, String rootdir, HistroySave histroySave) {
        new Thread(()->{
            String key = enterBox.getText();
            List<ListFileCell> lists = new ArrayList<>();

            Downer downer = new Downer("Bilibili");
            File fp = new File("C:/ProgramData/MusicGo/resources/jsonF/bilisearch.json");
            if(downer.Search(key, fp.getAbsolutePath(), rootdir, histroySave)){
                try{
                    ObservableList<SearchList> orlist = downer.secList();
                    for(SearchList i : orlist){
                        lists.add(new ListFileCell(i.getName(), i.getAut(), i.getUrl(), i.getForm()));
                    }

                }
                catch (IOException e){
                    System.out.println("loading serlist err");
                    e.printStackTrace();
                }
            }
            downer = new Downer("Wangyicloud");
            fp = new File("C:/ProgramData/MusicGo/resources/jsonF/wangyicloudsearch.json");
            if(downer.Search(key, fp.getAbsolutePath(), rootdir, histroySave)){
                try{
                    ObservableList<SearchList> orlist = downer.secList();
                    for(SearchList i : orlist){
                        lists.add(new ListFileCell(i.getName(), i.getAut(), i.getUrl(), i.getForm()));
                    }

                }
                catch (IOException e){
                    System.out.println("loading serlist err");
                    e.printStackTrace();
                }
            }
            tableView.setItems(FXCollections.observableList(lists));
        }).start();
    }

    public void onclick_play(AudioPlayer audioPlayer, Button playb, String rootdir) throws FileNotFoundException {
        if(audioPlayer.isplay()){
            //new FileInputStream(rootdir + "img/Previous track.png")
            ImageView stopIm = new ImageView(new Image(new FileInputStream(rootdir + "img/play.png")));
            stopIm.setFitHeight(15.0);
            stopIm.setFitWidth(15.0);
            audioPlayer.pause();
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

    public void onclick_down(String url,
                             String dir,
                             String name,
                             String aut,
                             PlayList playList,
                             TableView<ListFileCell> tableView,
                             String rootdir,
                             DownUI downUI,
                             String form){
        System.out.println(form);
            Downer downer = new Downer(form);
            downer.sureDown(url, dir, name, aut, playList, rootdir, downUI, tableView);
    }

    public void onclick_del(String uri, AudioPlayer audioPlayer, PlayList playList, TableView<ListFileCell> tableView){
        new Thread(()->{
            if(audioPlayer.isThisPlay(uri)){
                audioPlayer.stop();
            }
            File file = new File(uri);
            if (file.isFile() && file.exists()) {
                if(!file.delete()){
                    System.out.println("删除文件失败");
                }
            }
            playList.reFlushList();
            tableView.setItems(FXCollections.observableArrayList(playList.getFileList()));
        }).start();
    }

    public void onclick_setting(SettingStage settingStage){
        settingStage.getStage().show();
    }
}