package com.mncomunity1.model;


public class TitleItem extends BaseOrderDetailItem {
    private String titlePhoto;

    public TitleItem() {
        super(OrderDetailType.TYPE_PHOTO);
    }

    public String getTitle() {
        return titlePhoto;
    }

    public void setTitle(String titlePhoto) {
        this.titlePhoto = titlePhoto;
    }
}
