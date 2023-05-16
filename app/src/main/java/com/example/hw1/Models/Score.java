package com.example.hw1.Models;

public class Score {
    private int score;
    public Score() {}

    public Score(int score) {
        this.score=score;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score)
    {
        this.score = score;
        return this;
    }

    public String toString() {
        return "" + score;
    }
}
