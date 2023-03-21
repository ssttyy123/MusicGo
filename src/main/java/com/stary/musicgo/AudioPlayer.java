package com.stary.musicgo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class AudioPlayer {
    private StringBuilder uri;
    private File mediaFile;
    private Media media;
    private MediaPlayer mediaPlayer;
    private Duration wholeTime = null;

    public AudioPlayer(StringBuilder defaultUri){
        this.uri = defaultUri;
        mediaFile = new File(uri.toString());
        media = new Media(this.mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void changeAudioRes(StringBuilder curi){
        destroyMedia();
        this.uri = curi;
        mediaFile = new File(curi.toString());
        media = new Media(mediaFile.toURI().toString());
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
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.stop();
        }
        mediaPlayer.dispose();
        media = null;
        mediaFile = null;
        wholeTime = null;
        System.gc();
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

}
