package com.googleapis.roomandme;

import android.app.Application;

import com.googleapis.roomandme.models.User1;


public class UserClient extends Application {

    private User1 user = null;

    public User1 getUser() {
        return user;
    }

    public void setUser(User1 user) {
        this.user = user;
    }

}
