package com.example.jpond.x_launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import org.jblas.DoubleMatrix;

import java.util.TreeMap;

import missions.AroundtheMoon;

public class Run_mission1_Activity extends AppCompatActivity {

    static TextView mOut;
    EditText mTime;
    Button mIgnitionButton;

    Double dt = 0.1;
    AroundtheMoon atms = new AroundtheMoon();
    public static TreeMap<Double,String> actions= new TreeMap<Double,String>();

    public void run(Double time){
        Integer count = 0;
        atms.getSatV().getBooster("Booster1").ignition();
        for (long j = 0; j < time/dt; j++) {
            if (!actions.isEmpty()){
                if (j*dt == actions.firstKey()) {
                    atms.getAction(actions.get(actions.firstKey()).toString());
                    mOut.append("At " + j * dt + " seconds " + actions.firstEntry().toString() + "\n");
                    actions.remove(actions.firstKey());
                }
            }
            if (atms.timeStep(dt) == "empty" && count ==0){
                mOut.append("Empty at "+j*dt+" seconds!\n");
                count++;
            }
        }
        mOut.append(atms.getSatV().getPosition().toString());
    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(Run_mission1_Activity.this, AddTimeAction.class);
        startActivity(intent);
    }

    public void sendMessage2(View view)
    {
        Intent intent = new Intent(Run_mission1_Activity.this, missionBriefing.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_mission1);
        mOut = (TextView) findViewById(R.id.positionText);
        mTime = (EditText) findViewById(R.id.timeText);
        mIgnitionButton = (Button) findViewById(R.id.ignitionButton);

        mIgnitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOut.setText("");
                Double time = Double.valueOf(mTime.getText().toString());
                run(time);

            }
        });
    }
}