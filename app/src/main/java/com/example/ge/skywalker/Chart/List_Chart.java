package com.example.ge.skywalker.Chart;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ge.skywalker.R;

import java.util.List;

/**
 * Created by GE on 1/25/2018.
 */

public class List_Chart extends ArrayAdapter {
    private Activity context;
    List<vl_score> valueScore;

    public List_Chart(Activity context, List<vl_score> valueScore) {
        super(context, R.layout.ly_chart,valueScore);
        this.context = context;
        this.valueScore = valueScore;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.ly_chart,null, true);

        TextView txtchartusername =(TextView)ListViewItem.findViewById(R.id.txtchartusername);
        final TextView txtchartscore = (TextView)ListViewItem.findViewById(R.id.txtchartscore);

        vl_score vlScore = valueScore.get(position);
        txtchartscore.setText( vlScore.getScore().toString()+ " ຄະແນນ");
        txtchartusername.setText(vlScore.getUsername().toString());
        return ListViewItem;
    }
}
