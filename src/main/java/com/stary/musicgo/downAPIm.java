package com.stary.musicgo;

public interface downAPIm {
    boolean Start(String url, String dir, String name, String aut, String rootdir);
    boolean Search(String key, String jsonpath, String rootdir, HistroySave histroySave);
}
