package com.example.ge.skywalker.Chart;

/**
 * Created by GE on 1/24/2018.
 */

public class vl_score {
    String scoreId, username;
    Integer score;

    public vl_score( String username, Integer score) {
        this.username = username;
        this.score = score;
    }
    public String getUsername() {
        return username;
    }
    public Integer getScore() {
        return score;
    }

    public vl_score() {
    }
}
