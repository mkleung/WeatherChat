package com.example.androidlabs;

public class Message {

    //Android Studio hint: to create getter and setter, put mouse on variable and
    // click "alt+insert" in Windows, "control+return" on Macintosh
    protected String title;
    protected long id;
    protected String origin;

    /**Constructor:*/
    public Message(long id, String title, String origin)
    {
        id = id;
        title = title;
        origin = origin;
    }

    public void update(String title, String origin)
    {
        title = title;
        origin = origin;
    }

    /**Chaining constructor: */
    public Message(String n, String e)
    {
        this(0, n, e);
    }


    public String getTitle() {
        return title;
    }

    public String getOrigin() {
        return origin;
    }

    public long getId() {
        return id;
    }
}