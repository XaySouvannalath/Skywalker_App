package com.example.ge.skywalker;

/**
 * Created by GE on 12/22/2017.
 */

class CourseTypes {
    private String coursetypeid, coursetypename;
    public CourseTypes(String coursetypeid, String coursetypename) {
        this.coursetypeid = coursetypeid;
        this.coursetypename = coursetypename;
    }

    public String getCoursetypeid() {
        return coursetypeid;
    }

    public String getCoursetypename() {
        return coursetypename;
    }

    @Override
    public String toString() {
        return "CourseTypes{" +
                "coursetypename='" + coursetypename + '\'' +
                '}';
    }
}
