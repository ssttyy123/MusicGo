package com.stary.musicgo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URI;
import java.util.Objects;

public class mainui extends Application {
    @Override
    public void start(Stage stage){
        Controller controller = new Controller();


        //FXMLLoader fxmlLoader = new FXMLLoader(mainui.class.getResource("ui-view.fxml"));
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(738.0, 450.0);

        //player
        File medfile = new File("src/main/resources/music/11582.mp3");
        URI uri = medfile.toURI();
        Media media = new Media(uri.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView playerbox = new MediaView(mediaPlayer);
        playerbox.setFitHeight(30.0);
        playerbox.setFitWidth(30.0);
        playerbox.setLayoutX(462.0);
        playerbox.setLayoutY(386.0);
        pane.getChildren().add(playerbox);

        //textarea
        WebView localLiset = new WebView();
        localLiset.setLayoutY(58.0);
        localLiset.setPrefSize(215.0, 316.0);
        WebView webList = new WebView();
        webList.setLayoutX(215.0);
        webList.setLayoutY(58.0);
        webList.setPrefSize(523.0, 316.0);
        pane.getChildren().addAll(localLiset, webList);

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


        //playButton
        HBox playBox = new HBox();
        playBox.setSpacing(20.0);
        playBox.setPrefSize(136.0, 68.0);
        playBox.setLayoutX(301.0);
        playBox.setLayoutY(382.0);

        Button lastButton = new Button();
        ImageView lastIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Previous track.png"))));
        lastIm.setFitHeight(15.0);
        lastIm.setFitWidth(15.0);
        lastButton.setGraphic(lastIm);
        lastButton.setPrefSize(23.0, 23.0);

        Button nextButton = new Button();
        ImageView nextIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Next track.png"))));
        nextIm.setFitHeight(15.0);
        nextIm.setFitWidth(15.0);
        nextButton.setGraphic(nextIm);
        nextButton.setPrefSize(23.0, 23.0);

        Button playButton = new Button();
        ImageView playIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/play.png"))));
        playIm.setFitHeight(15.0);
        playIm.setFitWidth(15.0);
        playButton.setGraphic(playIm);
        playButton.setPrefSize(45.0, 39.0);
        playButton.setOnAction(actionEvent -> {
            mediaPlayer.play();
        });

        playBox.getChildren().addAll(lastButton, playButton, nextButton);
        pane.getChildren().add(playBox);


        //controllerBox
        Button closeButton = new Button();
        closeButton.setPrefSize(25.0, 23.0);
        ImageView closeIm = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/close.png"))));
        closeIm.setFitHeight(15.0);
        closeIm.setFitWidth(15.0);
        closeButton.setGraphic(closeIm);
        closeButton.setOnAction(e -> Platform.exit());

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
