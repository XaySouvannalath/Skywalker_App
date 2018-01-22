package com.example.ge.skywalker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.ge.skywalker.UserLogin.Login.UserName;

public class LoginPassword extends AppCompatActivity {
    CheckBox chbshowpassword; EditText etusername, etpassword; Button btlogin;
    Intent intent;
    public static String globalusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        btlogin = (Button)findViewById(R.id.button5);
        etpassword = (EditText)findViewById(R.id.etpassword);
        chbshowpassword = (CheckBox)findViewById(R.id.chbshowpassword);
        intent = getIntent();
        final String recieveUsername = intent.getStringExtra(UserName).toString();
        chbshowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if(!isChecked){
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("ຄໍາເຕືອນ!");
        al.setMessage("ລະຫັດບໍ່ຖືກຕ້ອງ");
        al.setIcon(R.drawable.ic_warning_black_48dp);
        al.setPositiveButton("ລອງໃໝ່", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etpassword.setText("");
            }
        });
        final AlertDialog aa = al.create();
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = etpassword.getText().toString();
                if(!TextUtils.isEmpty(password)){
                    DatabaseReference refPasswordCheck = FirebaseDatabase.getInstance().getReference("Users").child(recieveUsername);
                    refPasswordCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String dbpassword = dataSnapshot.child("password").getValue().toString();
                            if(dbpassword.equals(password)){
                                //Toast.makeText(LoginPassword.this, "Pass", Toast.LENGTH_SHORT).show();
                                StartApp(recieveUsername);
                            }else{
                                aa.show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(LoginPassword.this, "ກະລຸນາປ້ອນລະຫັດ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void StartApp(String name) {

        Intent i = new Intent(this, Skybase_Home.class);
        i.putExtra(globalusername, name);
        startActivity(i);
    }
}
