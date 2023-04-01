package com.stary.musicgo;

public class SearchList {
    private String name;
    private String aut;
    private String url;
    private String form;

    public String getAut() {
        return aut;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getForm(){
        return form;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAut(String aut) {
        this.aut = aut;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setForm(String form){
        this.form = form;
    }
}
