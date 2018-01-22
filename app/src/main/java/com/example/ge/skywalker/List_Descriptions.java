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

import java.util.List;

/**
 * Created by GE on 1/17/2018.
 */

public class List_Descriptions extends ArrayAdapter {
    private Activity context;
    List<vl_description> valueDescription;
    DatabaseReference refDescription;
    public List_Descriptions(Activity context, List<vl_description> valueDescription) {
        super(context, R.layout.ly_description,valueDescription);
        this.context = context;
        this.valueDescription = valueDescription;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.ly_description,null, true);
        TextView txtdescription = (TextView)ListViewItem.findViewById(R.id.txtdescription);
        vl_description vlDescription = valueDescription.get(position);
        txtdescription.setText(vlDescription.getDescription());
        return  ListViewItem;
    }
}
