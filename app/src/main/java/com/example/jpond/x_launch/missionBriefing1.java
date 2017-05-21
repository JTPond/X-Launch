package com.example.jpond.x_launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jpond on 5/21/17.
 */

public class missionBriefing1 extends AppCompatActivity {


    public void sendMessage(View view)
    {
        Intent intent = new Intent(missionBriefing1.this, Run_mission1_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_briefing1);


    }
}
