package com.stary.musicgo;
//*  bili

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public void DownStart(String url, String dir, String name, PlayList playList){
        boolean rt = dAPI.Start(url, dir, name);
        if(rt){
            playList.reFlushList();
        }
    }

    public void Search(String key, String jsonpath){
        dAPI.Search(key, jsonpath);
    }

    public List<SearchList> secList() throws IOException {
        return objectMapper.readValue(new File("src/main/resources/jsonF/bilisearch.json"), new TypeReference<List<SearchList>>(){});
    }

}
