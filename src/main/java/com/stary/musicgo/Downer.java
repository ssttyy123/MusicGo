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

public class Downer {
    private String downerForm;
    private downAPIm dAPI;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Downer(String form){
        this.downerForm = form;
        if(form.equals("bili")){
            dAPI = new BilibiliDowner();
        }
    }

    public void sureDown(String url, String dir, String name, String aut, PlayList playList, String rootdir, DownUI downUI, TableView<ListFileCell> tableView){
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
        return dAPI.Search(key, jsonpath, rootdir, histroySave);
    }

    public ObservableList<SearchList> secList() throws IOException {
        return FXCollections.observableList(objectMapper.readValue(new File("C:/ProgramData/MusicGo/resources/jsonF/bilisearch.json"), new TypeReference<List<SearchList>>(){}));
    }
}
