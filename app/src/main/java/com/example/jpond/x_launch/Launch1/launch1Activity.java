package com.example.jpond.x_launch.Launch1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jpond.x_launch.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

import static android.os.Build.VERSION.SDK_INT;

public class launch1Activity extends AppCompatActivity {

    private static TreeMap<String,String> progress= new TreeMap<String,String>();
    private static Boolean current; //true=up-to-date,false=needs-to-be-saved
    ArrayList<View> mbuttons = new ArrayList<>();


    public void gotoMission1(View view)
    {
        Intent intent = new Intent(launch1Activity.this, Run_mission1_Activity.class);
        startActivity(intent);
    }

    public void gotoMission2(View view)
    {
        Intent intent = new Intent(launch1Activity.this, Run_mission2_Activity.class);
        startActivity(intent);
    }

    public static void update_progress(String mission, String state){
        progress.put(mission, state);
        current = false;
        progress.put(progress.higherKey(mission),"next");
    }

    public static void completed(String mission){
        progress.put(mission,"completed");
        if (!(progress.lastKey().equals(mission))) {
            progress.put(progress.higherKey(mission), "next");
        }
        current = false;
    }

    public void save_progress(){
        try {
            FileOutputStream fos = openFileOutput("progress_L1.txt", Context.MODE_PRIVATE);
            for (String key:progress.keySet()) {
                String line = key+","+progress.get(key)+";";
                fos.write(line.getBytes());
            }
            fos.close();
            current=true;
        } catch (java.io.IOException e){
            e.printStackTrace();
        }
    }

    public void initial() {
        Boolean found = false;
        for (File file:getFilesDir().listFiles()){
            if (file.getName().equals("progress_L1.txt")) found = true;
        }
        if (!found) {
            try {
                FileOutputStream fos = openFileOutput("progress_L1.txt", Context.MODE_PRIVATE);
                fos.write("mission1,next;".getBytes());
                for (Integer i = 1; i <= mbuttons.size(); i++) {
                    fos.write(("mission" + (i + 1) + ",incomplete;").getBytes());
                }
                fos.close();
                current = false;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch1);
        mbuttons = (findViewById(R.id.launch1Lay)).getTouchables();
        this.initial();
        try {
            FileInputStream fis = openFileInput("progress_L1.txt");
            Scanner s = new Scanner(fis);
            s.useDelimiter(";");
            while (s.hasNext()){
                String line = s.next();
                try {
                    String[] cells = line.split(",");
                    progress.put(cells[0], cells[1]);
                }catch (PatternSyntaxException e){

                }
            }
            fis.close();
            current=true;
            for (View b:mbuttons) {
                if (progress.get(b.getTag().toString()).equals("incomplete") ) {
                    b.setClickable(false);
                    if (SDK_INT >= 23) b.setBackgroundColor(getResources().getColor(R.color.incomplete,getTheme()));
                    else if (SDK_INT < 23) b.setBackgroundColor(getResources().getColor(R.color.incomplete));

                }
                else if (progress.get(b.getTag().toString()).equals("completed") ){
                    if (SDK_INT >= 23) b.setBackgroundColor(getResources().getColor(R.color.complete,getTheme()));
                    else if (SDK_INT < 23) b.setBackgroundColor(getResources().getColor(R.color.complete));

                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    protected void onSaveInstanceState(Bundle outState){
        if (current == false){
            this.save_progress();
        }
    }
}
