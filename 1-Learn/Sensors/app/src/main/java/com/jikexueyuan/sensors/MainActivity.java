package com.jikexueyuan.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);

//        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);//获取所有的传感器
//        System.out.println("所有的传感器：");
//        for (Sensor s:
//                sensorList) {
//            System.out.println(s.getName());
//        }
        //在此注册会导致程序后台运行时刻仍然占用传感器资源，所以在最下边的周期函数里注册/注销，来解决。
//        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);//获取指定传感器
//        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);//参数2 数据
//        // 传输的速率，这里是普通的速率。
//        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_PROXIMITY:  //这里只处理 TYPE_PROXIMITY 的数据，还有别的类型的传感器数据
                System.out.println(event.values[0]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                System.out.format("x=%f,y=%f,z=%f\n",event.values[0],event.values[1],event.values[2]);
                break;
            case Sensor.TYPE_ORIENTATION:
                System.out.format("value:%f\n",event.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);//获取指定传感器
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);//参数2 数据
        // 传输的速率，这里是普通的速率。
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//加速度
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);//参数1 可以注销所有传感器，也可以通过参数2 指定Sensor类型注销某个
    }
}
