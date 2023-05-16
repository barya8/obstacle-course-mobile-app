package com.example.hw1.Models;

import android.util.Log;

import com.example.hw1.LeaderboardActivity;
import com.example.hw1.MainActivity;
import com.example.hw1.Utilities.DataManager;

public class Board {

    private String name = "";
    private Score score;
    private int place;

    public Board() {}
    public Board(Score score) {
        this.score=score;
    }

    public Board setName(String name) {
        this.name = name;
        return this;
    }

    public Board setScore(Score score) {
        this.score = score;
        return this;
    }

    public Board setScore(int score) {
        this.score=new Score(score);
        return this;
    }

    public Board setPlace(int place) {
        this.place = place;
        return this;
    }

    public String getName() {
        return name;
    }

    public Score getScore() {
        return score;
    }

    public int getPlace() {
        return place;
    }


    @Override
    public String toString() {
        return "Scores{" +
                "place=" + place +
                ", name='" + name + '\'' +
                ", score=" + score +'\'' +
                '}';
    }
}
