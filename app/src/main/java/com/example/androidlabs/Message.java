package com.example.androidlabs;

public class Message {
    public long id;
    public String title;
    public boolean isSender;

    public Message(long id, String title, boolean isSender) {
        this.id = id;
        this.title = title;
        this.isSender = isSender;
    }
}
