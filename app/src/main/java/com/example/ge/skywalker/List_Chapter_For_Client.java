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

import static com.example.ge.skywalker.Client_Chapter.CourseIDForHeadingFor;
import static com.example.ge.skywalker.Client_Course.CourseTypeIDForChapterFor;

/**
 * Created by GE on 1/3/2018.
 */

public class List_Chapter_For_Client extends ArrayAdapter {
    private Activity context;
    List<vl_chapter> valueChapter;
    DatabaseReference refChapter;
    public List_Chapter_For_Client(Activity context, List<vl_chapter> valueChapter) {
        super(context, R.layout.layout_chapter_client,valueChapter);
        this.context=context;
        this.valueChapter = valueChapter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.layout_chapter_client,null, true);

        TextView txtchapternumber =(TextView)ListViewItem.findViewById(R.id.txtchapternumberfor);
        TextView txtchaptername =(TextView)ListViewItem.findViewById(R.id.txtchapternamefor);
        final TextView txtheadingcount =(TextView)ListViewItem.findViewById(R.id.txtheadingcountfor);

        //  final TextView txtcoursecount = (TextView)ListViewItem.findViewById(R.id.txtcoursecount);

        vl_chapter vlChapter = valueChapter.get(position);
        txtchaptername.setText(vlChapter.getChapterNameValue().toString());
        txtchapternumber.setText(vlChapter.getChapterNumberValue().toString());

        refChapter= FirebaseDatabase.getInstance().getReference("Headings").child(CourseTypeIDForChapterFor).child(CourseIDForHeadingFor).child(vlChapter.getChapterId());
        refChapter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
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
