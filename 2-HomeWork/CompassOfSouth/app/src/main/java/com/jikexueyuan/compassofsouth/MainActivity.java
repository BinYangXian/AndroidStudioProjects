package com.jikexueyuan.compassofsouth;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private ImageView imageView;
    private float value;
    private TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        value = event.values[0];
        imageView.setRotation(360 - value);
        textView2.setText((int)value+"°");
        if (value >= 0 && value < 23) {
            textView.setText("北");
        } else if (value >= 23 && value < 75) {
            textView.setText("东北");
        } else if (value >= 75 && value < 112) {
            textView.setText("东");
        } else if (value >= 112 && value < 158) {
            textView.setText("东南");
        } else if (value >= 158 && value < 203) {
            textView.setText("南");
        } else if (value >= 203 && value < 248) {
            textView.setText("西南");
        } else if (value >= 248 && value < 293) {
            textView.setText("西");
        } else if (value >= 293 && value < 338) {
            textView.setText("西北");
        } else if (value >= 338 && value < 359) {
            textView.setText("北");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);//参数1 可以注销所有传感器，也可以通过参数2 指定Sensor类型注销某个
    }
}
