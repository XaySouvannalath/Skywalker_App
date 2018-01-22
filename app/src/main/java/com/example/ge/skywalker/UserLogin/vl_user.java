package com.example.ge.skywalker.UserLogin;

/**
 * Created by GE on 1/22/2018.
 */

class vl_user {
    String id, username, password;

    public vl_user(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
