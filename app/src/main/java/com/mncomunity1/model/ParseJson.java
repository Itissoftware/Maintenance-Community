package com.mncomunity1.model;

/**
 * Created by Bas on 7/4/2017 AD.
 */

public class ParseJson {
    String name;
    String url;

    public ParseJson(){

    }

    public ParseJson(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
