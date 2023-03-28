package com.stary.musicgo;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingStage {
    private final TabPane tabPane = new TabPane();

    //常规
    private final Tab commontTab = new Tab("常规");
    private final AnchorPane commontPane = new AnchorPane();

    private final Label localLabel = new Label("本地储存：");
    private final Label localPathLabel = new Label("localPath");
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
    private final Tab searchTab = new Tab();
    private final AnchorPane searchPane = new AnchorPane();

    private final Label keyMajusculeLabel = new Label("关键字大小写敏感");
    private final CheckBox keyMajusculeCheckBox = new CheckBox("区分大小写");

    private final Label searchReLabel = new Label("搜索方式");
    private final ToggleGroup searchhReGroup = new ToggleGroup();
    private final RadioButton searchRe_complete = new RadioButton("完全匹配");
    private final RadioButton searchRe_part = new RadioButton("部分匹配");
    private final RadioButton searchRe_generic = new RadioButton("泛型匹配");

    //其他
    private final Tab otherTab = new Tab();
    private final AnchorPane otherPane = new AnchorPane();

    private final Label backgroundLabel = new Label("背景设置");
    private final Label backgroundPath = new Label("background");
    private final Button backgroundBut = new Button();

    private final Label aboutLabel = new Label("关于作者");
    private final Label UIdesigner = new Label("UIdesigner");
    private final Label APPdesigner = new Label("APPdesigner");

    private final Stage stage = new Stage();


    public void init(){
        //常规
        localLabel.setLayoutX(22.0);
        localLabel.setLayoutY(15.0);
        localPathLabel.setLayoutX(29.0);
        localPathLabel.setLayoutY(35.0);
        localPathLabel.setPrefSize(233.0, 25.0);
        localBut.setLayoutX(272.0);
        localBut.setLayoutY(35.0);
        localBut.setPrefSize(25.0, 25.0);

        closeLabel.setLayoutX(22.0);
        closeLabel.setLayoutY(70.0);
        closeBut_tohide.setLayoutX(29.0);
        closeBut_tohide.setLayoutY(90.0);
        closeBut_tohide.setToggleGroup(closeGroup);
        closeBut_close.setLayoutX(29.0);
        closeBut_close.setLayoutY(110.0);
        closeBut_close.setToggleGroup(closeGroup);

        commontPane.setPrefSize(200.0, 180.0);
        commontPane.getChildren().addAll(localLabel, localPathLabel, localBut, closeLabel, closeBut_tohide, closeBut_close);
        commontTab.setGraphic(commontPane);

        //下载
        downLabel.setLayoutX(22.0);
        downLabel.setLayoutY(15.0);
        downPathLabel.setPrefSize(233.0, 25.0);
        downPathLabel.setLayoutX(29.0);
        downPathLabel.setLayoutY(35.0);
        downBut.setPrefSize(25.0, 25.0);
        downBut.setLayoutX(272.0);
        downBut.setLayoutY(35.0);

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

        downPane.setPrefSize(200.0, 180.0);
        downPane.getChildren().addAll(downLabel, downPathLabel, downBut, nameFormLabel, nameForm_singer2song, nameForm_song2singer, nameForm_song);
        downTab.setGraphic(downPane);

        //搜索
        keyMajusculeLabel.setLayoutX(22.0);
        keyMajusculeLabel.setLayoutY(15.0);
        keyMajusculeCheckBox.setLayoutX(29.0);
        keyMajusculeCheckBox.setLayoutY(35.0);

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
        searchTab.setGraphic(searchPane);

        //其他
        backgroundLabel.setLayoutX(22.0);
        backgroundLabel.setLayoutY(15.0);
        backgroundPath.setPrefSize(233.0, 25.0);
        backgroundPath.setLayoutX(29.0);
        backgroundPath.setLayoutY(35.0);
        backgroundBut.setPrefSize(25.0, 25.0);
        backgroundBut.setLayoutX(272.0);
        backgroundBut.setLayoutY(35.0);

        aboutLabel.setLayoutX(22.0);
        aboutLabel.setLayoutY(15.0);
        UIdesigner.setPrefSize(233.0, 25.0);
        UIdesigner.setLayoutX(29.0);
        UIdesigner.setLayoutY(90.0);
        APPdesigner.setPrefSize(233.0, 25.0);
        APPdesigner.setLayoutX(29.0);
        APPdesigner.setLayoutY(110.0);

        otherPane.setPrefSize(200.0, 180.0);
        otherPane.getChildren().addAll(backgroundLabel, backgroundPath, backgroundBut, aboutLabel, UIdesigner, APPdesigner);
        otherTab.setGraphic(otherPane);
    }

    public Stage getStage() {
        return stage;
    }
}
