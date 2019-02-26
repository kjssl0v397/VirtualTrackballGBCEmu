package com.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zlt.gbclibrary.R;

public class TestActivity extends AppCompatActivity {

    private VirtualTrackball virtualTrackball;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        virtualTrackball = findViewById(R.id.virtualTrackball);
    }

    public void change(View view) {
        if (virtualTrackball.isDpad())
            virtualTrackball.changeToTrackball();
        else
            virtualTrackball.changeToDpad4();
    }
}
