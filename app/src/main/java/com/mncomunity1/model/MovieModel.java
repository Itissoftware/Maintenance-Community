package com.mncomunity1.model;

import java.io.Serializable;

public class MovieModel implements Serializable {


    public String nameen;
    public String nameth;
    public String address;
    public String cover;
    public String companyCode;
    public String cat;
    public String type;

    public MovieModel(String type) {
        this.type = type;
    }
}
