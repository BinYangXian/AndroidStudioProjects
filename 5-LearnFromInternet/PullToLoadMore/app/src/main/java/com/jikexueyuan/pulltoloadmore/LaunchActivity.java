package com.jikexueyuan.pulltoloadmore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        if(savedInstanceState==null){
            getFragmentManager().beginTransaction()  //此句呈现了PlaceholderFragment
                    .add(R.id.container, new MainActivity())
                    .commit();
        }
    }
}
