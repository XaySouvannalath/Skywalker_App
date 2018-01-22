package com.example.ge.skywalker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class act_CourseTypes extends AppCompatActivity {
DatabaseReference refCourseType = FirebaseDatabase.getInstance().getReference("CourseType");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__course_types);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    public void closebutton(View view) {
       // Intent i = new Intent(this, Show_CourseTypes.class);
        //startActivity(i);\
        finish();
    }
EditText txtcoursetype;
    public void savect(View view) {
        txtcoursetype = (EditText)findViewById(R.id.etcoursetype);
        
        if(!TextUtils.isEmpty(txtcoursetype.getText().toString())){
          //  Toast.makeText(this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
            txtcoursetype.setText("");

        }
        else{
            Toast.makeText(this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveData(){

    }
}
