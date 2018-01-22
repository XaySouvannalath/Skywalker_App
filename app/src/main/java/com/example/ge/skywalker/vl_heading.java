package com.example.ge.skywalker;

/**
 * Created by GE on 1/4/2018.
 */

public class vl_heading {
    String headingId, headingName, headingImageUrl;

    public vl_heading(String headingId, String headingName ) {
        this.headingId = headingId;
        this.headingName = headingName;

    }

    public vl_heading() {
    }

    public String getHeadingId() {
        return headingId;
    }

    public String getHeadingName() {
        return headingName;
    }

    public String getHeadingImageUrl() {
        return headingImageUrl;
    }
}
