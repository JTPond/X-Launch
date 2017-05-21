package com.example.jpond.x_launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Run_mission1_Activity extends AppCompatActivity {

    static TextView mOut;
    Button mbrief;
    Button mcontrols;
    Button mignition;
    public static Double lunarSpot;
    public static Double dateMin;


    public void sendMessage(View view)
    {
        Intent intent = new Intent(Run_mission1_Activity.this, Mission1_controls_Activity.class);
        startActivity(intent);
    }

    public void sendMessage2(View view)
    {
        Intent intent = new Intent(Run_mission1_Activity.this, missionBriefing1.class);
        startActivity(intent);
    }

    public Integer run(){
        if (dateMin < 540.0) return 0;
        else if (dateMin > 1140.0) return 1;
        Double e2m = 38880.0/1440.0;
        if (dateMin*e2m - lunarSpot > 3111) return 2;
        else if (dateMin*e2m - lunarSpot < 3107) return 3;
        return 4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_mission1);
        mOut = (TextView) findViewById(R.id.mission1_text);
        mbrief = (Button) findViewById(R.id.mission1_brief);
        mcontrols = (Button) findViewById(R.id.mission1_controls);
        mignition = (Button) findViewById(R.id.mission1_ignition);

        mignition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOut.setText("");
                Integer result = run();
                if (result == 0){
                    mOut.setText(R.string.mission1_result1);
                }
                else if (result == 1){
                    mOut.setText(R.string.mission1_result2);
                }
                else if (result == 2){
                    mOut.setText(R.string.mission1_result3);
                }
                else if (result == 3){
                    mOut.setText(R.string.mission1_result4);
                }
                else if (result == 4){
                    mOut.setText(R.string.mission1_result5);
                    //MainActivity.completed("mission1");
                    //Intent intent = new Intent(Run_mission1_Activity.this, MainActivity.class);
                    //startActivity(intent);
                }
            }
        });
    }
}