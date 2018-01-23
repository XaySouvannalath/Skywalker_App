package com.example.ge.skywalker.UserLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.ge.skywalker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    Button btlogin, btregister;
    CheckBox chbshowpassword;
    EditText etusername, etpassword, etconfirmpassword;

    DatabaseReference refUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btlogin = (Button)findViewById(R.id.button5);
        btregister = (Button)findViewById(R.id.button6);

        chbshowpassword = (CheckBox)findViewById(R.id.chbshowpassword);

        etusername = (EditText)findViewById(R.id.etusername);
        etpassword = (EditText)findViewById(R.id.etpassword1);
        etconfirmpassword = (EditText)findViewById(R.id.etconfirmpassword);

        refUser = FirebaseDatabase.getInstance().getReference("Users");
        chbshowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etconfirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if(!isChecked){
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etconfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                String confirmpassword = etconfirmpassword.getText().toString();

                if(password.equals(confirmpassword)){
                    registerUser();
                }
                else {
                    Toast.makeText(Register.this, "ລະຫັດບໍ່ຕົງກັນ", Toast.LENGTH_SHORT).show();
                    etconfirmpassword.setText("");
                }
            }
        });

    }

    private void registerUser() {
        final ProgressDialog pgd = new ProgressDialog(this);
        pgd.setTitle("ກໍາລັງລົງທະບຽນ.....");
        pgd.show();
        String id = refUser.push().getKey();
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();
        if(!TextUtils.isEmpty(username)){
            vl_user vlUser = new vl_user(id, username,password);
            refUser.child(username).setValue(vlUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    pgd.dismiss();
                    etconfirmpassword.setText("");
                    etpassword.setText("");
                    etusername.setText("");
                    etusername.requestFocus();
                    Toast.makeText(Register.this, "ລົງທະບຽນສໍາເລັດ", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
        }
      
    }

    public void gologin(View view) {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
