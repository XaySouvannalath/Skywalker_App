package com.example.ge.skywalker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.ge.skywalker.Ac_Courses.CourseTypeIDForChapter;

/**
 * Created by GE on 1/3/2018.
 */

class List_Courses extends ArrayAdapter {
    private Activity context;
    List<vl_course> valueCourse;
    DatabaseReference refCourse;
  //  public List_Courses(Activity context, List<vl_course> valueCourse) {

  //  }


    public List_Courses(Activity context, List<vl_course> valueCourse) {
        super(context, R.layout.ly_course,valueCourse);
        this.context = context;
        this.valueCourse = valueCourse;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.ly_course,null, true);

        TextView txtcourse =(TextView)ListViewItem.findViewById(R.id.txtcoursename);
        final TextView txtcoursecount = (TextView)ListViewItem.findViewById(R.id.txtcoursecount);

        vl_course vlCourse = valueCourse.get(position);
        txtcourse.setText(vlCourse.getCourseName().toString());

        refCourse= FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter).child(vlCourse.getCourseId());
        refCourse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
                //    Toast.makeText(Tests.this,count, Toast.LENGTH_SHORT).show();
                txtcoursecount.setText("ມີທັງໝົດ: " + count + " ບົດ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //  txtlastname.setText(listCourseTypes.getQuantity().toString());

        return ListViewItem;
    }
}
