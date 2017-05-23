package com.example.jpond.x_launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

/**
 * Created by jpond on 5/21/17.
 */

public class missionBriefing extends AppCompatActivity {


    public void sendMessage(View view)
    {
        Intent intent = new Intent(missionBriefing.this, Run_mission1_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_briefing);


    }
}
