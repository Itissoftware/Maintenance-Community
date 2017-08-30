package com.mncomunity1.model;

/**
 * Created by Bas on 8/23/2017 AD.
 */

public class ModelCat {
    String nameTh;
    String cat;

    public ModelCat(String nameTh, String cat) {
        this.nameTh = nameTh;
        this.cat = cat;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
