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

public class List_Description_For_Client extends ArrayAdapter {
    private Activity context;
    List<vl_description> valueDescription;
    DatabaseReference refDescription;
    public List_Description_For_Client(Activity context, List<vl_description> valueDescription) {
        super(context, R.layout.layout_description_client,valueDescription);
        this.context = context;
        this.valueDescription = valueDescription;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.layout_description_client,null, true);
        TextView txtdescriptionfor = (TextView)ListViewItem.findViewById(R.id.txtdescriptionfor);
        vl_description vlDescription = valueDescription.get(position);
        txtdescriptionfor.setText(vlDescription.getDescription());
        return  ListViewItem;
    }
}
