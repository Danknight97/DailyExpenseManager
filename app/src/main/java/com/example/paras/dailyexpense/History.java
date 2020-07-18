package com.example.paras.dailyexpense;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Paras on 04-07-2017.
 */

public class History extends AppCompatActivity {
    ListView l;
    ArrayList<Dbase> fetched_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        fetched_data = new ArrayList<>();
        populate_arrayLise(fetched_data);
        l=(ListView)findViewById(R.id.lviewh);
        ArrayAdapter<Dbase> al = new ArrayAdapter<Dbase>(this,R.layout.listviewlayout,R.id.price_in_view,fetched_data){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                ((TextView)view.findViewById(R.id.price_in_view)).setText("Rs."+(fetched_data.get(position)).price);
                ((TextView)view.findViewById(R.id.cat)).setText((fetched_data.get(position)).cat);
                ((TextView)view.findViewById(R.id.date)).setText((fetched_data.get(position)).date);
                ((TextView)view.findViewById(R.id.des)).setText((fetched_data.get(position)).dis);
                ((TextView)view.findViewById(R.id.paymode)).setText((fetched_data.get(position)).payment);


                return view;
            }
        };
        l.setAdapter(al);
    }

    private void populate_arrayLise(ArrayList<Dbase> fetched_data) {
        String row = "";
        try {
            File file = new File(getExternalFilesDir("data"),MainActivity.Filename);
            FileInputStream fin = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));

            String op;
            while ( (op = br.readLine()) != null ){
                //op is price$category$date$desc$paymode
                StringTokenizer st = new StringTokenizer(op,"$");
                Dbase temp ; ArrayList<String> TokenArr = new ArrayList<>();
                while (st.hasMoreTokens()){
                    TokenArr.add(st.nextToken());
                }
                temp= new Dbase(Integer.valueOf((TokenArr.get(0)).trim()),TokenArr.get(1), TokenArr.get(2),TokenArr.get(3),TokenArr.get(4));

                fetched_data.add(temp);
            }

        } catch (FileNotFoundException e) {
            Log.e("File err" ,  "reading file");
        } catch (IOException e) {
            Log.e("File err" ,  e.toString());
        }

    }

}

