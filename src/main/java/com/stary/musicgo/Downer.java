package com.stary.musicgo;
//*  bili

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Downer extends Thread{
    private final Stage stage = new Stage();
    private final AnchorPane anchorPane = new AnchorPane();
    private final Label URLLabel = new Label("URL:");
    private final Label URLS = new Label();
    private final Label filenameLabel = new Label("文件名:");
    private final TextField filenameTextField = new TextField();
    private final Label singerLabel = new Label("演唱者:");
    private final TextField singerTextField = new TextField();
    private final Button currBut = new Button("确定");
    private final Button closeBut = new Button("取消");
    private final Scene scene = new Scene(anchorPane);

    private String downerForm;
    private OutputStream downerout;
    private downAPIm dAPI;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Downer(String form){
        this.downerForm = form;
        if(form.equals("bili")){
            dAPI = new BilibiliDowner();
        }
        URLLabel.setLayoutX(22.0);
        URLLabel.setLayoutY(15.0);
        URLS.setLayoutX(80.0);
        URLS.setLayoutY(15.0);
        URLS.setPrefSize(260.0, 15.0);

        filenameLabel.setLayoutX(22.0);
        filenameLabel.setLayoutY(50.0);
        filenameTextField.setLayoutX(80.0);
        filenameTextField.setLayoutY(50.0);
        filenameTextField.setPrefWidth(260.0);

        singerLabel.setLayoutX(22.0);
        singerLabel.setLayoutY(95.0);
        singerTextField.setLayoutX(80.0);
        singerTextField.setLayoutY(95.0);
        singerTextField.setPrefWidth(260.0);

        currBut.setLayoutX(210.0);
        currBut.setLayoutY(148.0);
        closeBut.setLayoutX(284.0);
        closeBut.setLayoutY(148.0);

        anchorPane.setPrefSize(380.0, 200.0);
        anchorPane.getChildren().addAll(URLLabel, URLS, filenameLabel, filenameTextField, singerLabel, singerTextField, currBut, closeBut);
        stage.setScene(scene);
    }

    public void sureDown(String url, String dir, String name, String aut, PlayList playList, String rootdir){
        currBut.setOnAction(event -> {

        });
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
