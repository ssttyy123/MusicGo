package com.stary.musicgo;

import com.mpatric.mp3agic.InvalidDataException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.LongAccumulator;

public class mainui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Controller controller = new Controller();
        HistroySave histroySave = new HistroySave();


        //FXMLLoader fxmlLoader = new FXMLLoader(mainui.class.getResource("ui-view.fxml"));
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(738.0, 450.0);


        //player
        PlayList playList = new PlayList(new File(histroySave.getHistroyMusic()), histroySave.getHistroyLoaddir());
        playList.init(histroySave.getHistroyLoaddir());
        AudioPlayer audioPlayer = new AudioPlayer(new File(histroySave.getHistroyMusic()));
        ObservableList<LocalFile> localFiles = FXCollections.observableArrayList(playList.getFileList());
        StringBuilder wholeTime;

        //playButton
        HBox playBox = new HBox();
        playBox.setSpacing(20.0);
        playBox.setPrefSize(136.0, 68.0);
        playBox.setLayoutX(301.0);
        playBox.setLayoutY(382.0);

        Button playButton = new Button();
        ImageView playIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/play.png"))));
        playIm.setFitHeight(15.0);
        playIm.setFitWidth(15.0);
        playButton.setGraphic(playIm);
        playButton.setPrefSize(45.0, 39.0);
        playButton.setOnAction(actionEvent -> {
            controller.onclick_play(audioPlayer, playButton);
        });

        Button lastButton = new Button();
        ImageView lastIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Previous track.png"))));
        lastIm.setFitHeight(15.0);
        lastIm.setFitWidth(15.0);
        lastButton.setGraphic(lastIm);
        lastButton.setPrefSize(23.0, 23.0);
        lastButton.setOnAction(actionEvent -> {
            audioPlayer.changeAudioRes(playList.getLastSong());
            controller.onclick_other_play(audioPlayer, playButton);
        });

        Button nextButton = new Button();
        ImageView nextIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Next track.png"))));
        nextIm.setFitHeight(15.0);
        nextIm.setFitWidth(15.0);
        nextButton.setGraphic(nextIm);
        nextButton.setPrefSize(23.0, 23.0);
        nextButton.setOnAction(actionEvent ->{
            audioPlayer.changeAudioRes(playList.getNextSong());
            controller.onclick_other_play(audioPlayer, playButton);
        });


        playBox.getChildren().addAll(lastButton, playButton, nextButton);
        pane.getChildren().add(playBox);




        //listarea
        SplitPane listArea = new SplitPane();
        listArea.setDividerPosition(0, 0.35);
        listArea.setLayoutY(50.0);
        listArea.setPrefSize(738.0, 324.0);

        TableView localtable = new TableView(localFiles);
        TableView webtable = new TableView();
        localtable.setPrefSize(254.0, 324.0);
        webtable.setPrefSize(254.0, 324.0);

        TableColumn<LocalFile, String> tc_name = new TableColumn<LocalFile, String>("歌曲名");
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LocalFile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LocalFile, String> param) {
                return param.getValue().getNameProperty();
            }
        });

        TableColumn<LocalFile, String> tc_aut = new TableColumn<LocalFile, String>("演唱者");
        tc_aut.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LocalFile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LocalFile, String> param) {
                return param.getValue().getAutProperty();
            }
        });

        TableColumn<LocalFile, String> tc_but = new TableColumn<LocalFile, String>("play");
        tc_but.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LocalFile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LocalFile, String> param) {
                return param.getValue().getUriProperty();
            }
        });
        tc_but.setCellFactory(new Callback<TableColumn<LocalFile, String>, TableCell<LocalFile, String>>() {
            @Override
            public TableCell<LocalFile, String> call(TableColumn<LocalFile, String> param) {
                TableCell<LocalFile, String> cell = new TableCell<LocalFile, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item != null && !empty){
                            Button button = new Button("p");
                            this.setGraphic(button);
                            button.setOnAction(event -> {
                                audioPlayer.changeAudioRes(playList.setFile(new File(item)));
                                controller.onclick_other_play(audioPlayer, playButton);
                            });
                        }

                    }
                };
                return cell;
            }
        });

        localtable.getColumns().add(tc_name);
        localtable.getColumns().add(tc_aut);
        localtable.getColumns().add(tc_but);

        AnchorPane locallist = new AnchorPane();
        AnchorPane weblist = new AnchorPane();
        locallist.setPrefSize(100.0, 160.0);
        weblist.setPrefSize(100.0, 160.0);

        locallist.getChildren().add(localtable);
        weblist.getChildren().add(webtable);
        listArea.getItems().addAll(locallist, weblist);
        pane.getChildren().addAll(listArea);

        //settingbutton
        Button settingButton = new Button();
        ImageView settingIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/setting.png"))));
        settingIm.setFitHeight(15.0);
        settingIm.setFitWidth(15.0);
        settingButton.setGraphic(settingIm);
        settingButton.setLayoutX(14.0);
        settingButton.setLayoutY(14.0);
        settingButton.setPrefSize(25.0, 25.0);
        settingButton.setStyle("-fx-background-color: transparent;");
        settingButton.setOnAction(actionEvent -> controller.onclick_setting());
        pane.getChildren().add(settingButton);


        //ser
        TextField enterBox = new TextField();
        enterBox.setLayoutX(259.0);
        enterBox.setLayoutY(14.0);

        Button serButton = new Button();
        ImageView serIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/search.png"))));
        serIm.setFitHeight(15.0);
        serIm.setFitWidth(15.0);
        serButton.setGraphic(serIm);
        serButton.setLayoutX(436.0);
        serButton.setLayoutY(14.0);
        serButton.setPrefSize(23.0, 23.0);
        serButton.setOnAction(actionEvent -> {
            controller.onclick_search(enterBox);
        });

        pane.getChildren().addAll(enterBox, serButton);


        //controllerBox
        Button closeButton = new Button();
        closeButton.setPrefSize(25.0, 23.0);
        ImageView closeIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/close.png"))));
        closeIm.setFitHeight(15.0);
        closeIm.setFitWidth(15.0);
        closeButton.setGraphic(closeIm);
        closeButton.setOnAction(e -> {
            histroySave.setHistroyLoaddir(playList.getDirURI());
            histroySave.setHistroyMusic(playList.getcurrMusic());
            try {
                histroySave.reflushJson();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Platform.exit();
        });

        Button minButton = new Button();
        ImageView minIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/minus.png"))));
        minIm.setFitHeight(15.0);
        minIm.setFitWidth(15.0);
        minButton.setGraphic(minIm);
        minButton.setPrefSize(25.0, 23.0);
        minButton.setOnAction(actionEvent -> stage.setIconified(true));

        HBox gencolHBox = new HBox();
        gencolHBox.setLayoutX(660.0);
        gencolHBox.setLayoutY(14.0);
        gencolHBox.setPrefSize(63.0, 23.0);
        gencolHBox.setSpacing(13.0);
        gencolHBox.getChildren().addAll(minButton, closeButton);
        pane.getChildren().add(gencolHBox);


        //stage
        Scene scene = new Scene(pane);
        stage.setTitle("a");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }


    public static void main(String[] args){
        launch();
    }
}
