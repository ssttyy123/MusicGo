package com.stary.musicgo;

import javafx.beans.property.*;

public class ListFileCell {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty aut = new SimpleStringProperty();
    private SimpleStringProperty uri = new SimpleStringProperty();

    public ListFileCell(String name, String aut, String uri){
        this.name.set(name);
        this.aut.set(aut);
        this.uri.set(uri);
    }

    public void setName(String name){
        this.setName(name);
    }

    public String getName(){
        return this.name.get();
    }

    public void setAut(String aut){
        this.aut.set(aut);
    }

    public String getAut(){
        return this.aut.get();
    }

    public void setUri(String uri){
        this.uri.set(uri);
    }

    public String getUri(){
        return this.uri.get();
    }

    public SimpleStringProperty getNameProperty(){
        return this.name;
    }

    public SimpleStringProperty getAutProperty(){
        return this.aut;
    }

    public SimpleStringProperty getUriProperty(){
        return this.uri;
    }

    @Override
    public String toString() {
        return name.get() + ", " + aut.get() + ", " + uri.get();
    }
}
