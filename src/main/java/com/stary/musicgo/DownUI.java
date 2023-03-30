package com.stary.musicgo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DownUI {
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

    public DownUI(){
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

    public void init(String url, String dir, String name, String aut, PlayList playList, String rootdir, Downer downer, TableView<ListFileCell> tableView){
        URLS.setText(url);
        filenameTextField.setText(name);
        singerTextField.setText(aut);
        currBut.setOnAction(event -> {
            stage.hide();
            new Thread(()->{
                downer.DownStart(url, dir, filenameTextField.getText(), singerTextField.getText(), playList, rootdir);
                tableView.setItems(FXCollections.observableArrayList(playList.getFileList()));
            }).start();
        });
        closeBut.setOnAction(event -> {
            stage.hide();
        });
    }

    public void show(){
        Platform.runLater(stage::show);
    }
}
