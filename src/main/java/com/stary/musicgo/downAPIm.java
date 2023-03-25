package com.stary.musicgo;

public interface downAPIm {
    public boolean Start(String url, String dir, String name, String aut);
    public boolean Search(String key, String jsonpath);
}
