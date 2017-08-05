package com.mncomunity1.model;

/**
 * Created by Akexorcist on 10/28/2016 AD.
 */

public class ContentItem extends BaseOrderDetailItem {

    private String detail;

    public ContentItem() {
        super(OrderDetailType.TYPE_TEXT);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
