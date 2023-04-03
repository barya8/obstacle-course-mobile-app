package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textview.MaterialTextView;

public class GameOverActivity extends AppCompatActivity {

    public static final String KEY_STATUS = "KEY_STATUS";
    private MaterialTextView game_over_LBL_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        game_over_LBL_go = findViewById(R.id.game_over_LBL_go);
        Intent previousIntent = getIntent();
        String status = previousIntent.getStringExtra(KEY_STATUS);
        game_over_LBL_go.setText(status);
    }
}
