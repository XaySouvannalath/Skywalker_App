package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ge.skywalker.Quiz.Quiz_Home_Activity;

public class Skybase_Home extends AppCompatActivity {
LinearLayout lyhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skybase__home);
        lyhome = (LinearLayout)findViewById(R.id.lyhome);
       getSupportActionBar().setTitle("Skybase");
        getSupportActionBar().setSubtitle("skywalker");
        lyhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotohomepage();
            }


        });

    }

    private void gotohomepage() {
        Intent i = new Intent(this, TestRecycleView.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_login){
            //  Toast.makeText(this, "Add Click", Toast.LENGTH_SHORT).show();
            //   Intent intent = new Intent(this, Ac_Headings.class);
            //   startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void gohome(MenuItem item) {
        Intent i = new Intent(this, Skybase_Home.class);
        startActivity(i);
    }

    public void gosetting(MenuItem item) {
        Intent i = new Intent(this, Ac_Coursetypes.class);
        startActivity(i);
    }

    public void gotoquiz(View view) {
        Intent i = new Intent(this, Quiz_Home_Activity.class);
        startActivity(i);
    }
}
