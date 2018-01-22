package com.example.ge.skywalker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by GE on 12/31/2017.
 */

public class TestDetailList extends ArrayAdapter<TestDetailVal> {

 Activity context;
    List<TestDetailVal> testDetailVals;
    public TestDetailList(Activity context, List<TestDetailVal> testDetailVals) {
        super(context, R.layout.layout_testdetail_list,testDetailVals);
        this.context = context;
        this.testDetailVals = testDetailVals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.layout_testdetail_list,null, true);

        TextView txtname =(TextView)ListViewItem.findViewById(R.id.textView);
        TextView txtlastname = (TextView)ListViewItem.findViewById(R.id.textView2);

        TestDetailVal testDetailVal = testDetailVals.get(position);
        txtname.setText(testDetailVal.getProducts());
        txtlastname.setText(testDetailVal.getQuantity().toString());

        return ListViewItem;
    }
}
