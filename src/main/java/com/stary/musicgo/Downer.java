package com.stary.musicgo;
//*  bili

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Downer {
    private downAPIm dAPI;
    private final String downerform;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Downer(String form){
        downerform = form;
        if(form.equals("Bilibili")){
            dAPI = new BilibiliDowner();
        } else if (form.equals("Wangyicloud")) {
            dAPI = new WangyicloudDowner();
        }
    }

    public void sureDown(String url,
                         String dir,
                         String name,
                         String aut,
                         PlayList playList,
                         String rootdir,
                         DownUI downUI,
                         TableView<ListFileCell> tableView){
        downUI.init(url, dir, name, aut, playList, rootdir, this, tableView);
        downUI.show();
    }

    public void DownStart(String url, String dir, String name, String aut, PlayList playList, String rootdir){
        boolean rt = dAPI.Start(url, dir, name, aut, rootdir);
        if(rt){
            playList.reFlushList();
        }
    }

    public boolean Search(String key, String jsonpath, String rootdir, HistroySave histroySave){
        if(Objects.equals(key, "")) return false;
        return dAPI.Search(key, jsonpath, rootdir, histroySave);
    }

    public ObservableList<SearchList> secList() throws IOException {
        if(Objects.equals(downerform, "Wangyicloud")){
            return FXCollections.observableList(objectMapper.readValue(new File("C:/ProgramData/MusicGo/resources/jsonF/wangyicloudsearch.json"), new TypeReference<List<SearchList>>(){}));

        }
        else if(Objects.equals(downerform, "Bilibili")){
            return FXCollections.observableList(objectMapper.readValue(new File("C:/ProgramData/MusicGo/resources/jsonF/bilisearch.json"), new TypeReference<List<SearchList>>(){}));
        }
        return null;
    }
}
