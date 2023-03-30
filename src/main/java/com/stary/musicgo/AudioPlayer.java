package com.stary.musicgo;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.*;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class AudioPlayer {
    private boolean mouseOn = false;
    private Media media;
    private MediaPlayer mediaPlayer;
    private double wholeTime;
    private final Label startLabel = new Label();
    private final Label endLabel = new Label();
    private final Slider playSlider = new Slider();

    public AudioPlayer(File defaultUri){
        media = new Media(defaultUri.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        startLabel.setLayoutX(30.0);
        startLabel.setLayoutY(380.0);
        endLabel.setLayoutX(680.0);
        endLabel.setLayoutY(380.0);
        playSlider.setPrefSize(580.0, 14.000);
        playSlider.setLayoutX(80.0);
        playSlider.setLayoutY(380.0);

        playSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOn = true;
            }
        });
        playSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(playSlider.getValue()));
                mouseOn = false;
            }
        });
    }

    public void setPlayForm(PlayList playList, Controller controller, Button playButton, String rootdir, AudioPlayer audioPlayer){
        mediaPlayer.setOnEndOfMedia(() -> {
            changeAudioRes(playList.getNextSong());
            try {
                controller.onclick_other_play(audioPlayer, playButton, rootdir);
                setPlayForm(playList, controller, playButton, rootdir, audioPlayer);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void changeAudioRes(File curi){
        destroyMedia();
        media = new Media(curi.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play(){
        mediaPlayer.play();
        mediaPlayer.setOnReady(() -> {
            playSlider.setValue(0);
            playSlider.setMin(0);
            playSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            wholeTime = mediaPlayer.getTotalDuration().toSeconds();
            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                if(!mouseOn){
                    playSlider.setValue(newValue.toSeconds());
                }
                startLabel.setText(formatTime(newValue.toSeconds()));
                endLabel.setText((formatTime(wholeTime - newValue.toSeconds())));
            });
        });
    }

    public void stop(){
        mediaPlayer.stop();
    }

    private void destroyMedia(){
        if(mediaPlayer.getStatus() == Status.PLAYING){
            mediaPlayer.stop();
        }
        mediaPlayer.dispose();
        media = null;
        System.gc();
    }

    public boolean isplay(){
        Status status = mediaPlayer.getStatus();
        if(status == Status.PLAYING) return true;
        else if (status == Status.PAUSED) {
            return false;
        }
        return false;
    }

    public String formatTime(double timeVal){
        if(timeVal < 60){
            if(timeVal < 10) return "00:0" + (int)timeVal;
            else return "00:" + (int)timeVal;
        }
        else {
            if((timeVal%60) < 10) return (int)(timeVal/60) + ":0" + (int)(timeVal%60);
            else return (int)(timeVal/60) + ":" + (int)(timeVal%60);
        }
    }

    public boolean isThisPlay(String uri){
        return Objects.equals(mediaPlayer.getMedia().getSource(), new File(uri).toURI().toString());
    }

    public Label getStartLabel(){
        return startLabel;
    }

    public Label getEndLabel(){
        return endLabel;
    }

    public Slider getPlaySlider(){
        return playSlider;
    }
}
