package com.mncomunity1.pack_chat.model;



public class Message{


    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_TEXT = "text";
    public String idSender;
    public String idReceiver;
    public String text;
    public long timestamp;
    public String type;
}