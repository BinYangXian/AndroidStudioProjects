package com.jikexueyuan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnotherActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private Button confirm,cancel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        editText= (EditText) findViewById(R.id.editText);
        confirm= (Button) findViewById(R.id.confirm);
        cancel= (Button) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        intent=new Intent();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                System.out.println("confirm");
                intent.putExtra("data", editText.getText().toString());
                setResult(1, intent);
                finish();
                break;
            case R.id.cancel:
                System.out.println("cancel");
            setResult(0);
                finish();
                break;
        }
    }
}
