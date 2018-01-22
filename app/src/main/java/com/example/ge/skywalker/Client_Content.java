package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ge.skywalker.Heading.vl_description;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Client_Chapter.ChapterIDFor;
import static com.example.ge.skywalker.Client_Chapter.ChapterNameFor;
import static com.example.ge.skywalker.Client_Chapter.ChapterNumberFor;
import static com.example.ge.skywalker.Client_Chapter.CourseIDForHeadingFor;
import static com.example.ge.skywalker.Client_Chapter.globalCourseFor;
import static com.example.ge.skywalker.Client_Course.CourseTypeIDForChapterFor;
import static com.example.ge.skywalker.Client_Course.globalCourseTypeFor;
import static com.example.ge.skywalker.Client_Course.globalCourseTypeNameFor;

public class Client_Content extends AppCompatActivity {

    public static final String HeadingIDFor = "headingId";
    public static final String HeadingNameFor = "headingName";
    public static String globalChapterFor;
    public static String globalChapterNameFor;
    public static String globalChapterNumberFor;
    Intent intent;
    int progressStatus = 0;
    List<vl_heading> valueHeading;
    List<vl_description> valueDescription;
    ListView listContent;

    DatabaseReference refHeading, refRoot, refChapter, refCourse;

    public static String ChapterIDForHeading;
    public static String courseIDForAddheading;
    EditText etheadingname;
    public static String sentCourseID;


public static String cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__content);
        intent = getIntent();
        getSupportActionBar().setTitle(globalCourseTypeNameFor);
        getSupportActionBar().setSubtitle(globalCourseFor+ " > " + "ບົດທີ: "+ intent.getStringExtra(ChapterNumberFor) + " " + intent.getStringExtra(ChapterNameFor));
        valueHeading = new ArrayList<>();
        listContent = (ListView)findViewById(R.id.listContent);
        globalChapterFor=intent.getStringExtra(ChapterIDFor);
        globalChapterNameFor = intent.getStringExtra(ChapterNameFor);
        globalChapterNumberFor = intent.getStringExtra(ChapterNumberFor);
    //    refHeading = FirebaseDatabase.getInstance().getReference("Headings").child(intent.getStringExtra(ChapterIDFor));
        refHeading = FirebaseDatabase.getInstance().getReference("Headings").child(CourseTypeIDForChapterFor).child(CourseIDForHeadingFor).child(intent.getStringExtra(ChapterIDFor.toString()));
        cid = globalCourseTypeFor;
        listContent.setDividerHeight(0);
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_heading vlHeading = valueHeading.get(position);
                Intent intent2 = new Intent(getApplicationContext(),Client_Description.class);
                intent2.putExtra(HeadingIDFor, vlHeading.getHeadingId());
                intent2.putExtra(HeadingNameFor,vlHeading.getHeadingName());
                startActivity(intent2);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        refHeading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueHeading.clear();
                for(DataSnapshot chapterSnapshot: dataSnapshot.getChildren()){
                    vl_heading tess = chapterSnapshot.getValue(vl_heading.class);
                    valueHeading.add(tess);
                }
                List_Content headingList = new List_Content(Client_Content.this,valueHeading);
                listContent.setAdapter(headingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
