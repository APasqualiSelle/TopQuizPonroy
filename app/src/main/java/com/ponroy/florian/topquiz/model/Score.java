package com.ponroy.florian.topquiz.model;

/**
 * Created by Florian PONROY - OpenClassrooms on 30/11/17.
 */

public class Score {
    private int mScore;
    private User mUser;

    public Score(int score, User user) {
        mScore = score;
        mUser = user;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
