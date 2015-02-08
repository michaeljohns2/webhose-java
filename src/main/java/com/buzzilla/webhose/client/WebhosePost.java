package com.buzzilla.webhose.client;

import com.google.api.client.util.Key;

/**
 * @author Israel Tsadok
 */
public class WebhosePost {

    @Key
    public String url;

    @Key
    public String title;

    @Key
    public String author;

    @Key
    public String text;

    @Key
    public String published;

    @Key
    public String crawled;

    @Key("ord_in_thread")
    public int ordInThread;

    @Key
    public String language;


    @Key
    public WebhoseThread thread;
}
