package com.example.ge.skywalker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Ac_Coursetypes.CourseTypeName;

public class Ac_Courses extends AppCompatActivity {
Toolbar toolbar;
    FloatingActionButton fabAddCourse;
    DatabaseReference refCourses;
    List<vl_course> valueCourse;
    ListView listCourse;
    Intent intent;
        public static final String CourseID = "courseId";
        public static final String CourseName = "courseName";
    public static  String CourseTypeIDForChapter;
        public static String globalCourseType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_courses);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        fabAddCourse = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        listCourse = (ListView)findViewById(R.id.listCourse);
        valueCourse = new ArrayList<>();
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
         intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(CourseTypeName.toString()));
        CourseTypeIDForChapter=intent.getStringExtra(Ac_Coursetypes.CourseTypeID);
        globalCourseType=intent.getStringExtra(Ac_Coursetypes.CourseTypeID);

        refCourses = FirebaseDatabase.getInstance().getReference("Courses").child(intent.getStringExtra(Ac_Coursetypes.CourseTypeID));

        fabAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        listCourse.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vl_course vlCourse = valueCourse.get(position);
               // Toast.makeText(Ac_Courses.this, vlCourse.getCourseId().toString(), Toast.LENGTH_SHORT).show();
                updateDialog(vlCourse.getCourseId(),vlCourse.getCourseName());
                return true;
            }
        });
        listCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_course vlCourse = valueCourse.get(position);
                Intent intent = new Intent(getApplicationContext(), Ac_Chapters.class);
                intent.putExtra(CourseID, vlCourse.getCourseId());
                intent.putExtra(CourseName, vlCourse.getCourseName());
              //  intent.putExtra(CourseTypeID, )
                startActivity(intent);

            }
        });
    }
    private void addDialog(){

        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_course_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etcourse = (EditText)dialogView.findViewById(R.id.etcourse);
        final Button btaddcourse = (Button)dialogView.findViewById(R.id.btaddcourse);

        dialogBuilder.setTitle("ເພີ່ມຫຼັກສູດໃໝ່");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btaddcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coursetypenamevalue = etcourse.getText().toString();
                if(!TextUtils.isEmpty(coursetypenamevalue))
                {

                    // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tests");

                    String id = refCourses.push().getKey();
                    vl_course test = new vl_course(id,coursetypenamevalue);
                    refCourses.child(id).setValue(test);
                    etcourse.setText("");
                    etcourse.requestFocus();
                    Toast.makeText(Ac_Courses.this, "ບັນທຶກສໍາເລັດ", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }else{
                    Toast.makeText(Ac_Courses.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateDialog(final String courseId, final String courseName){
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_course_dialog,null);
        dialogBuilder.setView(dialogView);

        // final TextView txtcoursetypename = (TextView)dialogView.findViewById(R.id.editText4);
        final EditText txtcourse = (EditText) dialogView.findViewById(R.id.etcoursename);
        final Button btupdatecourse = (Button)dialogView.findViewById(R.id.bteditcourse);
        final Button btdeletecourse =(Button)dialogView.findViewById(R.id.btdeletecourse);
        // txtname.setText(name);
        txtcourse.setText(courseName);
        dialogBuilder.setTitle("ແກ້ໄຂຂໍ້ມູນ");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btupdatecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = txtcourse.getText().toString();
                if(!TextUtils.isEmpty(course)){
                    updateCourse(courseId,course );
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Ac_Courses.this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btdeletecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coursetype = txtcourse.getText().toString();
                if(!TextUtils.isEmpty(coursetype)){
                    deleteCourse(courseId);
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Ac_Courses.this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean deleteCourse(String courseId) {
        DatabaseReference refCourseType = FirebaseDatabase.getInstance().getReference("Courses").child(intent.getStringExtra(Ac_Coursetypes.CourseTypeID));
        DatabaseReference refCourse =refCourseType.child(courseId);
      //  DatabaseReference refCourse = FirebaseDatabase.getInstance().getReference("Courses").child(courseTypeId);

        refCourse.removeValue();
      //  refCourse.removeValue();
       // Toast.makeText(this, courseId.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "ລົບສໍາເລັດ", Toast.LENGTH_SHORT).show();
        return true;
    }

        private boolean updateCourse(String courseId, String course) {

        DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference("Courses").child(intent.getStringExtra(Ac_Coursetypes.CourseTypeID));
        DatabaseReference refCourse = databaseReference.child(courseId);
        vl_course vlCoursetype = new vl_course(courseId,course);
        refCourse.setValue(vlCoursetype).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "ແກ້ໄຂສໍາເລັດ!", Toast.LENGTH_SHORT).show();
            }
        });
            refCourse.setValue(vlCoursetype).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "fail!", Toast.LENGTH_SHORT).show();
                }
            });

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        refCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueCourse.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    vl_course tess = postSnapshot.getValue(vl_course.class);

                    valueCourse.add(tess);
                }
             //   Toast.makeText(Ac_Courses.this, valueCourse.toString(), Toast.LENGTH_SHORT).show();
                List_Courses testList = new List_Courses(Ac_Courses.this, valueCourse);

              //  vl_course vlCourse = valueCourse.get(1);
              //  Toast.makeText(Ac_Courses.this,  vlCourse.getCourseName(), Toast.LENGTH_SHORT).show();
                listCourse.setAdapter(testList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
