package com.jikexueyuan.learcomponts;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class ChooseADate extends AppCompatActivity {
 private Button btnChooseDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_adate);
        btnChooseDate= (Button) findViewById(R.id.btnChooseDate);
        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ChooseADate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String theDate=String.format("%04d-%02d-%02d",year,monthOfYear+1,dayOfMonth);
                        btnChooseDate.setText(theDate);
                        System.out.println(theDate);
                    }
                }, 2015, 3, 30).show();
            }
        });
    }
}
