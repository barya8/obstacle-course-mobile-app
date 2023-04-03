package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.example.hw1.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private int life=3;
    private int errors=0;
    private MaterialButton[] main_BTN_arrows;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_player;
    private ShapeableImageView[][] main_IMG_obstacle;
    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (life!=0) {
                handler.postDelayed(this, 1000); //Do it again in a second
                newObstacle();
                nextStep();
                errorHandler();
            }
            else{
                finish();
                openGameOverScreen("Game Over");
            }
        }
    };

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameBeginingView();
//        gameManager = new GameManager(main_IMG_hearts.length);
//        refreshUI();
        setArrowButtonListener();
        handler.postDelayed(runnable,0);
    }

    //connect the layout to view
     private void findViews() {
        main_BTN_arrows=new MaterialButton[]{
                findViewById(R.id.main_BTN_left),
                findViewById(R.id.main_BTN_right)};
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        main_IMG_player = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_player_left),
                findViewById(R.id.main_IMG_player_center),
                findViewById(R.id.main_IMG_player_right)};
        main_IMG_obstacle = new ShapeableImageView[][]{
                {
                    findViewById(R.id.main_IMG_obstacle1_left),
                    findViewById(R.id.main_IMG_obstacle1_center),
                    findViewById(R.id.main_IMG_obstacle1_right)
                },
                {
                    findViewById(R.id.main_IMG_obstacle2_left),
                    findViewById(R.id.main_IMG_obstacle2_center),
                    findViewById(R.id.main_IMG_obstacle2_right)
                },
                {
                    findViewById(R.id.main_IMG_obstacle3_left),
                    findViewById(R.id.main_IMG_obstacle3_center),
                    findViewById(R.id.main_IMG_obstacle3_right)
                },
                {
                    findViewById(R.id.main_IMG_obstacle4_left),
                    findViewById(R.id.main_IMG_obstacle4_center),
                    findViewById(R.id.main_IMG_obstacle4_right)
                },
                {
                    findViewById(R.id.main_IMG_obstacle5_left),
                    findViewById(R.id.main_IMG_obstacle5_center),
                    findViewById(R.id.main_IMG_obstacle5_right)
                }
        };
}

    // set the view in the begining of the game
    private void gameBeginingView(){
        main_IMG_player[0].setVisibility(View.INVISIBLE);// hide left player icon
        main_IMG_player[2].setVisibility(View.INVISIBLE);// hide right player icon
        //hide obstacles
        for (int i = 0; i < main_IMG_obstacle.length; i++) {
            for (int j = 0; j < main_IMG_obstacle[i].length; j++) {
                main_IMG_obstacle[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    //click on arrow button
    private void setArrowButtonListener() {
        for (MaterialButton mb: main_BTN_arrows)
            mb.setOnClickListener(v -> clicked(mb.getId()));
    }

    //update visiblity of player after clicking
    private void clicked(int selectedButton) {
        int index=999;//default
        for (int i = 0; i < main_IMG_player.length; i++)
            if (main_IMG_player[i].getVisibility() == View.VISIBLE) {
                index = i;
            }
        if (selectedButton==2131230959) {//id of left button
            if (index == 0) {
                main_IMG_player[0].setVisibility(View.INVISIBLE);
                main_IMG_player[2].setVisibility(View.VISIBLE);
            } else {
                main_IMG_player[index].setVisibility(View.INVISIBLE);
                main_IMG_player[index - 1].setVisibility(View.VISIBLE);
            }
        }
        else {
            if (index == 2) {
                main_IMG_player[2].setVisibility(View.INVISIBLE);
                main_IMG_player[0].setVisibility(View.VISIBLE);
            } else {
                main_IMG_player[index].setVisibility(View.INVISIBLE);
                main_IMG_player[index + 1].setVisibility(View.VISIBLE);
            }
        }
    }

    //add new obstacle
    private void newObstacle(){
        int col=(int) (Math.random()*3);
        main_IMG_obstacle[0][col].setVisibility(View.VISIBLE);
    }

    //the icon go down to next row
    private void nextStep(){
        for (int i = main_IMG_obstacle.length-1; i >0; i--) {
            for (int j = 0; j < main_IMG_obstacle[i].length; j++) {
                if (main_IMG_obstacle[i-1][j].getVisibility() == View.VISIBLE) {
                    main_IMG_obstacle[i][j].setVisibility(View.VISIBLE);
                    main_IMG_obstacle[i-1][j].setVisibility(View.INVISIBLE);
                }
                else {
                    main_IMG_obstacle[i][j].setVisibility(View.INVISIBLE);
                    main_IMG_obstacle[i-1][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    //handle error- vibrate, toast, remove heart
    private void errorHandler(){
        for (int i = 0; i < main_IMG_player.length; i++) {
            if (main_IMG_player[i].getVisibility()==View.VISIBLE) {
                if (main_IMG_player[i].getVisibility()==main_IMG_obstacle[main_IMG_obstacle.length - 1][i].getVisibility()) {
                    if (life != 0) {
                        errors++;
                        life--;
                        main_IMG_hearts[main_IMG_hearts.length - errors].setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "😵 Ouch!", Toast.LENGTH_LONG).show();
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(500);
                        }
                    }
                }
            }
        }
    }
    private void openGameOverScreen(String status) {
        Intent intent = new Intent(this,GameOverActivity.class);
        intent.putExtra(GameOverActivity.KEY_STATUS,status);
        startActivity(intent);
        finish();
    }
}