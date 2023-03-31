package com.stary.musicgo;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.mpatric.mp3agic.*;

public class PlayList {
    private ArrayList<File> musicdir = new ArrayList<>();//多文件夹
    private ArrayList<File> mediaList = new ArrayList<>();
    private ArrayList<File> randList;
    private ArrayList<String> dirSave;
    private File currFile;
    private int radcurrp = -1;
    private int ordcurrp = 0;
    private int endp = 0;

    public PlayList(File defFile, ArrayList<String> dirURI){
        init(dirURI);
        if(defFile == null){
            this.currFile = null;
        }
        else {
            this.currFile = defFile;
            this.ordcurrp = this.mediaList.indexOf(defFile);
        }
    }

    public void init(ArrayList<String> dirURI){
        dirSave = dirURI;
        mediaList = new ArrayList<>();
        try{
            for(String i : dirURI){
                if(!musicdir.contains(new File(i))){
                    this.musicdir.add(new File(i));
                }
            }
            for(File i : musicdir){
                this.mediaList.addAll(List.of(Objects.requireNonNull(i.listFiles())));
            }
            this.endp = mediaList.size()-1;
            this.radcurrp = -1;
            this.ordcurrp = 0;
            if(this.currFile == null) this.currFile = rtFirst();
            randSong();
        }
        catch(NullPointerException e){
            System.out.println("文件夹为空！");
            e.printStackTrace();
        }
    }

    public File getRandSong(){
        this.radcurrp++;
        if(this.radcurrp > endp) this.radcurrp=0;
        this.currFile = this.randList.get(this.radcurrp);
        return currFile;
    }

    public File setFile(File f){
        this.ordcurrp = mediaList.indexOf(f);
        return f;
    }//点击歌曲事件

    public File getLastSong(){
        if(this.ordcurrp == 0) this.ordcurrp = this.endp;
        else this.ordcurrp--;
        this.currFile = mediaList.get(this.ordcurrp);
        return currFile;
    }

    public File getNextSong(){
        if(this.ordcurrp == this.endp) this.ordcurrp = 0;
        else this.ordcurrp++;
        this.currFile = mediaList.get(this.ordcurrp);
        return currFile;
    }

    public File rtFirst(){
        this.currFile = mediaList.get(0);
        return currFile;
    }

    public void reFlushList(){
        this.musicdir = new ArrayList<>();
        this.mediaList = null;
        this.randList = null;
        System.gc();
        init(this.dirSave);
        this.ordcurrp = this.mediaList.indexOf(this.currFile);
    }

    private void randSong(){
        this.randList = new ArrayList<>(this.mediaList);
        Collections.shuffle(this.randList);
    }

    public String getcurrMusic(){
        return this.currFile.toString();
    }

    //String name, String aut, String uri
    public List<ListFileCell> getFileList() {
        List<ListFileCell> files = new ArrayList<>();
        for (File i : this.mediaList){
            String name = i.toString().substring(i.toString().lastIndexOf("\\")+1, i.toString().lastIndexOf("."));
            String aut;
            try {
                Mp3File mp3File = new Mp3File(i);
                ID3v2 idtag = mp3File.getId3v2Tag();
                aut = idtag.getArtist();
                if(aut == null){
                    aut = "佚名";
                }
            }
            catch (UnsupportedTagException | InvalidDataException | NullPointerException | IOException e){
                aut = "佚名";
            }
            String uri = i.toString();
            files.add(new ListFileCell(name, aut, uri));
        }
        //for(LocalFile i : files)System.out.println(i.toString());

        return files;
    }
}
