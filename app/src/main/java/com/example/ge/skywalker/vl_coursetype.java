package com.example.ge.skywalker;

/**
 * Created by GE on 1/2/2018.
 */

public class vl_coursetype {
    String coursetypename, coursetypeid;

    public vl_coursetype(String coursetypename, String coursetypeid) {
        this.coursetypename = coursetypename;
        this.coursetypeid = coursetypeid;
    }

    public vl_coursetype() {
    }

    public String getCoursetypename() {
        return coursetypename;
    }

    public String getCoursetypeid() {
        return coursetypeid;
    }

    public void setCoursetypename(String coursetypename) {
        this.coursetypename = coursetypename;
    }

    public void setCoursetypeid(String coursetypeid) {
        this.coursetypeid = coursetypeid;
    }
}
