package com.example.ge.skywalker;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by GE on 1/4/2018.
 */

public class Skywalker extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
