package com.example.androidlabs;

public class Chat {

    //Android Studio hint: to create getter and setter, put mouse on variable and
    // click "alt+insert" in Windows, "control+return" on Macintosh
    protected String message;
    protected boolean isSent;
    protected long id;

    /**Constructor:*/
    public Chat(String n, boolean e, long i)
    {
        message =n;
        isSent = e;
        id = i;
    }

    public void update(String n, boolean e)
    {
        message = n;
        isSent = e;
    }

    /**Chaining constructor: */
    public Chat(String n, boolean e) { this(n, e, 0);}


    public String getMessage() {
        return message;
    }

    public boolean getIsSent() {
        return isSent;
    }

    public long getId() {
        return id;
    }

}