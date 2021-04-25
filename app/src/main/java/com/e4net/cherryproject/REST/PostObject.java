package com.e4net.cherryproject.REST;

import org.json.JSONObject;

public class PostObject {
    String url;
    public JSONObject jsonObject;

    public PostObject(String url, JSONObject jsonObject){
        this.url = url;
        this.jsonObject = jsonObject;
    }
}
