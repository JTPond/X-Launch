package com.example.jpond.x_launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jpond.x_launch.Launch1.launch1Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    private static TreeMap<String,String> progress= new TreeMap<String,String>();
    private static Boolean current; //true=up-to-date,false=needs-to-be-saved
    //Button mLaunch1;
    //Button mLaunch2;
    ArrayList<View> mbuttons = new ArrayList<>();


    public void gotoLaunch1(View view)
    {
        Intent intent = new Intent(MainActivity.this, launch1Activity.class);
        startActivity(intent);
    }

    public void gotoLaunch2(View view)
    {
        //Intent intent = new Intent(MainActivity.this, launch2Activity.class);
        //startActivity(intent);
    }

    public void gotoLaunch3(View view)
    {
        //Intent intent = new Intent(MainActivity.this, launch3Activity.class);
        //startActivity(intent);
    }

    public static void update_progress(String launch, String state){
        progress.put(launch, state);
        current = false;
        progress.put(progress.higherKey(launch),"next");
    }

    public static void completed(String launch){
        progress.put(launch,"completed");
        progress.put(progress.lowerKey(launch),"next");
        current = false;
    }

    public void save_progress(){
        try {
            FileOutputStream fos = openFileOutput("progress.txt", Context.MODE_PRIVATE);
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
            if (file.getName().equals("progress.txt")) found = true;
        }
        if (!found) {
            try {
                FileOutputStream fos = openFileOutput("progress.txt", Context.MODE_PRIVATE);
                fos.write("launch1,next;".getBytes());
                for (Integer i = 1; i <= mbuttons.size(); i++) {
                    fos.write(("launch" + (i + 1) + ",incomplete;").getBytes());
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
        setContentView(R.layout.activity_main);
        //mLaunch1 = (Button) findViewById(R.id.launch1_main);
        //mLaunch2 = (Button) findViewById(R.id.launch2_main);
        mbuttons = (findViewById(R.id.mainLay)).getTouchables();
        this.initial();
        try {
            FileInputStream fis = openFileInput("progress.txt");
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
                    if (SDK_INT >= 23) b.setBackgroundColor(getResources().getColor(R.color.incomplete,this.getTheme()));
                    else if (SDK_INT < 23) b.setBackgroundColor(getResources().getColor(R.color.incomplete));

                }
                else if (progress.get(b.getTag().toString()).equals("complete") ){
                    if (SDK_INT >= 23) b.setBackgroundColor(getResources().getColor(R.color.complete,this.getTheme()));
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
