package com.mncomunity1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mncomunity1.R;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {


    Button btn, btn1;
    int count;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "myprefTest";

    List<Integer> myList = new ArrayList<>();

    int[] intArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        btn = (Button) findViewById(R.id.btn);
        btn1 = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Log.e("bbbb",count+"");
                myList.add(count);
                intArray = toIntArray(myList);
                saveArray(intArray);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("bbbb", sharedpreferences.getInt("myArrayKey", 0) + "");
            }
        });

    }

    public int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e.intValue();
        return ret;
    }

    public void saveArray(int[] value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        for (int i = 0; i < value.length; i++) {
            Log.e("aaaaa",value[i]+"");
            editor.putInt("myArrayKey", value[i]);
        }
        editor.commit();
    }

    public int[] loadArray() {
        int[] value = new int[0 + 1];
        Log.e("bbbb", sharedpreferences.getInt("myArrayKey", 0) + "");

        for (int i = 0; i < value.length + 1; i++) {
            String myString = "myArrayKey" + i;
            value[i] = sharedpreferences.getInt(myString, 0);
        }
        return value;
    }

}
