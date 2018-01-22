package com.example.ge.skywalker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by GE on 1/20/2018.
 */

public class coursetypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public coursetypeHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindCourseType(vl_coursetype restaurant) {

        TextView nameTextView = (TextView) mView.findViewById(R.id.txtcoursetypenamefor);
        nameTextView.setText(restaurant.getCoursetypename());
       // categoryTextView.setText(restaurant.getCategories().get(0));
       // ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
    }
    @Override
    public void onClick(View v) {

    }
}
