package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.hw1.Adapter.BoardAdapter;
import com.example.hw1.Fragments.ListFragment;
import com.example.hw1.Fragments.MapFragment;
import com.example.hw1.Interfaces.CallBack_SendClick;
import com.example.hw1.Models.Board;
import com.example.hw1.Utilities.DataManager;

public class LeaderboardActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        initFragments();
        beginTransactions();
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }

    private void initFragments() {
        listFragment = new ListFragment();
        mapFragment = new MapFragment();

    }
}