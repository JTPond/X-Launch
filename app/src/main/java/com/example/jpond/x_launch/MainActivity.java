package com.example.jpond.x_launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private static TreeMap<String,String> progress= new TreeMap<String,String>();
    private static Boolean current; //true=up-to-date,false=needs-to-be-saved
    Button mMission1;
    Button mMission2;
    //ArrayList<View> mbuttons = new ArrayList<>();


    public void gotoMission1(View view)
    {
        Intent intent = new Intent(MainActivity.this, Run_mission1_Activity.class);
        startActivity(intent);
    }

    public void gotoMission2(View view)
    {
        Intent intent = new Intent(MainActivity.this, Run_mission2_Activity.class);
        startActivity(intent);
    }

    public static void update_progress(String mission, String state){
        progress.put(mission, state);
        current = false;
        progress.put(progress.higherKey(mission),"next");
    }

    public static void completed(String mission){
        progress.put(mission,"completed");
        progress.put(progress.lowerKey(mission),"next");
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

    /*public void initial(){
        if (fileList().length == 0){
            try {
                FileOutputStream fos = openFileOutput("progress.txt", Context.MODE_PRIVATE);
                fos.write("mission1,next;".getBytes());
                for (Integer i = 1; i < mbuttons.size(); i++) {
                    fos.write(("mission"+(i+1)+",incomplete;").getBytes());
                }
                fos.close();
                current=false;
            } catch (java.io.IOException e){
                e.printStackTrace();
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMission1 = (Button) findViewById(R.id.mission1_main);
        mMission2 = (Button) findViewById(R.id.mission2_main);
        mMission2.setClickable(false);
        //this.initial();
        /*try {
            FileInputStream fis = openFileInput("progress.txt");
            Scanner s = new Scanner(fis);
            s.useDelimiter(";");
            while (s.hasNext()){
                String line = s.next();
                try {
                    String[] cells = line.split(",");
                    progress.put(cells[0], cells[1]);
                }finally {
                    fis.close();
                    current=true;
                }
            }*/
            //mbuttons = ((LinearLayout) findViewById(R.id.mainLay)).getTouchables();
            //for (View b:mbuttons) {
            //    b = (Button) b;
                /*if (progress.get(b.getTag().toString()).equals("incomplete") ) {
                    b.setClickable(false);
                    b.setBackgroundColor(Integer.parseInt("@color/incomplete"));

                }
                else if (progress.get(b.getTag().toString()).equals("complete") ){
                    b.setBackgroundColor(Integer.parseInt("@color/complete"));

                }*/
            //}
        /*} catch (java.io.IOException e) {
            e.printStackTrace();
        }*/


    }
}
