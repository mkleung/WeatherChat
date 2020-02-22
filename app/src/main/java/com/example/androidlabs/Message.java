package com.example.androidlabs;

public class Message {

    //Android Studio hint: to create getter and setter, put mouse on variable and
    // click "alt+insert" in Windows, "control+return" on Macintosh
    protected String title;
    protected long id;
    protected boolean isSender;

    /**Constructor:*/
    public Message(long id, String title, boolean isSender)
    {
        id = id;
        title = title;
        isSender = isSender;
    }

    public void update(String title, boolean isSender)
    {
        title = title;
        isSender = isSender;
    }

    /**Chaining constructor: */
    public Message(String n, boolean e)
    {
        this(0, n, e);
    }


    public String getTitle() {
        return title;
    }

    public boolean getIsSender() {
        return isSender;
    }

    public long getId() {
        return id;
    }
}