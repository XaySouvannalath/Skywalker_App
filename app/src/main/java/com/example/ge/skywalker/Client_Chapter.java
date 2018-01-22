package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Client_Course.CourseIDFor;
import static com.example.ge.skywalker.Client_Course.CourseNameFor;
import static com.example.ge.skywalker.Client_Course.globalCourseTypeFor;
import static com.example.ge.skywalker.Client_Course.globalCourseTypeNameFor;

public class Client_Chapter extends AppCompatActivity {
    Intent intent;
    List<vl_chapter> valueChapter;
    ListView listChapterClient;
  //  public static String globalCourseFor;
    DatabaseReference refChapter;
    DatabaseReference refRoot;
    DatabaseReference refCourse;
    public static String CourseIDForHeadingFor;
    public static String globalCourse;
    public static String globalCourseFor;
    public static final String ChapterIDFor = "chapterId";
    public static final String ChapterNameFor = "chapterNameValue";
    public static final String ChapterNumberFor = "chapterNumberValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__chapter);
        intent = getIntent();
        getSupportActionBar().setTitle(globalCourseTypeNameFor);
       getSupportActionBar().setSubtitle(intent.getStringExtra(CourseNameFor.toString()));
        listChapterClient = (ListView)findViewById(R.id.listChapterClient);
        CourseIDForHeadingFor = intent.getStringExtra(CourseIDFor);
        listChapterClient.setDividerHeight(0);
   globalCourse=intent.getStringExtra(CourseIDFor);
        globalCourseFor = intent.getStringExtra(CourseNameFor);
        valueChapter = new ArrayList<>();
      //  refRoot = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapterFor);
       // refChapter = refRoot.child(intent.getStringExtra(CourseIDFor.toString()));
        refChapter = FirebaseDatabase.getInstance().getReference("Chapters").child(globalCourseTypeFor).child(intent.getStringExtra(CourseIDFor.toString()));
        listChapterClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_chapter vlChapter = valueChapter.get(position);
                Intent intent1 = new Intent(getApplicationContext(), Client_Content.class);
                intent1.putExtra(ChapterIDFor, vlChapter.getChapterId());
                intent1.putExtra(ChapterNameFor, vlChapter.getChapterNameValue());
                intent1.putExtra(ChapterNumberFor, vlChapter.getChapterNumberValue());
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refChapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueChapter.clear();
                for(DataSnapshot chapterSnapshot: dataSnapshot.getChildren()){
                    vl_chapter tess = chapterSnapshot.getValue(vl_chapter.class);
                    valueChapter.add(tess);
                }
                List_Chapter_For_Client chapterList = new List_Chapter_For_Client(Client_Chapter.this,valueChapter);
                listChapterClient.setAdapter(chapterList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
