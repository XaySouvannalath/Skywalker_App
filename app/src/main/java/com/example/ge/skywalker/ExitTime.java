package com.example.ge.skywalker;

/**
 * Created by GE on 12/31/2017.
 */

class ExitTime {
    String exittimeId, exitTime;
    public ExitTime(String exittimeId, String exitTime) {
        this.exitTime = exitTime;
        this.exittimeId = exittimeId;
    }

    public String getExittimeId() {
        return exittimeId;
    }

    public String getExitTime() {
        return exitTime;
    }
}
