package com.stary.musicgo;

import java.util.ArrayList;
import java.util.List;

public class SaveJson {
    private String downerDir;//保存路径
    private String playingMusicUri;//历史播放的音频
    private String backgroundPath;//背景
    private List<String> localPath;//本地储存
    private int closeForm;//关闭格式
    private int nameForm;//命名格式
    private int keyMajusculeForm;//大小写
    private int searchReForm;//搜索匹配

    public SaveJson(){}

    public void initc(String downerDir, String playingMusicUri, List<String> localPath, String backgroundPath, int closeForm, int nameForm, int keyMajusculeForm, int searchReForm){
        setDownerDir(downerDir);
        setPlayingMusicUri(playingMusicUri);
        setLocalPath(localPath);
        setBackgroundPath(backgroundPath);
        setCloseForm(closeForm);
        setNameForm(nameForm);
        setKeyMajusculeForm(keyMajusculeForm);
        setSearchReForm(searchReForm);
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

    public List<String> getLocalPath(){
        return localPath;
    }

    public void setLocalPath(List<String> localPath){
        this.localPath = localPath;
    }

    public void addLocalPath(String localPath){
        ArrayList<String> temp = new ArrayList<>(this.localPath);
        temp.add(localPath);
        this.localPath = temp;
    }

    public int getCloseForm(){
        return closeForm;
    }

    public void setCloseForm(int closeForm){
        this.closeForm = closeForm;
    }

    public int getNameForm(){
        return nameForm;
    }

    public void setNameForm(int nameForm){
        this.nameForm = nameForm;
    }

    public int getKeyMajusculeForm(){
        return keyMajusculeForm;
    }

    public void setKeyMajusculeForm(int keyMajusculeForm){
        this.keyMajusculeForm = keyMajusculeForm;
    }

    public int getSearchReForm(){
        return searchReForm;
    }

    public void setSearchReForm(int searchReForm){
        this.searchReForm = searchReForm;
    }

    public String getBackgroundPath(){
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath){
        this.backgroundPath = backgroundPath;
    }

}
