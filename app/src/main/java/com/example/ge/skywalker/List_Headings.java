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

import static com.example.ge.skywalker.Ac_Chapters.globalCourse;
import static com.example.ge.skywalker.Ac_Courses.globalCourseType;
import static com.example.ge.skywalker.Ac_Headings.globalChapter;

/**
 * Created by GE on 1/4/2018.
 */

public class List_Headings  extends ArrayAdapter{
    private Activity context;
    List<vl_heading> valueHeading;
    DatabaseReference refHeading;
    public List_Headings(Activity context, List<vl_heading> valueHeading) {
        super(context, R.layout.ly_heading,valueHeading);
        this.context=context;
        this.valueHeading = valueHeading;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.ly_heading,null, true);
        TextView txtheadingname = (TextView) ListViewItem.findViewById(R.id.txtheading);
        final TextView txtcountdescription = (TextView)ListViewItem.findViewById(R.id.txtdescriptioncount);
        vl_heading vlHeading = valueHeading.get(position);
        txtheadingname.setText(vlHeading.getHeadingName().toString());
        refHeading = FirebaseDatabase.getInstance().getReference("Descriptions").child(globalCourseType).child(globalCourse).child(globalChapter).child(vlHeading.getHeadingId());
        refHeading.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
                //    Toast.makeText(Tests.this,count, Toast.LENGTH_SHORT).show();
                txtcountdescription.setText(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return ListViewItem;

    }
}
