package com.stary.musicgo;

import javafx.beans.property.*;

public class ListFileCell {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty aut = new SimpleStringProperty();
    private final SimpleStringProperty uri = new SimpleStringProperty();
    private final SimpleStringProperty form = new SimpleStringProperty();

    public ListFileCell(String name, String aut, String uri, String form){
        this.name.set(name);
        this.aut.set(aut);
        this.uri.set(uri);
        this.form.set(form);
    }

    public void setName(String name){
        this.name.set(name);
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

    public void setForm(String form){
        this.form.set(form);
    }

    public String getForm(){
        return this.form.get();
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

    public SimpleStringProperty getFormProperty(){
        return this.form;
    }

    @Override
    public String toString() {
        return name.get() + ", " + aut.get() + ", " + uri.get() + ", " + form.get();
    }
}
