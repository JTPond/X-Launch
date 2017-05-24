package com.example.jpond.x_launch.Launch1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.jpond.x_launch.R;

/**
 * Created by jpond on 5/21/17.
 */

public class missionBriefing1 extends AppCompatActivity {

    TextView tv1;


    public void sendMessage(View view)
    {
        Intent intent = new Intent(missionBriefing1.this, Run_mission1_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_briefing1);
        tv1 = (TextView) findViewById(R.id.briefing);
        tv1.setMovementMethod(new ScrollingMovementMethod());


    }
}
