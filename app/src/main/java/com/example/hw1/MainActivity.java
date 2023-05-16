package com.example.hw1;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageView;
import android.media.MediaPlayer;

import com.bumptech.glide.Glide;
import com.example.hw1.Interfaces.StepCallback;
import com.example.hw1.Models.Board;
import com.example.hw1.Models.Score;
import com.example.hw1.Utilities.StepDetector;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView main_IMG_background;
    private int life = 3;//sets the number of strikes
    private int errors = 0;// set the default current number of errors
    private static Score score;
    private Board board;
    private MediaPlayer mediaPlayer;
    private StepDetector stepDetector;

    private MaterialTextView main_LBL_score;
    private final int BONUS_ADDITION_SCORE = 10;
    private MaterialButton[] main_BTN_arrows;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_player;
    private ShapeableImageView [][] main_IMG_obstacle;
    private final Handler handler = new Handler();

    //the runnable is responsible for the movement of the obstacles to the player
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (life != 0) {
                handler.postDelayed(this, MenuSpeedActivity.getModeTimeDelay()); //Do it again as mentioned in menu
//                handler.postDelayed(this,750);
                newObstacle();
                nextStep();
                errorHandler();
            } else { //open game over screen
                board= new Board(score);
                finish();
//                openGameOverScreen("Game Over");
                openEnterNameScreen();
            }
        }
    };

    //main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Glide
                .with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Soccer_Field_Transparant.svg/800px-Soccer_Field_Transparant.svg.png")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_IMG_background);
        gameBeginningView();
        score=new Score();
        score.setScore(0);
//        setArrowButtonListener();
        chosenMode();
        handler.postDelayed(runnable, 0);
    }

    //connect the layout to view
    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_BTN_arrows = new MaterialButton[]{
                findViewById(R.id.main_BTN_left),
                findViewById(R.id.main_BTN_right)};
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        main_IMG_player = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_player_left),
                findViewById(R.id.main_IMG_player_left_center),
                findViewById(R.id.main_IMG_player_center),
                findViewById(R.id.main_IMG_player_right_center),
                findViewById(R.id.main_IMG_player_right)};
        main_IMG_obstacle = new ShapeableImageView[][]{
                {
                        findViewById(R.id.main_IMG_obstacle1_left),
                        findViewById(R.id.main_IMG_obstacle1_left_center),
                        findViewById(R.id.main_IMG_obstacle1_center),
                        findViewById(R.id.main_IMG_obstacle1_right_center),
                        findViewById(R.id.main_IMG_obstacle1_right)
                },
                {
                        findViewById(R.id.main_IMG_obstacle2_left),
                        findViewById(R.id.main_IMG_obstacle2_left_center),
                        findViewById(R.id.main_IMG_obstacle2_center),
                        findViewById(R.id.main_IMG_obstacle2_right_center),
                        findViewById(R.id.main_IMG_obstacle2_right)
                },
                {
                        findViewById(R.id.main_IMG_obstacle3_left),
                        findViewById(R.id.main_IMG_obstacle3_left_center),
                        findViewById(R.id.main_IMG_obstacle3_center),
                        findViewById(R.id.main_IMG_obstacle3_right_center),
                        findViewById(R.id.main_IMG_obstacle3_right)
                },
                {
                        findViewById(R.id.main_IMG_obstacle4_left),
                        findViewById(R.id.main_IMG_obstacle4_left_center),
                        findViewById(R.id.main_IMG_obstacle4_center),
                        findViewById(R.id.main_IMG_obstacle4_right_center),
                        findViewById(R.id.main_IMG_obstacle4_right)
                },
                {
                        findViewById(R.id.main_IMG_obstacle5_left),
                        findViewById(R.id.main_IMG_obstacle5_left_center),
                        findViewById(R.id.main_IMG_obstacle5_center),
                        findViewById(R.id.main_IMG_obstacle5_right_center),
                        findViewById(R.id.main_IMG_obstacle5_right)
                },
                {
                        findViewById(R.id.main_IMG_obstacle6_left),
                        findViewById(R.id.main_IMG_obstacle6_left_center),
                        findViewById(R.id.main_IMG_obstacle6_center),
                        findViewById(R.id.main_IMG_obstacle6_right_center),
                        findViewById(R.id.main_IMG_obstacle6_right)
                }
        };
    }

    // set the view in the beginning of the game
    private void gameBeginningView() {
        main_IMG_player[0].setVisibility(View.INVISIBLE);// hide left player icon
        main_IMG_player[1].setVisibility(View.INVISIBLE);// hide left center player icon
        main_IMG_player[3].setVisibility(View.INVISIBLE);// hide right center player icon
        main_IMG_player[4].setVisibility(View.INVISIBLE);// hide right player icon
        //hide obstacles
        for (int i = 0; i < main_IMG_obstacle.length; i++) {
            for (int j = 0; j < main_IMG_obstacle[i].length; j++) {
                main_IMG_obstacle[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void chosenMode(){
        if (MenuModeActivity.getChosenMode()==1)
        {
            setArrowButtonListener();
        }
        else
        {
            main_BTN_arrows[0].setVisibility(View.INVISIBLE);
            main_BTN_arrows[1].setVisibility(View.INVISIBLE);
            initStepDetector();
        }
    }

    //click on arrow button
    private void setArrowButtonListener() {
        for (MaterialButton mb : main_BTN_arrows)
            mb.setOnClickListener(v -> clicked(mb.getId()));
    }

    //update visibility of player after clicking the arrow
    private void clicked(int selectedButton) {
        int index = 999;//default
        for (int i = 0; i < main_IMG_player.length; i++)
            if (main_IMG_player[i].getVisibility() == View.VISIBLE) {
                index = i;
            }
        if (selectedButton == 2131230973) {//id of left button
            if (index == 0) {
                main_IMG_player[0].setVisibility(View.INVISIBLE);
                main_IMG_player[4].setVisibility(View.VISIBLE);
            } else {
                main_IMG_player[index].setVisibility(View.INVISIBLE);
                main_IMG_player[index - 1].setVisibility(View.VISIBLE);
            }
        } else {
            if (index == 4) {
                main_IMG_player[4].setVisibility(View.INVISIBLE);
                main_IMG_player[0].setVisibility(View.VISIBLE);
            } else {
                main_IMG_player[index].setVisibility(View.INVISIBLE);
                main_IMG_player[index + 1].setVisibility(View.VISIBLE);
            }
        }
    }

    //add new obstacle to the game in the last row
    private void newObstacle() {
        int col = (int) (Math.random() * 9);
        if (col/4>0) {
            main_IMG_obstacle[0][col % 4].setImageResource(R.drawable.ic_icon_coin);
        }
        else{
            main_IMG_obstacle[0][col % 4].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_football_defender));
        }
            main_IMG_obstacle[0][col % 4].setVisibility(View.VISIBLE);
    }

    //the movement of the obstacles icons to the next row
    private void nextStep() {
        for (int i = main_IMG_obstacle.length - 1; i > 0; i--) {
            for (int j = 0; j < main_IMG_obstacle[i].length-1; j++) {
                if (main_IMG_obstacle[i - 1][j].getVisibility() == View.VISIBLE) {
                    main_IMG_obstacle[i][j].setVisibility(View.VISIBLE);
                    main_IMG_obstacle[i][j].setImageDrawable(main_IMG_obstacle[i-1][j].getDrawable());
                    main_IMG_obstacle[i - 1][j].setVisibility(View.INVISIBLE);
                } else {
                    main_IMG_obstacle[i][j].setVisibility(View.INVISIBLE);
                    main_IMG_obstacle[i][j].setImageDrawable(main_IMG_obstacle[i-1][j].getDrawable());
                    main_IMG_obstacle[i - 1][j].setVisibility(View.INVISIBLE);
                }
            }
        }


    }

    //handle error- vibrate, toast, remove heart icon
    private void errorHandler() {
        for (int i = 0; i < main_IMG_player.length; i++) {
            if (main_IMG_player[i].getVisibility() == View.VISIBLE) {
                if (main_IMG_player[i].getVisibility() == main_IMG_obstacle[main_IMG_obstacle.length - 1][i].getVisibility()) {
                    if (main_IMG_obstacle[main_IMG_obstacle.length - 1][i].getDrawable().getConstantState()== getResources().getDrawable(R.drawable.ic_icon_football_defender).getConstantState()) {
                        if (life != 0) {
                            errors++;
                            life--;
                            main_IMG_hearts[main_IMG_hearts.length - errors].setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "😵 Ouch!", Toast.LENGTH_LONG).show();
                            mediaPlayer.start();
                            {
                                sleep(2000);
                                mediaPlayer.pause();
                            }
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(500);
                            }
                        }
                    }
                    else {
                        score.setScore(score.getScore()+BONUS_ADDITION_SCORE);
                        main_LBL_score.setText("" + score.getScore());
                    }
                } else //not error, update the odometer
                {
                    score.setScore(score.getScore()+1);
                    main_LBL_score.setText("" + score.getScore());
                }
            }
        }
    }

    private void openEnterNameScreen() {
        Intent intent = new Intent(this, EnterNameActivity.class);
        startActivity(intent);
        finish();
    }

    public static Score getScore() {
        return score;
    }

    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepX() {
                int index = 999;//default
                for (int i = 0; i < main_IMG_player.length; i++)
                    if (main_IMG_player[i].getVisibility() == View.VISIBLE) {
                        index = i;
                    }
                if (stepDetector.getDirection() == 1) {//id of left button
                    if (index == 0) {
                        main_IMG_player[0].setVisibility(View.INVISIBLE);
                        main_IMG_player[4].setVisibility(View.VISIBLE);
                    } else {
                        main_IMG_player[index].setVisibility(View.INVISIBLE);
                        main_IMG_player[index - 1].setVisibility(View.VISIBLE);
                    }
                } else {
                    if (index == 4) {
                        main_IMG_player[4].setVisibility(View.INVISIBLE);
                        main_IMG_player[0].setVisibility(View.VISIBLE);
                    } else {
                        main_IMG_player[index].setVisibility(View.INVISIBLE);
                        main_IMG_player[index + 1].setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void stepY() {
                //pass
            }

            @Override
            public void stepZ() {
                // Pass
            }
        });
    }
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(this,R.raw.carcrashsoundeffects);
        mediaPlayer.setVolume(1.0f,1.0f);
        if (MenuModeActivity.getChosenMode()==2)
            stepDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        if (MenuModeActivity.getChosenMode()==2)
            stepDetector.stop();
    }
}