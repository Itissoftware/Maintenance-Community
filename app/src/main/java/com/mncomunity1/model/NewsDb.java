package com.mncomunity1.model;

public class NewsDb {

    private int id;
    private String nameth;
    private String content;
    private String cover;
    private String category;
    private String check;
    private String ts;

    public NewsDb() {

    }

    public NewsDb(int id, String nameth, String content, String cover, String category, String check, String ts) {
        this.id = id;
        this.nameth = nameth;
        this.content = content;
        this.cover = cover;
        this.category = category;
        this.check = check;
        this.ts = ts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameth() {
        return nameth;
    }

    public void setNameth(String nameth) {
        this.nameth = nameth;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
