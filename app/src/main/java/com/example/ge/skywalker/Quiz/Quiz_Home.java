package com.example.ge.skywalker.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ge.skywalker.Ac_Coursetypes;
import com.example.ge.skywalker.R;
import com.example.ge.skywalker.Skybase_Home;

public class Quiz_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__home);
        getSupportActionBar();
    }

    public void gohome(MenuItem item) {
        Intent ii = new Intent(this, Skybase_Home.class);
        startActivity(ii);
    }

    public void gosetting(MenuItem item) {
        Intent i = new Intent(this, Ac_Coursetypes.class);
        startActivity(i);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
