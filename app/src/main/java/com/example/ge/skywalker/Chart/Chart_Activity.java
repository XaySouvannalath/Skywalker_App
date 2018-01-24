package com.example.ge.skywalker.Chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.ge.skywalker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chart_Activity extends AppCompatActivity {
ListView listChart;
    List<vl_score> valueScore;
    DatabaseReference refChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_);
        refChart = FirebaseDatabase.getInstance().getReference("Scores");
        listChart = (ListView)findViewById(R.id.listChart);
        valueScore = new ArrayList<>();
        listChart.setDividerHeight(0);


    }

    @Override
    protected void onStart() {
        super.onStart();
        refChart.orderByChild("score").startAt(1).limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueScore.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    vl_score tess = postSnapshot.getValue(vl_score.class);

                    valueScore.add(tess);

                }
                Collections.reverse(valueScore);
                //   Toast.makeText(Ac_Courses.this, valueCourse.toString(), Toast.LENGTH_SHORT).show();
                List_Chart testList = new List_Chart(Chart_Activity.this, valueScore);

                //  vl_course vlCourse = valueCourse.get(1);
                //  Toast.makeText(Ac_Courses.this,  vlCourse.getCourseName(), Toast.LENGTH_SHORT).show();

              //
                listChart.setAdapter(testList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
