package com.example.ge.skywalker.Community;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ge.skywalker.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by GE on 1/25/2018.
 */

class List_Posts  extends ArrayAdapter{

View ListViewItem;
    private Activity context;
    List<vl_post> valuePost;
    DatabaseReference refCourse;
    public List_Posts(Activity context, List<vl_post> valuePost) {
        super(context, R.layout.ly_post,valuePost);
        this.context = context;
        this.valuePost = valuePost;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.ly_post,null, true);

        TextView txtuserpost =(TextView)ListViewItem.findViewById(R.id.txtuserpost);
        TextView txttimestamp =(TextView)ListViewItem.findViewById(R.id.txttimestamp);
        TextView txtpost =(TextView)ListViewItem.findViewById(R.id.txtpost);

       /// final TextView txtcoursecount = (TextView)ListViewItem.findViewById(R.id.txtcoursecount);

        vl_post vlPost = valuePost.get(position);
        txtpost.setText(vlPost.getPostvalue().toString());
        txttimestamp.setText("ໂພສ໌ເມື່ອ: " + vlPost.getPosttime().toString());
        txtuserpost.setText(vlPost.getUsername().toString() + ":");

     /*   refCourse= FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter).child(vlCourse.getCourseId());
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
*/
        return ListViewItem;
    }


}
