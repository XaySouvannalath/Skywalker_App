package com.example.ge.skywalker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ge.skywalker.Heading.vl_description;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE on 1/4/2018.
 */

public class List_Content  extends ArrayAdapter{
    private Activity context;
    List<vl_heading> valueHeading;
    List<vl_description> valueDescription;
    DatabaseReference refDescription;
    public List_Content(Activity context, List<vl_heading> valueHeading) {
        super(context, R.layout.layout_content_client,valueHeading);
        this.context=context;
        this.valueHeading = valueHeading;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View ListViewItem = inflater.inflate(R.layout.layout_content_client,null, true);
        TextView txtheadingname = (TextView) ListViewItem.findViewById(R.id.txtheadingFor);

        vl_heading vlHeading = valueHeading.get(position);
        txtheadingname.setText(vlHeading.getHeadingName().toString());
        valueDescription = new ArrayList<>();
        return ListViewItem;

    }
}
