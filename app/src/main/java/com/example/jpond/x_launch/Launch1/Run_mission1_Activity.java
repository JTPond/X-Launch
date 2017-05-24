package com.example.jpond.x_launch.Launch1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jpond.x_launch.R;

import java.io.File;
import java.io.FileOutputStream;

import static android.os.Build.VERSION.SDK_INT;

public class Run_mission1_Activity extends AppCompatActivity {

    static TextView mOut;
    Button mbrief;
    Button mcontrols;
    Button mignition;
    Button mNext;
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

    public void next(View view)
    {
        Boolean found = false;
        for (File file:getFilesDir().listFiles()){
            if (file.getName().equals("progress_L1.txt")) found = true;
        }
        if (found) {
            try {
                FileOutputStream fos = openFileOutput("progress_L1.txt", Context.MODE_PRIVATE);
                for (String key:launch1Activity.progress.keySet()){
                    fos.write((key+","+launch1Activity.progress.get(key)+";").getBytes());
                }
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(Run_mission1_Activity.this, launch1Activity.class);
        startActivity(intent);
    }
    public Integer run(){
        if (dateMin < 540.0) return 0;
        else if (dateMin > 1140.0) return 1;
        Double e2m = 38880.0/1440.0;
        if (dateMin*e2m - lunarSpot > 3136) return 2;
        else if (dateMin*e2m - lunarSpot < 3082) return 3;
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
        mNext = (Button) findViewById(R.id.mission1_next);
        mNext.setClickable(false);

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
                    launch1Activity.completed("mission1");
                    if (SDK_INT >= 23) mNext.setTextColor(getResources().getColor(R.color.backColor,getTheme()));
                    else if (SDK_INT < 23) mNext.setTextColor(getResources().getColor(R.color.backColor));
                    mNext.setClickable(true);
                    //Intent intent = new Intent(Run_mission1_Activity.this, launch1Activity.class);
                    //startActivity(intent);
                }
            }
        });
    }
}