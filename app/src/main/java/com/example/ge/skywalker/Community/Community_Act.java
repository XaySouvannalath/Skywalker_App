package com.example.ge.skywalker.Community;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ge.skywalker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.UserLogin.LoginPassword.globalusername;

public class Community_Act extends AppCompatActivity {
ListView listPost;
    FloatingActionButton fabPost;
    DatabaseReference refPost;
    EditText message_text;
    List<vl_post> valuePost;
    ImageView sent_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_2);
        listPost = (ListView)findViewById(R.id.listPost);
        message_text = (EditText)findViewById(R.id.message_text);
        refPost = FirebaseDatabase.getInstance().getReference("Posts");
sent_button = (ImageView)findViewById(R.id.send_button);
        sent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   addPostDialog();
                addPost();
            }
        });
        valuePost = new ArrayList<>();

        listPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void addPost() {
                String postvalue = message_text.getText().toString();
                if(!TextUtils.isEmpty(postvalue))
                {
                    String timeStamp = new SimpleDateFormat("HH:mm:ss dd/MM/YYYY").format(new java.util.Date());
                    String id = refPost.push().getKey();
                    vl_post test = new vl_post(id, globalusername, postvalue, timeStamp);
                    refPost.child(id).setValue(test);
                    message_text.setText("");
                  //  message_text.requestFocus();
                 //   listPost.setSelection(listPost.getAdapter().getCount()-1);

                  //  Toast.makeText(Community_Act.this, "ບັນທຶກສໍາເລັດ", Toast.LENGTH_SHORT).show();
                    listPost.setSelection(listPost.getAdapter().getCount()-1);
                }else{
                    Toast.makeText(Community_Act.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                }
    }



    @Override
    protected void onStart() {
        super.onStart();
        refPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valuePost.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    vl_post tess = postSnapshot.getValue(vl_post.class);

                    valuePost.add(tess);
                }
                //   Toast.makeText(Ac_Courses.this, valueCourse.toString(), Toast.LENGTH_SHORT).show();
                List_Posts testList = new List_Posts(Community_Act.this, valuePost);

                //  vl_course vlCourse = valueCourse.get(1);
                //  Toast.makeText(Ac_Courses.this,  vlCourse.getCourseName(), Toast.LENGTH_SHORT).show();
                listPost.setAdapter(testList);
                listPost.setSelection(listPost.getAdapter().getCount()-1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
