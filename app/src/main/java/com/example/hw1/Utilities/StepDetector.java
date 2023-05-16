package com.example.hw1.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.hw1.Interfaces.StepCallback;

public class StepDetector {
    private Sensor sensor;
    private SensorManager sensorManager;

    private StepCallback stepCallback;

    private long timestamp = 0;

    private int direction=3;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 6.0) {
                direction=1;
                stepCallback.stepX();
            }
            if (x < -6.0) {
                direction=2;
                stepCallback.stepX();
            }
            if (y > 6.0) {
                stepCallback.stepY();
            }
            if (y < -6.0) {
                stepCallback.stepY();
            }
        }
    }

    public int getDirection() {
        return this.direction;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}

