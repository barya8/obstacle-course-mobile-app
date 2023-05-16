package com.example.hw1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.button.MaterialButton;

public class MenuModeActivity extends Activity {
    private MaterialButton[] menu_BTN_mode;
    private static int chosenMode =999; //default

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mode);

        menu_BTN_mode = new MaterialButton[]{
                findViewById(R.id.menu_BTN_buttons),
                findViewById(R.id.menu_BTN_tilt),
                findViewById(R.id.menu_BTN_leaderboard)};
        setModeButtonListener();
    }

    private void setModeButtonListener() {
        for (MaterialButton mb: menu_BTN_mode){
            mb.setOnClickListener(v -> clicked(mb.getId()));}
    }

    private void clicked(int selectedButton) {
        if (selectedButton==2131231059) { //id of leaderboard button
            openLeaderboardActivityButtonListener();
        }
        else {
            if (selectedButton == 2131231058) { //id of buttons button
                chosenMode = 1;
            }
            else {
                chosenMode =2;
            }
            openMenuSpeedActivityScreen();
        }
    }

    public static int getChosenMode() {
        return chosenMode;
    }

    private void openMenuSpeedActivityScreen() {
        Intent intent = new Intent(this,MenuSpeedActivity.class);
        startActivity(intent);
        finish();
    }

    private void openLeaderboardActivityButtonListener() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}