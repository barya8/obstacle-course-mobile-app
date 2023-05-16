package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MenuSpeedActivity extends AppCompatActivity {
    private MaterialButton[] menu_BTN_mode;
    private static int modeTimeDelay=5000; //default

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_speed);

        menu_BTN_mode = new MaterialButton[]{
                findViewById(R.id.menu_BTN_snail),
                findViewById(R.id.menu_BTN_bunny),
                findViewById(R.id.menu_BTN_leaderboard)};
        setModeButtonListener();
    }

    private void setModeButtonListener() {
        for (MaterialButton mb: menu_BTN_mode)
            mb.setOnClickListener(v -> clicked(mb.getId()));
    }

    private void clicked(int selectedButton) {
        if (selectedButton == 2131231059) { //id of leaderboard button
            openLeaderboardActivityButtonListener();
        }
        else{
                if (selectedButton == 2131231057) { //id of bunny button
                    modeTimeDelay = 750;
                } else {
                    modeTimeDelay = 1500;
                }
                openMainActivityScreen();
            }
        }

    public static int getModeTimeDelay() {
        return modeTimeDelay;
    }

    private void openMainActivityScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openLeaderboardActivityButtonListener() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}