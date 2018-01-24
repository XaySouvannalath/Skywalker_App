package com.example.ge.skywalker.Community;

/**
 * Created by GE on 1/25/2018.
 */

class vl_post {
    String id, username, postvalue,posttime;

    public vl_post(String id, String username, String postvalue, String posttime) {
        this.id = id;
        this.username = username;
        this.postvalue = postvalue;
        this.posttime = posttime;
    }

    public vl_post() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPostvalue() {
        return postvalue;
    }

    public String getPosttime() {
        return posttime;
    }
}
