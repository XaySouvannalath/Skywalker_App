package com.example.ge.skywalker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ge.skywalker.Quiz.Quiz_Home_Activity;
import com.example.ge.skywalker.UserLogin.Register;

import static com.example.ge.skywalker.LoginPassword.globalusername;

public class Skybase_Home extends AppCompatActivity {
LinearLayout lyhome;
    public static  MenuItem menuitem ;
    TextView txtusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skybase__home);
        lyhome = (LinearLayout)findViewById(R.id.lyhome);

        txtusername= (TextView)findViewById(R.id.txtusername);
        Intent intent;
        intent = getIntent();
        Toast.makeText(this, "ຍິນດີຕ້ອນຮັບ " + globalusername, Toast.LENGTH_SHORT).show();
        txtusername.setText(globalusername);


       getSupportActionBar().setTitle("Skybase");
        getSupportActionBar().setSubtitle("skywalker");
        lyhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotohomepage();
            }


        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtusername= (TextView)findViewById(R.id.txtusername);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        txtusername= (TextView)findViewById(R.id.txtusername);
    }

    private void gotohomepage() {
        Intent i = new Intent(this, TestRecycleView.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menuitem = menu.findItem(R.id.action_setting);
        if(globalusername.equals("Saiyavong")){

            menuitem.setVisible(true);
        }else{
            menuitem.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_login){

            Intent intent = new Intent(this, Register.class);
               startActivity(intent);
        }else if(item.getItemId()==R.id.action_about){
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
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

    public void gotochart(View view) {
        MediaPlayer ring= MediaPlayer.create(Skybase_Home.this,R.raw.correct);
        ring.start();
    }
}
