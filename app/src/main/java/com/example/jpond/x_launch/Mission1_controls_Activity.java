package com.example.jpond.x_launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by jpond on 5/19/17.
 */

public class Mission1_controls_Activity extends AppCompatActivity{

    SeekBar lunarSpot;
    TimePicker timePick;
    Button submit;
    TextView tv1;

    public void addListenerOnButton() {


        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Run_mission1_Activity.lunarSpot= (double) lunarSpot.getProgress();
                if (SDK_INT < 23) Run_mission1_Activity.dateMin = (double) ((timePick.getCurrentHour()*60 + timePick.getCurrentMinute()));
                else if (SDK_INT >= 23) Run_mission1_Activity.dateMin = (double) ((timePick.getHour()*60 + timePick.getMinute()));

                Intent intent = new Intent(Mission1_controls_Activity.this, Run_mission1_Activity.class);
                startActivity(intent);

            }

        });
        lunarSpot.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv1.setText(Integer.toString(lunarSpot.getProgress()));
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls1);
        lunarSpot = (SeekBar) findViewById(R.id.mission1_moonDate);
        timePick = (TimePicker) findViewById(R.id.mission1_LaunchTime);
        submit = (Button) findViewById(R.id.mission1_submit);
        tv1 = (TextView) findViewById(R.id.mission1_controls_text1);
        timePick.setIs24HourView(true);

        addListenerOnButton();

    }

}
