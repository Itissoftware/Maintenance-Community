package com.mncomunity1.pack_chat.model;



public class User2 {
    public String userId;
    public String name;
    public String email;
    public String avata;

    public User2(){

    }

    public User2(String userId, String name, String email, String avata) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.avata = avata;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }
}
