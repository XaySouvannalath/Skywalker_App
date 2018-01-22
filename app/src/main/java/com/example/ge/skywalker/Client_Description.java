package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.ge.skywalker.Heading.vl_description;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Client_Chapter.CourseIDForHeadingFor;
import static com.example.ge.skywalker.Client_Chapter.globalCourseFor;
import static com.example.ge.skywalker.Client_Content.HeadingIDFor;
import static com.example.ge.skywalker.Client_Content.HeadingNameFor;
import static com.example.ge.skywalker.Client_Content.globalChapterFor;
import static com.example.ge.skywalker.Client_Content.globalChapterNameFor;
import static com.example.ge.skywalker.Client_Course.CourseTypeIDForChapterFor;

public class Client_Description extends AppCompatActivity {
    Intent intent;
    List<vl_description> valueDescription;
    ListView listDescriptionFor;
    DatabaseReference refDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__description);
        intent = getIntent();
        listDescriptionFor = (ListView)findViewById(R.id.listDescriptionFor);
        valueDescription = new ArrayList<>();
        getSupportActionBar().setTitle(globalCourseFor);
     //   getSupportActionBar().setSubtitle();
        getSupportActionBar().setSubtitle(globalChapterNameFor + " > " + intent.getStringExtra(HeadingNameFor).toString());
        refDescription = FirebaseDatabase.getInstance().getReference("Descriptions").child(CourseTypeIDForChapterFor).child(CourseIDForHeadingFor).child(globalChapterFor).child(intent.getStringExtra(HeadingIDFor.toString()));
      //  Toast.makeText(this, refDescription.toString(), Toast.LENGTH_LONG).show();
        listDescriptionFor.setDividerHeight(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueDescription.clear();
                for(DataSnapshot descriptionSnapshot: dataSnapshot.getChildren()){
                    vl_description tess = descriptionSnapshot.getValue(vl_description.class);
                    valueDescription.add(tess);
                }
                List_Description_For_Client descriptionlist = new List_Description_For_Client(Client_Description.this,valueDescription);
                listDescriptionFor.setAdapter(descriptionlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
