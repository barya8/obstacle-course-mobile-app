package com.example.hw1.Fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw1.R;
import com.google.android.material.textview.MaterialTextView;

public class MapFragment extends Fragment {

    private MaterialTextView map_LBL_title;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        return view;
    }
    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }
}
