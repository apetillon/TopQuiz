package com.petillon.alexandre.topquiz.model;

/**
 * Created by Alexandre Pétillon on 18/06/2018.
 */
public class User {
    private String mFirstname;
    private int mScore = 0;

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFirstname='" + mFirstname + '\'' +
                '}';
    }
}
