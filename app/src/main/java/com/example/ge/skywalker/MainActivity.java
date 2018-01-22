package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void gotodbmgr(View view) {
        Intent i = new Intent(this, DatabaseManagement.class);
        startActivity(i);
    }
}
