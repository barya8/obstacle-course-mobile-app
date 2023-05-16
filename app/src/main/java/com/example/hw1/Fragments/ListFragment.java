package com.example.hw1.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1.Adapter.BoardAdapter;
import com.example.hw1.R;
import com.example.hw1.Utilities.DataManager;

public class ListFragment extends Fragment {

    private RecyclerView main_LST_board;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void findViews(View view) {
        main_LST_board=view.findViewById(R.id.main_LST_board);
    }
    private void initViews(View view) {
        BoardAdapter boardAdapter = new BoardAdapter(DataManager.getScores().getBoardArrayList());
        Log.d("ttt", DataManager.getScores().getBoardArrayList().toString());
        Log.d("ttt", DataManager.getScores().toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_board.setAdapter(boardAdapter);
        main_LST_board.setLayoutManager(linearLayoutManager);
    }
}