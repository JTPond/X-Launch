package com.example.jpond.x_launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;


/**
 * Created by jpond on 5/19/17.
 */

public class AddTimeAction extends AppCompatActivity{

    EditText mTT;
    RadioGroup rg;
    RadioButton checked;
    Button submit;
    SeekBar throttle;

    public void addListenerOnButton() {

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        submit = (Button) findViewById(R.id.button3);
        mTT = (EditText) findViewById(R.id.editText);
        throttle = (SeekBar) findViewById(R.id.seekBar);

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = rg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                checked = (RadioButton) findViewById(selectedId);

                Double thrVal = (throttle.getProgress())/10.0;

                Run_mission2_Activity.actions.put(Double.valueOf(mTT.getText().toString()),checked.getTag().toString()+":"+thrVal);


                Intent intent = new Intent(AddTimeAction.this, Run_mission2_Activity.class);
                startActivity(intent);

            }

        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timeaction);

        addListenerOnButton();

    }

}
