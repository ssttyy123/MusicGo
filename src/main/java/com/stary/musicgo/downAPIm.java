package com.stary.musicgo;

public interface downAPIm {
    public boolean Start(String url, String dir, String name, String aut, String rootdir);
    public boolean Search(String key, String jsonpath, String rootdir);
}
