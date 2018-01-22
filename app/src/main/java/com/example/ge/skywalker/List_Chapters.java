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

import static com.example.ge.skywalker.Ac_Chapters.CourseIDForHeading;
import static com.example.ge.skywalker.Ac_Courses.CourseTypeIDForChapter;

/**
 * Created by GE on 1/3/2018.
 */

public class List_Chapters extends ArrayAdapter {
    private Activity context;
    List<vl_chapter> valueChapter;
    DatabaseReference refChapter;
    public List_Chapters(Activity context, List<vl_chapter> valueChapter) {
        super(context, R.layout.ly_chapter,valueChapter);
        this.context=context;
        this.valueChapter = valueChapter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.ly_chapter,null, true);

        TextView txtchapternumber =(TextView)ListViewItem.findViewById(R.id.txtchapternumber);
        TextView txtchaptername =(TextView)ListViewItem.findViewById(R.id.txtchaptername);
        final TextView txtheadingcount =(TextView)ListViewItem.findViewById(R.id.txtheadingcount);

      //  final TextView txtcoursecount = (TextView)ListViewItem.findViewById(R.id.txtcoursecount);

        vl_chapter vlChapter = valueChapter.get(position);
        txtchaptername.setText(vlChapter.getChapterNameValue().toString());
        txtchapternumber.setText(vlChapter.getChapterNumberValue().toString());

        refChapter= FirebaseDatabase.getInstance().getReference("Headings").child(CourseTypeIDForChapter).child(CourseIDForHeading).child(vlChapter.getChapterId());
        refChapter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
                //    Toast.makeText(Tests.this,count, Toast.LENGTH_SHORT).show();
                txtheadingcount.setText(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //  txtlastname.setText(listCourseTypes.getQuantity().toString());

        return ListViewItem;
    }
}
