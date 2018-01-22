package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DatabaseManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_management);


    }


    public void gotocoursetype(View view) {
        Intent i = new Intent(this, Ac_Coursetypes.class);
        startActivity(i);
    }

    public void gototest(View view) {
        Intent i = new Intent(this, Tests.class);
        startActivity(i);
    }
}
