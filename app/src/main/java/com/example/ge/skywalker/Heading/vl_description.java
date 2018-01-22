package com.example.ge.skywalker.Heading;

/**
 * Created by GE on 1/17/2018.
 */

public class vl_description {
    String descriptionId, description;

    public vl_description(String descriptionId, String description) {
        this.descriptionId = descriptionId;
        this.description = description;
    }

    public vl_description() {
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public String getDescription() {
        return description;
    }
}
