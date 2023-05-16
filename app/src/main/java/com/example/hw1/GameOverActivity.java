package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class GameOverActivity extends AppCompatActivity {

    public static final String KEY_STATUS = "KEY_STATUS";
    private MaterialTextView game_over_LBL_go;
    private MaterialButton[] menu_BTN_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        game_over_LBL_go = findViewById(R.id.game_over_LBL_go);
        menu_BTN_go = new MaterialButton[]{
                findViewById(R.id.go_BTN_new_game),
                findViewById(R.id.go_BTN_leaderboard)};
        Intent previousIntent = getIntent();
        String status = previousIntent.getStringExtra(KEY_STATUS);
        game_over_LBL_go.setText(status);
        setGameOverButtonListener();
    }

    private void setGameOverButtonListener() {
        for (MaterialButton mb: menu_BTN_go)
            mb.setOnClickListener(v -> clicked(mb.getId()));
    }

    private void clicked(int selectedButton) {
        if (selectedButton==2131230929) { //id of leaderboard button
            setLeaderboardActivityButtonListener();
        }
        else {
            setNewGameButtonListener();
        }
    }

    private void setLeaderboardActivityButtonListener() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void setNewGameButtonListener() {
        Intent intent = new Intent(this, MenuModeActivity.class);
        startActivity(intent);
        finish();
    }

}
