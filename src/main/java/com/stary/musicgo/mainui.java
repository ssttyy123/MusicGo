package com.stary.musicgo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class mainui extends Application {
    double _stg2mosx = 0.0;
    double _stg2mosy = 0.0;
    @Override
    public void start(Stage stage) throws IOException {
        Controller controller = new Controller();
        HistroySave histroySave = new HistroySave();


        //FXMLLoader fxmlLoader = new FXMLLoader(mainui.class.getResource("ui-view.fxml"));
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(738.0, 450.0);
        Scene scene = new Scene(pane);


        //stage
        stage.setTitle("MusicGo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);


        //move pane
        AnchorPane movePane = new AnchorPane();
        movePane.setPrefSize(738.0, 53.0);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _stg2mosx = event.getScreenX() - stage.getX();
                _stg2mosy = event.getScreenY() - stage.getY();
            }
        });
        movePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - _stg2mosx);
                stage.setY(event.getScreenY() - _stg2mosy);
            }
        });
        pane.getChildren().add(movePane);


        //player
        PlayList playList = new PlayList(new File(histroySave.getHistroyMusic()), histroySave.getHistroyLoaddir());
        playList.init(histroySave.getHistroyLoaddir());
        AudioPlayer audioPlayer = new AudioPlayer(new File(histroySave.getHistroyMusic()));
        playList.setFile(new File(histroySave.getHistroyMusic()));
        ObservableList<ListFileCell> localFiles = FXCollections.observableArrayList(playList.getFileList());

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

        TableView<ListFileCell> localtable = new TableView<ListFileCell>(localFiles);
        TableView<ListFileCell> webtable = new TableView<ListFileCell>();
        localtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        webtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ListFileCell, String> tclo_name = new TableColumn<ListFileCell, String>("歌曲名");
        tclo_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ListFileCell, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ListFileCell, String> param) {
                return param.getValue().getNameProperty();
            }
        });

        TableColumn<ListFileCell, String> tclo_aut = new TableColumn<ListFileCell, String>("演唱者");
        tclo_aut.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ListFileCell, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ListFileCell, String> param) {
                return param.getValue().getAutProperty();
            }
        });

        TableColumn<ListFileCell, String> tclo_but = new TableColumn<ListFileCell, String>("play");
        tclo_but.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ListFileCell, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ListFileCell, String> param) {
                return param.getValue().getUriProperty();
            }
        });
        tclo_but.setCellFactory(new Callback<TableColumn<ListFileCell, String>, TableCell<ListFileCell, String>>() {
            @Override
            public TableCell<ListFileCell, String> call(TableColumn<ListFileCell, String> param) {
                return new TableCell<ListFileCell, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item != null && !empty){
                            HBox hBox = new HBox();
                            hBox.setSpacing(10);
                            Button playbutton = new Button("p");
                            Button delbutton = new Button("del");
                            playbutton.setOnAction(event -> {
                                audioPlayer.changeAudioRes(playList.setFile(new File(item)));
                                controller.onclick_other_play(audioPlayer, playButton);
                            });
                            delbutton.setOnAction(event -> {
                                controller.onclick_del(item, audioPlayer, playList, localtable);
                            });

                            hBox.getChildren().addAll(playbutton, delbutton);
                            this.setGraphic(hBox);
                        }

                    }
                };
            }
        });
        localtable.getColumns().add(tclo_name);
        localtable.getColumns().add(tclo_aut);
        localtable.getColumns().add(tclo_but);

        TableColumn<ListFileCell, String> tcwb_name = new TableColumn<ListFileCell, String>("Songname");
        tcwb_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ListFileCell, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ListFileCell, String> param) {
                return param.getValue().getNameProperty();
            }
        });

        TableColumn<ListFileCell, String> tcwb_aut = new TableColumn<ListFileCell, String>("aut");
        tcwb_aut.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ListFileCell, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ListFileCell, String> param) {
                return param.getValue().getAutProperty();
            }
        });

        TableColumn<ListFileCell, String> tcwb_uri = new TableColumn<ListFileCell, String>();
        tcwb_uri.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ListFileCell, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ListFileCell, String> param) {
                return param.getValue().getNameProperty();
            }
        });
        tcwb_uri.setCellFactory(new Callback<TableColumn<ListFileCell, String>, TableCell<ListFileCell, String>>() {
            @Override
            public TableCell<ListFileCell, String> call(TableColumn<ListFileCell, String> param) {
                return new TableCell<ListFileCell, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item != null && !empty){
                            Button button = new Button("d");
                            this.setGraphic(button);
                            button.setOnAction(event -> {
                                System.out.println(param.getTableView().getItems().get(this.getIndex()).getUri());
                                //String url, String dir, String name, String aut, PlayList playList
                                controller.onclick_down(param.getTableView().getItems().get(this.getIndex()).getUri(), playList.getDirURI(), item, param.getTableView().getItems().get(this.getIndex()).getAut(), playList, localtable);

                            });
                        }

                    }
                };
            }
        });
        webtable.getColumns().add(tcwb_name);
        webtable.getColumns().add(tcwb_aut);
        webtable.getColumns().add(tcwb_uri);


        StackPane locallist = new StackPane();
        StackPane weblist = new StackPane();
        locallist.setPrefSize(100.0, 160.0);
        weblist.setPrefSize(100.0, 160.0);

        locallist.getChildren().add(localtable);
        weblist.getChildren().add(webtable);
        listArea.getItems().addAll(locallist, weblist);
        localtable.prefWidthProperty().bind(locallist.widthProperty());
        webtable.prefWidthProperty().bind(weblist.widthProperty());
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
            controller.onclick_search(enterBox, webtable);
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


        stage.show();

    }


    public static void main(String[] args){
        launch();
    }
}
