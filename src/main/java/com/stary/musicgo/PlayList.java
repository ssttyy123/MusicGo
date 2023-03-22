package com.stary.musicgo;

import java.io.File;
import java.util.*;

public class PlayList {
    private ArrayList<File> musicdir = new ArrayList<>();//多文件夹
    private ArrayList<File> mediaList;
    private ArrayList<File> randList;
    private int radcurrp = -1;
    private int ordcurrp = 0;
    private int endp = 0;

    public void init(String dirURI){
        try{
            musicdir.add(new File(dirURI));
            mediaList = new ArrayList<File>(List.of(Objects.requireNonNull(musicdir.get(0).listFiles())));
            endp = mediaList.size()-1;
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
        return this.randList.get(this.radcurrp);
    }


    public File setOrderIndex(int index){
        this.ordcurrp = index-1;
        return getNextSong();
    }

    public File getLastSong(){
        if(this.ordcurrp == 0) this.ordcurrp = this.endp;
        else this.ordcurrp--;
        return mediaList.get(this.ordcurrp);
    }

    public File getNextSong(){
        if(this.ordcurrp == this.endp) this.ordcurrp = 0;
        else this.ordcurrp++;
        return mediaList.get(this.ordcurrp);
    }

    public File rtFirst(){
        return mediaList.get(0);
    }

    private void randSong(){
        randList = new ArrayList<>(mediaList);
        Collections.shuffle(randList);
    }
}
