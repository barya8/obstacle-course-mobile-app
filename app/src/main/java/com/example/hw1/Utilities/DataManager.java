package com.example.hw1.Utilities;
import android.util.Log;

import com.example.hw1.Models.Board;
import com.example.hw1.Models.BoardList;
import com.example.hw1.Models.Score;
import com.google.gson.Gson;

public class DataManager {
    public static BoardList getScores() {
            BoardList boardArrayList = new BoardList();
        String leaderBoardJson = MySPv3.getInstance().getString("Leaderboard","");
        Log.d(leaderBoardJson, "xxxxxxxx");
        if (leaderBoardJson=="")
            boardArrayList=new Gson().fromJson(leaderBoardJson, BoardList.class);
        else {
            for (int i=1; i<=20;i++) {
                boardArrayList.getBoardArrayList().add(i-1, new Board()
                        .setPlace(i)
                        .setName("empty")
                        .setScore(0)
                );
            }
        }
        return boardArrayList;
    }

    public static void saveToSP(BoardList boardArrayList)
    {
        String leaderBoardJson = new Gson().toJson(boardArrayList);
        MySPv3.getInstance().putString("Leaderboard", leaderBoardJson);
    }
}
