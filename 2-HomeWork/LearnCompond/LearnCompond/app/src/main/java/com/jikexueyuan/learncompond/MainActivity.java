package com.jikexueyuan.learncompond;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnSubmit;
    private RadioButton radioButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        radioButton1= (RadioButton) findViewById(R.id.radioButton1);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton1.isChecked()){
                    Toast.makeText(MainActivity.this,"我也这样认为的",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"我不这样认为",Toast.LENGTH_SHORT).show();
                }
        }
    });
}}
