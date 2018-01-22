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
 * Created by GE on 12/31/2017.
 */

public class TestList extends ArrayAdapter<TestVal> {
DatabaseReference dbCountTestDetail;
    private Activity context;
    List<TestVal> tests;
    public TestList(Activity context, List<TestVal> tests){
        super(context, R.layout.layout_test_list, tests);
        this.context = context;
        this.tests =tests;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.layout_test_list,null, true);

        TextView txtname =(TextView)ListViewItem.findViewById(R.id.textView);
        TextView txtlastname = (TextView)ListViewItem.findViewById(R.id.textView2);
        final TextView txtcount = (TextView)ListViewItem.findViewById(R.id.textView3);
        TestVal testval = tests.get(position);
        dbCountTestDetail= FirebaseDatabase.getInstance().getReference("TestDetails").child(testval.getTestId());
        dbCountTestDetail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
            //    Toast.makeText(Tests.this,count, Toast.LENGTH_SHORT).show();
                txtcount.setText("All Item: " + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        txtname.setText(testval.getName());
        txtlastname.setText(testval.getLastname());

        return ListViewItem;
    }
}
