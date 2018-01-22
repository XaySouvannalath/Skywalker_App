package com.example.ge.skywalker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Show_CourseTypes extends AppCompatActivity {
FloatingActionButton btaddmorecoursetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__course_types);
        btaddmorecoursetype = (FloatingActionButton)findViewById(R.id.btaddmorecoursetype);
        btaddmorecoursetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addct();
            }
        });
    }
    public void addct(){
        Intent i = new Intent(getBaseContext(), act_CourseTypes.class);
        getBaseContext().startActivity(i);
    }
}
