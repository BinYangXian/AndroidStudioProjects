package com.jikexueyuan.learcomponts;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class ChooseTime extends AppCompatActivity {
    private Button chooseTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        chooseTime= (Button) findViewById(R.id.btnChooseTime);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ChooseTime.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String theTime=String.format("%02d:%02d",hourOfDay,minute);
                        System.out.println(theTime);
                        chooseTime.setText(theTime);
                    }
                },0,0,true).show();
            }
        });
    }
}
