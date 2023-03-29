package com.stary.musicgo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HistroySave {
    private SaveJson saveo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File savejson = new File("C:/ProgramData/MusicGo/resources/jsonF/save.json");

    public HistroySave() throws IOException {
        write2Obj();
    }

    private void firstwriteObj() throws IOException {
        //String downerDir, String playingMusicUri, String localPath, String backgroundPath, int closeForm, int nameForm, int keyMajusculeForm, int searchReForm
        saveo.initc("D:/MusicGodown", "D:/MusicGodown/test.mp3", List.of("D:/MusicGodown"), "null", 0, 0, 0, 0);
        objectMapper.writeValue(savejson, saveo);
    }

    private void write2Obj() throws IOException {
        if(savejson.exists()){
            try{saveo = objectMapper.readValue(savejson, SaveJson.class);}
            catch (java.io.IOException e){
                try(FileWriter fileWriter = new FileWriter(savejson)){
                    fileWriter.write("");
                    fileWriter.flush();
                }
                firstwriteObj();
                write2Obj();

            }
        }
        else {
            if(savejson.createNewFile()) {
                saveo = new SaveJson();
                firstwriteObj();
            }
            else {
                System.out.println("操作错误:json文件被未知操作删除");
            }
        }
    }

    public SaveJson getSaveo(){
        return saveo;
    }

    public void reflushJson() {
        try{
            if(savejson.exists()){
                objectMapper.writeValue(savejson, saveo);
            }
            else {
                if(savejson.createNewFile()) objectMapper.writeValue(savejson, saveo);
                else objectMapper.writeValue(savejson, saveo);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
