package com.stary.musicgo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class mainui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Controller controller = new Controller();

        //FXMLLoader fxmlLoader = new FXMLLoader(mainui.class.getResource("ui-view.fxml"));
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(738.0, 450.0);

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
        ImageView settingIm = new ImageView(new Image(getClass().getResourceAsStream("/img/setting.png")));
        settingIm.setFitHeight(15.0);
        settingIm.setFitWidth(15.0);
        settingButton.setGraphic(settingIm);
        settingButton.setLayoutX(14.0);
        settingButton.setLayoutY(14.0);
        settingButton.setPrefSize(25.0, 25.0);
        settingButton.setStyle("-fx-background-color: transparent;");
        settingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onclick_close();
            }
        });
        pane.getChildren().add(settingButton);
        Scene scene = new Scene(pane);
        stage.setTitle("a");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        //ser
        Button serButton = new Button();
        ImageView serIm = new ImageView(new Image(getClass().getResourceAsStream("/img/search.png")));
        serIm.setFitHeight(15.0);
        serIm.setFitWidth(15.0);
        serButton.setGraphic(serIm);
        serButton.setLayoutX(436.0);
        serButton.setLayoutY(14.0);
        serButton.setPrefSize(23.0, 23.0);

        TextField enterBox = new TextField();
        enterBox.setLayoutX(259.0);
        enterBox.setLayoutY(14.0);
        pane.getChildren().addAll(enterBox, serButton);


        //playButton
        HBox playBox = new HBox();
        playBox.setSpacing(20.0);
        playBox.setPrefSize(136.0, 68.0);
        playBox.setLayoutX(301.0);
        playBox.setLayoutY(382.0);

        Button lastButton = new Button();
        ImageView lastIm = new ImageView(new Image(getClass().getResourceAsStream("/img/Previous track.png")));
        lastIm.setFitHeight(15.0);
        lastIm.setFitWidth(15.0);
        lastButton.setGraphic(lastIm);
        lastButton.setPrefSize(23.0, 23.0);

        Button nextButton = new Button();
        ImageView nextIm = new ImageView(new Image(getClass().getResourceAsStream("/img/Next track.png")));
        nextIm.setFitHeight(15.0);
        nextIm.setFitWidth(15.0);
        nextButton.setGraphic(nextIm);
        nextButton.setPrefSize(23.0, 23.0);

        Button playButton = new Button();
        ImageView playIm = new ImageView(new Image(getClass().getResourceAsStream("/img/play.png")));
        playIm.setFitHeight(15.0);
        playIm.setFitWidth(15.0);
        playButton.setGraphic(playIm);
        playButton.setPrefSize(45.0, 39.0);

        playBox.getChildren().addAll(lastButton, playButton, nextButton);
        pane.getChildren().add(playBox);


        //controllerBox
        Button closeButton = new Button();
        closeButton.setPrefSize(25.0, 23.0);
        ImageView closeIm = new ImageView(new Image(getClass().getResourceAsStream("/img/close.png")));
        closeIm.setFitHeight(15.0);
        closeIm.setFitWidth(15.0);
        closeButton.setGraphic(closeIm);
        closeButton.setOnAction(e -> Platform.exit());

        Button minButton = new Button();
        ImageView minIm = new ImageView(new Image(getClass().getResourceAsStream("/img/minus.png")));
        minIm.setFitHeight(15.0);
        minIm.setFitWidth(15.0);
        minButton.setGraphic(minIm);
        minButton.setPrefSize(25.0, 23.0);
        minButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setIconified(true);
            }
        });

        HBox gencolHBox = new HBox();
        gencolHBox.setLayoutX(660.0);
        gencolHBox.setLayoutY(14.0);
        gencolHBox.setPrefSize(63.0, 23.0);
        gencolHBox.setSpacing(13.0);
        gencolHBox.getChildren().addAll(minButton, closeButton);
        pane.getChildren().add(gencolHBox);

    }


    public static void main(String[] args) {
        launch();
    }
}
