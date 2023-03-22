package com.stary.musicgo;

import java.io.File;
import java.util.*;

public class PlayList {
    private ArrayList<File> musicdir = new ArrayList<>();//多文件夹
    private ArrayList<File> mediaList;
    private ArrayList<File> randList;
    private File currFile = null;
    private String dirURI;
    private int radcurrp = -1;
    private int ordcurrp = 0;
    private int endp = 0;

    public PlayList(File defFile, String dirURI){
        init(dirURI);
        if(defFile == null){
            currFile = null;
        }
        else {
            currFile = defFile;
            ordcurrp = mediaList.indexOf(defFile);
        }
    }

    public void init(String dirURI){
        try{
            this.dirURI = dirURI;
            musicdir.add(new File(dirURI));
            mediaList = new ArrayList<File>(List.of(Objects.requireNonNull(musicdir.get(0).listFiles())));
            endp = mediaList.size()-1;
            radcurrp = -1;
            ordcurrp = 0;
            if(currFile == null) currFile = rtFirst();
            randSong();
        }
        catch(NullPointerException e){
            System.out.println("文件夹为空！");
            e.printStackTrace();
        }
    }

    public ArrayList<File> returnSong(String form){
        if(Objects.equals(form, "order")) return mediaList;
        else if (Objects.equals(form, "rand")) return randList;
        return null;
    }

    public File getRandSong(){
        this.radcurrp++;
        if(this.radcurrp > endp) this.radcurrp=0;
        this.currFile = this.randList.get(this.radcurrp);
        return currFile;
    }


    public File setOrderIndex(int index){
        this.ordcurrp = index-1;
        currFile = getNextSong();
        return currFile;
    }//点击歌曲事件

    public File getLastSong(){
        if(this.ordcurrp == 0) this.ordcurrp = this.endp;
        else this.ordcurrp--;
        currFile = mediaList.get(this.ordcurrp);
        return currFile;
    }

    public File getNextSong(){
        if(this.ordcurrp == this.endp) this.ordcurrp = 0;
        else this.ordcurrp++;
        currFile = mediaList.get(this.ordcurrp);
        return currFile;
    }

    public File rtFirst(){
        currFile = mediaList.get(0);
        return currFile;
    }

    public void reFlushList(){
        musicdir = null;
        mediaList = null;
        randList = null;
        System.gc();
        init(this.dirURI);
        this.ordcurrp = mediaList.indexOf(currFile);
    }

    private void randSong(){
        randList = new ArrayList<>(mediaList);
        Collections.shuffle(randList);
    }

    public String getDirURI(){
        return dirURI;
    }

    public String getcurrMusic(){
        return currFile.toString();
    }
}
