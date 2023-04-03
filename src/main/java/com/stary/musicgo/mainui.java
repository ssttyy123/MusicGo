package com.stary.musicgo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.awt.*;
import java.awt.MenuItem;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class mainui extends Application {
    private double _stg2mosx = 0.0;
    private double _stg2mosy = 0.0;
    private String rootdir;

    public boolean initexe(){
        //C:\ProgramData\MusicGo
        File dataDir = new File("C:\\ProgramData\\MusicGo");
        //D:/project/java/MusicGo/out/artifacts/MusicGo_jar/
        File roottxt = new File("C:\\ProgramData\\MusicGo\\resources\\init.txt");
        File mydir = new File("");
        if(!dataDir.exists()){
            if(!dataDir.mkdir()){
                return false;
            }
        }

        dataDir = new File("C:\\ProgramData\\MusicGo\\resources");
        if(!dataDir.exists()){
            if(!dataDir.mkdir()){
                return false;
            }
        }

        dataDir = new File("C:\\ProgramData\\MusicGo\\resources\\jsonF");
        if(!dataDir.exists()){
            if(!dataDir.mkdir()){
                return false;
            }
        }

        boolean rt = false;
        if(!roottxt.exists()){
            try{
                rt = roottxt.createNewFile();
                if(!rt){
                    System.out.println("创建初始化文件失败");
                    return false;
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(roottxt, false));
                String putintxt = mydir.getAbsolutePath().replace("\\", "/") + "/";
                bufferedWriter.write(putintxt);
                bufferedWriter.close();
            }
            catch (IOException e){
                System.out.println("创建初始化文件失败");
                return false;
            }
        }

        return true;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.initexe();
        Platform.setImplicitExit(false);
        try{
         rootdir = Files.readString(new File("C:/ProgramData/MusicGo/resources/init.txt").toPath());
         //D:/project/java/MusicGo/out/artifacts/MusicGo_jar/
        }
        catch (IOException e){
            throw new IOException("初始化根路径失败");
        }
        File mainUIcss = new File(rootdir + "css/mainUI.css");
        Controller controller = new Controller();
        HistroySave histroySave = new HistroySave(rootdir);


        //FXMLLoader fxmlLoader = new FXMLLoader(mainui.class.getResource("ui-view.fxml"));
        AnchorPane pane = new AnchorPane();
        pane.setId("mainPane");
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(mainUIcss.toURI().toString());
        Image paneImage = new Image(new File(histroySave.getSaveo().getBackgroundPath()).toURI().toString());
        BackgroundSize panesize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO,
                false,
                false,
                true,
                true);
        BackgroundImage backgroundImage = new BackgroundImage(paneImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                panesize);
        pane.setBackground(new Background(backgroundImage));


        //stage
        stage.setTitle("MusicGo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);


        //move pane
        AnchorPane movePane = new AnchorPane();
        movePane.setId("movePane");
        scene.setOnMousePressed(event -> {
            _stg2mosx = event.getScreenX() - stage.getX();
            _stg2mosy = event.getScreenY() - stage.getY();
        });
        movePane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - _stg2mosx);
            stage.setY(event.getScreenY() - _stg2mosy);
        });
        pane.getChildren().add(movePane);


        //player
        ArrayList<String> temp = new ArrayList<>(histroySave.getSaveo().getLocalPath());
        PlayList playList = new PlayList(new File(histroySave.getSaveo().getPlayingMusicUri()), temp);
        playList.init(temp);
        AudioPlayer audioPlayer = new AudioPlayer(new File(histroySave.getSaveo().getPlayingMusicUri()));
        playList.setFile(new File(histroySave.getSaveo().getPlayingMusicUri()));
        ObservableList<ListFileCell> localFiles = FXCollections.observableArrayList(playList.getFileList());

        pane.getChildren().addAll(audioPlayer.getStartLabel(), audioPlayer.getPlaySlider(), audioPlayer.getEndLabel());


        //playButton
        Button playButton = new Button();
        playButton.setId("playButton");
        playButton.setLayoutX(475);
        playButton.setLayoutY(525);
        ImageView playIm = new ImageView(new Image(new FileInputStream(rootdir + "img/play.png")));
        playIm.setFitHeight(15.0);
        playIm.setFitWidth(15.0);
        playButton.setGraphic(playIm);
        playButton.setOnAction(actionEvent -> {
            try {
                controller.onclick_play(audioPlayer, playButton, rootdir);
                audioPlayer.setPlayForm(playList, controller, playButton, rootdir, audioPlayer);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button lastButton = new Button();
        lastButton.setId("lastButton");
        lastButton.setLayoutX(410);
        lastButton.setLayoutY(530);
        ImageView lastIm = new ImageView(new Image(new FileInputStream(rootdir + "img/Previous track.png")));
        lastIm.setFitHeight(15.0);
        lastIm.setFitWidth(15.0);
        lastButton.setGraphic(lastIm);
        lastButton.setOnAction(actionEvent -> {
            audioPlayer.changeAudioRes(playList.getLastSong());
            try {
                controller.onclick_other_play(audioPlayer, playButton, rootdir);
                audioPlayer.setPlayForm(playList, controller, playButton, rootdir, audioPlayer);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button nextButton = new Button();
        nextButton.setId("nextButton");
        nextButton.setLayoutX(550);
        nextButton.setLayoutY(530);
        ImageView nextIm = new ImageView(new Image(new FileInputStream(rootdir + "img/Next track.png")));
        nextIm.setFitHeight(15.0);
        nextIm.setFitWidth(15.0);
        nextButton.setGraphic(nextIm);
        nextButton.setOnAction(actionEvent ->{
            audioPlayer.changeAudioRes(playList.getNextSong());
            try {
                controller.onclick_other_play(audioPlayer, playButton, rootdir);
                audioPlayer.setPlayForm(playList, controller, playButton, rootdir, audioPlayer);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        pane.getChildren().addAll(lastButton, playButton, nextButton);


        //listarea
        SplitPane listArea = new SplitPane();
        listArea.setDividerPosition(0, 0.35);
        listArea.setId("listArea");
        listArea.setLayoutX(0);
        listArea.setLayoutY(70);

        TableView<ListFileCell> localtable = new TableView<>(localFiles){
            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                ScrollBar scrollBar = (ScrollBar) queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);
                if (scrollBar != null && scrollBar.isVisible()) {
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                    scrollBar.setOpacity(1);
                    scrollBar.setVisible(false);
                }
            }
        };
        TableView<ListFileCell> webtable = new TableView<>(){
            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                ScrollBar scrollBar = (ScrollBar) queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);
                if (scrollBar != null && scrollBar.isVisible()) {
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                    scrollBar.setOpacity(1);
                    scrollBar.setVisible(false);
                }
            }
        };
        localtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        webtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DownUI downUI = new DownUI();

        TableColumn<ListFileCell, String> tclo_name = new TableColumn<>("歌曲名");
        tclo_name.setCellValueFactory(param -> param.getValue().getNameProperty());

        TableColumn<ListFileCell, String> tclo_aut = new TableColumn<>("演唱者");
        tclo_aut.setCellValueFactory(param -> param.getValue().getAutProperty());

        TableColumn<ListFileCell, String> tclo_but = new TableColumn<>("play");
        tclo_but.setCellValueFactory(param -> param.getValue().getUriProperty());
        tclo_but.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ListFileCell, String> call(TableColumn<ListFileCell, String> param) {
                return new TableCell<>() {

                    final HBox hBox = new HBox();
                    final Button playbutton = new Button("p");
                    final Button delbutton = new Button("del");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null && !empty) {
                            hBox.setSpacing(10);
                            hBox.setStyle("-fx-background-color: transparent");
                            playbutton.setOnAction(event -> {
                                audioPlayer.changeAudioRes(playList.setFile(new File(item)));
                                try {
                                    controller.onclick_other_play(audioPlayer, playButton, rootdir);
                                    audioPlayer.setPlayForm(playList, controller, playButton, rootdir, audioPlayer);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            delbutton.setOnAction(event -> controller.onclick_del(item, audioPlayer, playList, localtable));
                            hBox.getChildren().clear();
                            hBox.getChildren().addAll(playbutton, delbutton);
                            this.setGraphic(hBox);
                        } else {
                            hBox.getChildren().clear();
                        }
                    }
                };
            }

        });

        localtable.getColumns().add(tclo_name);
        localtable.getColumns().add(tclo_aut);
        localtable.getColumns().add(tclo_but);

        TableColumn<ListFileCell, String> tcwb_name = new TableColumn<>("Songname");
        tcwb_name.setCellValueFactory(param -> param.getValue().getNameProperty());

        TableColumn<ListFileCell, String> tcwb_aut = new TableColumn<>("aut");
        tcwb_aut.setCellValueFactory(param -> param.getValue().getAutProperty());

        TableColumn<ListFileCell, String> tcwb_form = new TableColumn<>("form");
        tcwb_form.setCellValueFactory(param -> param.getValue().getFormProperty());

        TableColumn<ListFileCell, String> tcwb_uri = new TableColumn<>();
        tcwb_uri.setCellValueFactory(param -> param.getValue().getNameProperty());
        tcwb_uri.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ListFileCell, String> call(TableColumn<ListFileCell, String> param) {
                return new TableCell<>() {
                    final Button button = new Button("d");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null && !empty) {
                            this.setGraphic(button);
                            button.setOnAction(event -> controller.onclick_down(param.getTableView().getItems().get(this.getIndex()).getUri(),
                                    histroySave.getSaveo().getDownerDir(),
                                    item, param.getTableView().getItems().get(this.getIndex()).getAut(),
                                    playList, localtable, rootdir, downUI, param.getTableView().getItems().get(this.getIndex()).getForm()));
                        }

                    }
                };
            }
        });
        webtable.getColumns().add(tcwb_name);
        webtable.getColumns().add(tcwb_aut);
        webtable.getColumns().add(tcwb_form);
        webtable.getColumns().add(tcwb_uri);


        StackPane locallist = new StackPane();
        StackPane weblist = new StackPane();
        locallist.setStyle("-fx-background-color: transparent");
        weblist.setStyle("-fx-background-color: transparent");

        locallist.getChildren().add(localtable);
        weblist.getChildren().add(webtable);
        listArea.getItems().addAll(locallist, weblist);
        localtable.prefWidthProperty().bind(locallist.widthProperty());
        webtable.prefWidthProperty().bind(weblist.widthProperty());
        pane.getChildren().addAll(listArea);

        //settingbutton
        SettingStage settingStage = new SettingStage();
        settingStage.init(histroySave, playList, localtable, pane, rootdir);
        Button settingButton = new Button();
        settingButton.setId("settingButton");
        settingButton.setLayoutX(760);
        settingButton.setLayoutY(17.5);
        ImageView settingIm = new ImageView(new Image(new FileInputStream(rootdir + "img/setting.png")));
        settingIm.setFitHeight(15.0);
        settingIm.setFitWidth(15.0);
        settingButton.setGraphic(settingIm);
        settingButton.setOnAction(actionEvent -> controller.onclick_setting(settingStage));
        pane.getChildren().add(settingButton);


        //ser
        TextField enterBox = new TextField();
        enterBox.setId("enterBox");
        enterBox.setLayoutX(265);
        enterBox.setLayoutY(20);

        Button serButton = new Button();
        serButton.setId("serButton");
        serButton.setLayoutX(430);
        serButton.setLayoutY(17.5);
        ImageView serIm = new ImageView(new Image(new FileInputStream(rootdir + "img/search.png")));
        serIm.setFitHeight(15.0);
        serIm.setFitWidth(15.0);
        serButton.setGraphic(serIm);
        serButton.setOnAction(actionEvent -> controller.onclick_search(enterBox, webtable, rootdir, histroySave));

        pane.getChildren().addAll(enterBox, serButton);


        //tray
        SystemTray systemTray = SystemTray.getSystemTray();
        java.awt.Image image = Toolkit.getDefaultToolkit().getImage(rootdir + "img/trayicon.png");
        PopupMenu popupMenu = new PopupMenu();
        MenuItem openItem = new MenuItem("open");
        MenuItem closeItem = new MenuItem("close");
        popupMenu.add(openItem);
        popupMenu.add(closeItem);
        TrayIcon trayIcon = new TrayIcon(image, "MusicGo", popupMenu);
        try{
            systemTray.add(trayIcon);
        }
        catch (AWTException e){
            System.out.println("无法生成系统托盘");
            histroySave.getSaveo().setCloseForm(1);
            histroySave.reflushJson();
        }
        openItem.addActionListener(e -> Platform.runLater(stage::show));
        closeItem.addActionListener(e -> {
            histroySave.getSaveo().setPlayingMusicUri(playList.getcurrMusic());
            histroySave.reflushJson();
            systemTray.remove(trayIcon);
            Platform.exit();
        });


        //controllerBox
        Button closeButton = new Button();
        closeButton.setId("closeButton");
        closeButton.setLayoutX(950);
        closeButton.setLayoutY(15);
        ImageView closeIm = new ImageView(new Image(new FileInputStream(rootdir + "img/close.png")));
        closeIm.setFitHeight(15.0);
        closeIm.setFitWidth(15.0);
        closeButton.setGraphic(closeIm);
        closeButton.setOnAction(e -> {
            if(histroySave.getSaveo().getCloseForm() == 0){
                stage.hide();
            }
            else {
                histroySave.getSaveo().setPlayingMusicUri(playList.getcurrMusic());
                histroySave.reflushJson();
                systemTray.remove(trayIcon);
                Platform.exit();
            }
        });

        Button minButton = new Button();
        minButton.setId("minButton");
        minButton.setLayoutX(895);
        minButton.setLayoutY(15);
        ImageView minIm = new ImageView(new Image(new FileInputStream(rootdir + "img/minus.png")));
        minIm.setFitHeight(15.0);
        minIm.setFitWidth(15.0);
        minButton.setGraphic(minIm);
        minButton.setOnAction(actionEvent -> stage.setIconified(true));

        pane.getChildren().addAll(minButton, closeButton);

        stage.show();

    }


    public static void main(String[] args){
        launch();
    }
}
