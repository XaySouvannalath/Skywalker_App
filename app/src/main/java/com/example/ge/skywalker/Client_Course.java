package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import static com.example.ge.skywalker.TestRecycleView.CourseTypeIDFor;
import static com.example.ge.skywalker.TestRecycleView.CourseTypeNameFor;

public class Client_Course extends AppCompatActivity {
ListView listCourseClient;

    DatabaseReference refCourses;
    List<vl_course> valueCourse;
   ;
    Intent intent;
    public static final String CourseIDFor = "courseId";
    public static final String CourseNameFor = "courseName";
    public static  String CourseTypeIDForChapterFor;
    public static String globalCourseTypeFor;
    public static String globalCourseTypeNameFor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__course);
        Intent intent = getIntent();
        listCourseClient = (ListView)findViewById(R.id.listCourseClient);
        valueCourse = new ArrayList<>();
        getSupportActionBar().setTitle(intent.getStringExtra(CourseTypeNameFor).toString());
        CourseTypeIDForChapterFor=intent.getStringExtra(CourseTypeIDFor);
        globalCourseTypeNameFor=intent.getStringExtra(CourseTypeNameFor);
        globalCourseTypeFor=intent.getStringExtra(CourseTypeIDFor);
        refCourses = FirebaseDatabase.getInstance().getReference("Courses").child(intent.getStringExtra(CourseTypeIDFor));
        listCourseClient.setDividerHeight(0);
        listCourseClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_course vlCourse = valueCourse.get(position);
                Intent intent = new Intent(getApplicationContext(), Client_Chapter.class);
                intent.putExtra(CourseIDFor, vlCourse.getCourseId());
                intent.putExtra(CourseNameFor, vlCourse.getCourseName());
                //  intent.putExtra(CourseTypeID, )
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        refCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueCourse.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    ///  TestVal tests = postSnapshot.getValue(TestVal.class);
                    vl_course tess = postSnapshot.getValue(vl_course.class);

                    valueCourse.add(tess);
                }
                List_Course_For_Client testList = new List_Course_For_Client(Client_Course.this, valueCourse);
                listCourseClient.setAdapter(testList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    public void gohome(MenuItem item) {
        Intent i = new Intent(this, Skybase_Home.class);
        startActivity(i);
    }
    public void gosetting(MenuItem item) {
        Intent i = new Intent(this, Ac_Coursetypes.class);
        startActivity(i);
    }
}
