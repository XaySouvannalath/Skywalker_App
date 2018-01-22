package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class TestRecycleView extends AppCompatActivity {
    ListView coursetypeList;
    public static final String CourseTypeIDFor = "courseTypeId";
    public static final String CourseTypeNameFor = "courseTypeName";
    public static String globalCourseTypeFor;

Toolbar toolbar;
    List<vl_coursetype> valueCourseType;
    DatabaseReference refCourseType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycle_view);
        refCourseType = FirebaseDatabase.getInstance().getReference("CourseTypes");
        valueCourseType = new ArrayList<>();
        getSupportActionBar().setSubtitle("Learn");
        coursetypeList = (ListView) findViewById(R.id.coursetypeList);
        coursetypeList.setDividerHeight(0);
        coursetypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_coursetype tess = valueCourseType.get(position);
                Intent intent = new Intent(getApplicationContext(), Client_Course.class);
                intent.putExtra(CourseTypeIDFor, tess.getCoursetypeid());
                intent.putExtra(CourseTypeNameFor, tess.getCoursetypename());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        refCourseType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueCourseType.clear();
                Integer i = 1;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    ///  TestVal tests = postSnapshot.getValue(TestVal.class);
                    vl_coursetype tess = postSnapshot.getValue(vl_coursetype.class);

                    valueCourseType.add(tess);
                }
                List_CourseType_For_Client testList = new List_CourseType_For_Client(TestRecycleView.this, valueCourseType);
                coursetypeList.setAdapter(testList);
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
