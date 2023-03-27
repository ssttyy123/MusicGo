package com.stary.musicgo;
//*  bili

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Downer extends Thread{
    private String downerForm;
    private OutputStream downerout;
    downAPIm dAPI;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Downer(String form){
        this.downerForm = form;
        if(form.equals("bili")){
            dAPI = new BilibiliDowner();
        }
    }

    public void DownStart(String url, String dir, String name, String aut, PlayList playList, String rootdir){
        boolean rt = dAPI.Start(url, dir, name, aut, rootdir);
        if(rt){
            playList.reFlushList();
        }
    }

    public boolean Search(String key, String jsonpath, String rootdir){
        return dAPI.Search(key, jsonpath, rootdir);
    }

    public ObservableList<SearchList> secList() throws IOException {
        return FXCollections.observableList(objectMapper.readValue(new File("C:/ProgramData/MusicGo/resources/jsonF/bilisearch.json"), new TypeReference<List<SearchList>>(){}));
    }

}
