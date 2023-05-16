package com.example.hw1;

import android.app.Application;

import com.example.hw1.Utilities.MySPv3;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySPv3.init(this);
    }
}
