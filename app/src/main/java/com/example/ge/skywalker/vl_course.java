package com.example.ge.skywalker;

/**
 * Created by GE on 1/3/2018.
 */

public class vl_course {
    String courseId, courseName;

    public vl_course() {
    }

    public vl_course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
