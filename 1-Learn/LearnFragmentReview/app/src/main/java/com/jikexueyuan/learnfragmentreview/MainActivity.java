package com.jikexueyuan.learnfragmentreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()  //此句呈现了PlaceholderFragment
                    .add(R.id.container, new AnotherFragment())
                    .commit();
        }

    }

}
