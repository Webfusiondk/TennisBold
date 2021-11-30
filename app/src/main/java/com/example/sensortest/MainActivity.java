package com.example.sensortest;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sm;
    ImageView ball;
    List list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize screen components
        ball = (ImageView) findViewById(R.id.Ball);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
    }

    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = ball.getX() - values[0] * 4;
            float y = ball.getY() + values[1] * 4;
            if (CheckForOutOfBounds(x,y) == false){
                ball.setX(x);
                ball.setY(y);
            }
        }
    };


    //Checks if the ball is getting out of the screen.
    boolean CheckForOutOfBounds(float x, float y){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;


        if(x > 0 - ball.getMeasuredWidth() / 2  && (x + ball.getMeasuredWidth()) / 2 < dpWidth * 2 &&
                y > 0 - ball.getMeasuredHeight() / 2  && (y - ball.getMeasuredHeight() / 2) < dpHeight * 2){
        return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }
}