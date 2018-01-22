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

/**
 * Created by GE on 1/2/2018.
 */

public class List_CourseType_For_Client extends ArrayAdapter {
    Activity context;
    DatabaseReference refCourse;
    List<vl_coursetype> coursetypevals;
    public List_CourseType_For_Client(Activity context, List<vl_coursetype> coursetypevals) {
        super(context, R.layout.layout_coursetype_client,coursetypevals);
        this.context = context;
        this.coursetypevals = coursetypevals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.layout_coursetype_client,null, true);

        TextView txtcoursetypename =(TextView)ListViewItem.findViewById(R.id.txtcoursetypenamefor);
        final TextView txtcountcourse = (TextView)ListViewItem.findViewById(R.id.txtcountcourse);

        vl_coursetype vlCoursetype = coursetypevals.get(position);
        txtcoursetypename.setText(vlCoursetype.getCoursetypename().toString());

      refCourse= FirebaseDatabase.getInstance().getReference("Courses").child(vlCoursetype.getCoursetypeid());
        refCourse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
                //    Toast.makeText(Tests.this,count, Toast.LENGTH_SHORT).show();
                txtcountcourse.setText("ຫຼັກສູດທັງໝົດ: " + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //  txtlastname.setText(listCourseTypes.getQuantity().toString());

        return ListViewItem;
    }
}
