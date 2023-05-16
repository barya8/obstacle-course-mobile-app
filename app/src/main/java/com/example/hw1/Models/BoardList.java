package com.example.hw1.Models;

import java.util.ArrayList;

public class BoardList {

    private ArrayList<Board> boardArrayList = new ArrayList<>();

    public BoardList(){

    }

    public ArrayList<Board> getBoardArrayList() {
        return boardArrayList;
    }

    public String toString() {
        return "BoardList{" +
                "boardArrayList=" + boardArrayList +
                '}';
    }
}
