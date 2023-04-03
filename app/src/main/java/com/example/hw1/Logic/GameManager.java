package com.example.hw1.Logic;

import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class GameManager {
    private int life;
    private int disqualification;

    public GameManager(int life) {
        this.life = life;
        this.disqualification = 0;
    }

    public boolean isLose() {
        return life == disqualification;
    }

    public int getDisqualification() {
        return disqualification;
    }
}
