package com.example.androidlabs;

public class Chat {

    //Android Studio hint: to create getter and setter, put mouse on variable and
    // click "alt+insert" in Windows, "control+return" on Macintosh
    protected String title, type;
    protected long id;

    /**Constructor:*/
    public Chat(String n, String e, long i)
    {
        title =n;
        type = e;
        id = i;
    }

    public void update(String n, String e)
    {
        title = n;
        type = e;
    }

    /**Chaining constructor: */
    public Chat(String n, String e) { this(n, e, 0);}


    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

}