package com.example.ge.skywalker;

/**
 * Created by GE on 12/31/2017.
 */

class TestVal {
    String testId, name, lastname;

    public TestVal() {

    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTestId() {
        return testId;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public TestVal(String testId, String name, String lastname) {
        this.testId = testId;
        this.name = name;
        this.lastname = lastname;
    }
}
