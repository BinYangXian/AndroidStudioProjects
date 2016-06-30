package com.jikexueyuan.sendargs2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);
        findViewById(R.id.btnStartAty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, TheAty.class);
//                i.putExtra("date","Hello yanzi,are you crazy?");
//                Bundle b=new Bundle();
//                b.putString("name","yanzi");
//                b.putInt("age",2);
//                b.putString("name1","认错人了");
////                i.putExtras(b);
//                i.putExtra("information",b);
                i.putExtra("user", new User("yanzi", 2));
//                startActivity(i);
                startActivityForResult(i,0);
            }


        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        textView.setText("另一个Activity返回的数据是"+data.getStringExtra("date"));
    }
}
