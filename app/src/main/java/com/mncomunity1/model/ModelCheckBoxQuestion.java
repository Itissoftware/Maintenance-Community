package com.mncomunity1.model;

import java.io.Serializable;

public class ModelCheckBoxQuestion implements Serializable {


    private boolean isSelected;
    private String id;
    private String userid;
    private String category;
    private String title;
    private String details;
    private String path_photo;
    private String amount;


    public ModelCheckBoxQuestion(){

    }

    public ModelCheckBoxQuestion(String id, String userid, String category, String title, String details, String path_photo, String amount) {
        this.id = id;
        this.userid = userid;
        this.category = category;
        this.title = title;
        this.details = details;
        this.path_photo = path_photo;
        this.amount = amount;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath_photo() {
        return path_photo;
    }

    public void setPath_photo(String path_photo) {
        this.path_photo = path_photo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}


