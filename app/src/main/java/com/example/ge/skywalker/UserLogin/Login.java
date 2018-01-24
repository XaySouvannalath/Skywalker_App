package com.example.ge.skywalker.UserLogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ge.skywalker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button btlogin;
    EditText etusername, etpassword;
    CheckBox chbshowpassword;
    DatabaseReference refUser;
    public static String UserName;
     String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
refUser  = FirebaseDatabase.getInstance().getReference("Users");
        btlogin = (Button)findViewById(R.id.button5);

        chbshowpassword = (CheckBox)findViewById(R.id.chbshowpassword);
        etusername = (EditText)findViewById(R.id.etusername);
        etpassword = (EditText)findViewById(R.id.etpassword);
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("ຄໍາເຕືອນ!");
        al.setMessage("ບໍ່ມີຜູ້ໃຊ້ນີ້ໃນລະບົບ");
        al.setIcon(R.drawable.ic_warning_black_48dp);
        al.setPositiveButton("ລອງໃໝ່", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              etusername.setText("");
            }
        });
       final AlertDialog aa = al.create();
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refCheckUser;
               username = etusername.getText().toString();
                if(!TextUtils.isEmpty(username)){
                    refCheckUser = FirebaseDatabase.getInstance().getReference("Users").child(username);
                    refCheckUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()){
                                //Toast.makeText(Login.this, "nohave", Toast.LENGTH_SHORT).show();
                                aa.show();

                            }
                            else{
                                sendUsernameValue(username);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(Login.this, "ກະລຸນາປ້ອນຊື່ຜູ້ໃຊ້", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
    public void sendUsernameValue(String usernameValue){
        Intent i = new Intent(this, LoginPassword.class);
        i.putExtra(UserName, usernameValue);
        startActivity(i);
    }
}
