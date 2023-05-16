package com.example.hw1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.hw1.Interfaces.CallBack_SendClick;
import com.example.hw1.Models.Board;
import com.example.hw1.Models.BoardList;
import com.example.hw1.Models.Score;
import com.example.hw1.Utilities.DataManager;
import com.example.hw1.Utilities.MySPv3;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class EnterNameActivity extends Activity {
    private AppCompatEditText en_ET_name;
    private MaterialButton en_BTN_send;
    private static String name;

    private CallBack_SendClick callBack_sendClick = new CallBack_SendClick() {
        @Override
        public void userNameChosen(String name) {
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        findViews();
        en_BTN_send.setOnClickListener(v -> sendClicked());
    }

    private void sendClicked() {
        if (callBack_sendClick != null) {
            name=en_ET_name.getText().toString();
            callBack_sendClick.userNameChosen(name);
            updateLeaderBoard();
            openGameOverScreen("Game Over");
        }
    }

    private void findViews() {
        en_ET_name = findViewById(R.id.en_ET_name);
        en_BTN_send =findViewById(R.id.en_BTN_send);
    }

//    private void openMenuModeScreen() {
//        Intent intent = new Intent(this, MenuModeActivity.class);
//        startActivity(intent);
//        finish();
//    }
    private void openGameOverScreen(String status) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra(GameOverActivity.KEY_STATUS, status);
        startActivity(intent);
        finish();
    }


    public String getName() {
        return name;
    }

    public void updateLeaderBoard() {
        BoardList boardArrayList=DataManager.getScores();
        int p=boardArrayList.getBoardArrayList().size()+1;
        Board currentBoard=new Board()
                .setScore(MainActivity.getScore().getScore())
                .setName(name)
                .setPlace(p);
        Log.d(currentBoard.toString(),"aaa");
        boardArrayList.getBoardArrayList().set(0,currentBoard);
        String leaderBoardJson = new Gson().toJson(boardArrayList);
        Log.d(leaderBoardJson, "ddd");
        MySPv3.getInstance().putString("Leaderboard", leaderBoardJson);
//        DataManager.saveToSP(boardArrayList);
//        Board.updateNewScore(name);
    }

}
