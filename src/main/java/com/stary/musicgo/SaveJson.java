package com.stary.musicgo;

public class SaveJson {
    private String downerDir;//保存路径
    private String playingMusicUri;//历史播放的音频

    public SaveJson(){}

    public void initc(String downerDir, String playingMusicUri){
        setDownerDir(downerDir);
        setPlayingMusicUri(playingMusicUri);
    }

    public void setDownerDir(String downerDir){
        this.downerDir = downerDir;
    }

    public void setPlayingMusicUri(String playingMusicUri){
        this.playingMusicUri = playingMusicUri;
    }

    public String getDownerDir(){
        return downerDir;
    }

    public String getPlayingMusicUri(){
        return playingMusicUri;
    }

}
