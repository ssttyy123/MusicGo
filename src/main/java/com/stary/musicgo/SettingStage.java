package com.stary.musicgo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SettingStage {
    private final TabPane tabPane = new TabPane();
    private final Scene scene = new Scene(tabPane);
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final FileChooser fileChooser = new FileChooser();

    //常规
    private final Tab commontTab = new Tab("常规");
    private final AnchorPane commontPane = new AnchorPane();

    private final Label localLabel = new Label("本地储存：");
    private final ListView<String> localPathList = new ListView<>();
    private final ObservableList<String> observableList = FXCollections.observableArrayList("D:/MusicGodown");
    private final Button localBut = new Button();

    private final Label closeLabel = new Label("关闭主面板：");
    private final ToggleGroup closeGroup = new ToggleGroup();
    private final RadioButton closeBut_tohide = new RadioButton("最小化到系统托盘");
    private final RadioButton closeBut_close = new RadioButton("退出MusicGo");

    //下载
    private final Tab downTab = new Tab("下载");
    private final AnchorPane downPane = new AnchorPane();

    private final Label downLabel = new Label("下载位置");
    private final Label downPathLabel = new Label("downPath");
    private final Button downBut = new Button();

    private final Label nameFormLabel = new Label("下载命名格式");
    private final ToggleGroup nameFormGroup = new ToggleGroup();
    private final RadioButton nameForm_singer2song = new RadioButton("歌手-歌曲名");
    private final RadioButton nameForm_song2singer = new RadioButton("歌曲名-歌手");
    private final RadioButton nameForm_song = new RadioButton("歌曲名");


    //搜索
    private final Tab searchTab = new Tab("搜索");
    private final AnchorPane searchPane = new AnchorPane();

    private final Label keyMajusculeLabel = new Label("关键字大小写敏感");
    private final CheckBox keyMajusculeCheckBox = new CheckBox("区分大小写");

    private final Label searchReLabel = new Label("搜索方式");
    private final ToggleGroup searchhReGroup = new ToggleGroup();
    private final RadioButton searchRe_complete = new RadioButton("完全匹配");
    private final RadioButton searchRe_part = new RadioButton("部分匹配");
    private final RadioButton searchRe_generic = new RadioButton("泛型匹配");

    //其他
    private final Tab otherTab = new Tab("其他");
    private final AnchorPane otherPane = new AnchorPane();

    private final Label backgroundLabel = new Label("背景设置");
    private final Label backgroundPath = new Label("background");
    private final Button backgroundBut = new Button();

    private final Label aboutLabel = new Label("关于作者");
    private final Label UIdesigner = new Label("UIdesigner");
    private final Label APPdesigner = new Label("APPdesigner");

    private final Stage stage = new Stage();


    public void init(HistroySave histroySave, PlayList playList, TableView<ListFileCell> tableView){
        //常规
        localLabel.setLayoutX(22.0);
        localLabel.setLayoutY(15.0);
        localPathList.setLayoutX(29.0);
        localPathList.setLayoutY(35.0);
        localPathList.setPrefSize(233.0, 25.0);
        observableList.setAll(histroySave.getSaveo().getLocalPath());
        localPathList.setItems(observableList);
        localBut.setLayoutX(272.0);
        localBut.setLayoutY(35.0);
        localBut.setPrefSize(25.0, 25.0);
        localBut.setOnAction(event -> {
            directoryChooser.setTitle("请选择本地文件夹");
            histroySave.getSaveo().addLocalPath(directoryChooser.showDialog(stage).getAbsolutePath());
            histroySave.reflushJson();
            playList.init(new ArrayList<>(histroySave.getSaveo().getLocalPath()));
            observableList.setAll(histroySave.getSaveo().getLocalPath());
            tableView.setItems(FXCollections.observableArrayList(playList.getFileList()));
        });

        closeLabel.setLayoutX(22.0);
        closeLabel.setLayoutY(70.0);
        closeBut_tohide.setLayoutX(29.0);
        closeBut_tohide.setLayoutY(90.0);
        closeBut_tohide.setToggleGroup(closeGroup);
        closeBut_close.setLayoutX(29.0);
        closeBut_close.setLayoutY(110.0);
        closeBut_close.setToggleGroup(closeGroup);
        //  0/1   -   最小化到系统托盘/退出MusicGo
        if(histroySave.getSaveo().getCloseForm() == 0){
            closeBut_tohide.setSelected(true);
        } else if (histroySave.getSaveo().getCloseForm() == 1) {
            closeBut_close.setSelected(true);
        }
        closeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton r = (RadioButton)newValue;
            if(r.getText().equals("最小化到系统托盘")){
                histroySave.getSaveo().setCloseForm(0);
            } else if (r.getText().equals("退出MusicGo")) {
                histroySave.getSaveo().setCloseForm(1);
            }
            histroySave.reflushJson();
        });

        commontPane.setPrefSize(200.0, 180.0);
        commontPane.getChildren().addAll(localLabel, localPathList, localBut, closeLabel, closeBut_tohide, closeBut_close);
        commontTab.setContent(commontPane);

        //下载
        downLabel.setLayoutX(22.0);
        downLabel.setLayoutY(15.0);
        downPathLabel.setPrefSize(233.0, 25.0);
        downPathLabel.setLayoutX(29.0);
        downPathLabel.setLayoutY(35.0);
        downPathLabel.setText(histroySave.getSaveo().getDownerDir());
        downBut.setPrefSize(25.0, 25.0);
        downBut.setLayoutX(272.0);
        downBut.setLayoutY(35.0);
        downBut.setOnAction(event -> {
            directoryChooser.setTitle("请选择下载文件夹");
            histroySave.getSaveo().setDownerDir(directoryChooser.showDialog(stage).getAbsolutePath());
            histroySave.reflushJson();
            downPathLabel.setText(histroySave.getSaveo().getDownerDir());
        });

        nameFormLabel.setLayoutX(22.0);
        nameFormLabel.setLayoutY(70.0);
        nameForm_singer2song.setLayoutX(29.0);
        nameForm_singer2song.setLayoutY(90.0);
        nameForm_singer2song.setToggleGroup(nameFormGroup);
        nameForm_song2singer.setLayoutX(29.0);
        nameForm_song2singer.setLayoutY(110.0);
        nameForm_song2singer.setToggleGroup(nameFormGroup);
        nameForm_song.setLayoutX(29.0);
        nameForm_song.setLayoutY(130.0);
        nameForm_song.setToggleGroup(nameFormGroup);
        //歌手-歌曲名/歌曲名-歌手/歌曲名 -- 0/1/2
        nameFormGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton r = (RadioButton)newValue;
            if(r.getText().equals("歌手-歌曲名")){
                histroySave.getSaveo().setNameForm(0);
            } else if (r.getText().equals("歌曲名-歌手")) {
                histroySave.getSaveo().setNameForm(1);
            } else if (r.getText().equals("歌曲名")) {
                histroySave.getSaveo().setNameForm(2);
            }
            histroySave.reflushJson();
        });

        downPane.setPrefSize(200.0, 180.0);
        downPane.getChildren().addAll(downLabel, downPathLabel, downBut, nameFormLabel, nameForm_singer2song, nameForm_song2singer, nameForm_song);
        downTab.setContent(downPane);

        //搜索
        keyMajusculeLabel.setLayoutX(22.0);
        keyMajusculeLabel.setLayoutY(15.0);
        keyMajusculeCheckBox.setLayoutX(29.0);
        keyMajusculeCheckBox.setLayoutY(35.0);
        //区分大小写

        searchReLabel.setLayoutX(22.0);
        searchReLabel.setLayoutY(70.0);
        searchRe_complete.setLayoutX(29.0);
        searchRe_complete.setLayoutY(90.0);
        searchRe_complete.setToggleGroup(searchhReGroup);
        searchRe_part.setLayoutX(29.0);
        searchRe_part.setLayoutY(110.0);
        searchRe_part.setToggleGroup(searchhReGroup);
        searchRe_generic.setLayoutX(29.0);
        searchRe_generic.setLayoutY(130.0);
        searchRe_generic.setToggleGroup(searchhReGroup);

        searchPane.setPrefSize(220.0, 180.0);
        searchPane.getChildren().addAll(keyMajusculeLabel, keyMajusculeCheckBox, searchReLabel, searchRe_complete, searchRe_part, searchRe_generic);
        searchTab.setContent(searchPane);

        //其他
        backgroundLabel.setLayoutX(22.0);
        backgroundLabel.setLayoutY(15.0);
        backgroundPath.setPrefSize(233.0, 25.0);
        backgroundPath.setLayoutX(29.0);
        backgroundPath.setLayoutY(35.0);
        backgroundPath.setText(histroySave.getSaveo().getBackgroundPath());
        backgroundBut.setPrefSize(25.0, 25.0);
        backgroundBut.setLayoutX(272.0);
        backgroundBut.setLayoutY(35.0);
        backgroundBut.setOnAction(event -> {
            fileChooser.setTitle("请选择背景");
            histroySave.getSaveo().setBackgroundPath(fileChooser.showOpenDialog(stage).getAbsolutePath());
            histroySave.reflushJson();
            backgroundPath.setText(histroySave.getSaveo().getBackgroundPath());
        });

        aboutLabel.setLayoutX(22.0);
        aboutLabel.setLayoutY(70.0);
        UIdesigner.setPrefSize(233.0, 25.0);
        UIdesigner.setLayoutX(29.0);
        UIdesigner.setLayoutY(90.0);
        APPdesigner.setPrefSize(233.0, 25.0);
        APPdesigner.setLayoutX(29.0);
        APPdesigner.setLayoutY(110.0);

        otherPane.setPrefSize(200.0, 180.0);
        otherPane.getChildren().addAll(backgroundLabel, backgroundPath, backgroundBut, aboutLabel, UIdesigner, APPdesigner);
        otherTab.setContent(otherPane);


        tabPane.setPrefSize(600.0, 400.0);
        tabPane.getTabs().addAll(commontTab, downTab, searchTab, otherTab);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }
}
