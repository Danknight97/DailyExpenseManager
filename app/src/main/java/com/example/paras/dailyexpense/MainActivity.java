package com.example.paras.dailyexpense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent x;
    Button a,h;
    static String Filename = "data.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a=(Button)findViewById(R.id.button);
        h=(Button)findViewById(R.id.button2);
        a.setOnClickListener(this);
        h.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==a){
            x = new Intent(this,Add.class);
            startActivity(x);
        }
        else if(view == h){
            x = new Intent(this,History.class);
            startActivity(x);
        }
    }
}
