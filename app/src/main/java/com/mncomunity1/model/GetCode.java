package com.mncomunity1.model;

/**
 * Created by chonlakantsattaboot on 2/22/2017 AD.
 */

public class GetCode {


    private String success;
    private String user;
    private String name;
    private String email;
    private String code_company;
    private String company;

    public GetCode() {
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode_company() {
        return code_company;
    }

    public void setCode_company(String code_company) {
        this.code_company = code_company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
