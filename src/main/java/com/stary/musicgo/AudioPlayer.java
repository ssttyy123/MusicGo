package com.stary.musicgo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer.*;

import java.io.File;
import java.util.Objects;

public class AudioPlayer {
    private Media media;
    private MediaPlayer mediaPlayer;
    private Duration wholeTime = null;

    public AudioPlayer(File defaultUri){
        media = new Media(defaultUri.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void changeAudioRes(File curi){
        destroyMedia();
        media = new Media(curi.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play(){
        mediaPlayer.play();
        wholeTime =  mediaPlayer.getStopTime();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public String getEndTime(){
        if(wholeTime == null){
            return "None";
        }
        else {
            return formatTime(mediaPlayer.getStopTime().toSeconds());
        }
    }

    private void destroyMedia(){
        if(mediaPlayer.getStatus() == Status.PLAYING){
            mediaPlayer.stop();
        }
        mediaPlayer.dispose();
        media = null;
        wholeTime = null;
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

}
