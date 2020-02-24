package com.example.androidlabs;

public class Chat {

    protected String message;
    protected int isSent;
    protected long id;

    /**Constructor:*/
    public Chat(String n, int e, long i)
    {
        message =n;
        isSent = e;
        id = i;
    }

    public void update(String n, int e)
    {
        message = n;
        isSent = e;
    }

    /**Chaining constructor: */
    public Chat(String n, int e) { this(n, e, 0);}


    public String getMessage() {
        return message;
    }

    public int getIsSent() {
        return isSent;
    }

    public long getId() {
        return id;
    }

}