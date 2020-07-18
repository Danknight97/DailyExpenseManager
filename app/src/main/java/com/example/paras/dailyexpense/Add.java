package com.example.paras.dailyexpense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 * Created by Paras on 04-07-2017.
 */

public class Add extends AppCompatActivity implements View.OnClickListener{
    Button b,bdate;
    int month_x,day_x,year_x;
    static final int Dialog_ID=0;
    Spinner s,s1;
    EditText pr,desc;
    String cat_selection, Payment_selection;
    ArrayAdapter adapter,arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        s=(Spinner)findViewById(R.id.spinner);
        s1=(Spinner)findViewById(R.id.spinner2);
        b=(Button)findViewById(R.id.button3);
        bdate=(Button)findViewById(R.id.bdate);
        pr=(EditText)findViewById(R.id.editText);
        desc=(EditText)findViewById(R.id.editText4);
        b.setOnClickListener(this);
        bdate.setOnClickListener(this);
        final Calendar cal = Calendar.getInstance();
        year_x=cal.get(Calendar.DAY_OF_MONTH);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.YEAR);
        adapter = ArrayAdapter.createFromResource(Add.this,R.array.Category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        arrayAdapter = ArrayAdapter.createFromResource(Add.this,R.array.PaymentMode,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(arrayAdapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat_selection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Payment_selection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        showDialogOnButtonClick();

    }
    public void showDialogOnButtonClick(){
        bdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(Dialog_ID);
                    }
                }
        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if( id == Dialog_ID)
            return new DatePickerDialog(this,dpickerlistener,day_x,month_x,year_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerlistener
            =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
       day_x = i;
            month_x = i1+1;
            year_x = i2;
        }
    };
    @Override
    public void onClick(View view) {
        if(view == b){

            if (pr.getText().toString().equals("") || desc.getText().toString().equals(""))Toast.makeText(this,"Please enter values!", Toast.LENGTH_SHORT).show();
            else {
                int e1 = Integer.valueOf(pr.getText().toString());
                String e2 = cat_selection;
                String e3 = year_x + "/" + month_x + "/" + day_x;
                String e4 = desc.getText().toString();
                String e5 = Payment_selection;

                Dbase temp = new Dbase(e1, e2, e3, e4, e5);
                addDataToFile(temp);
            }

        }
    }

    void addDataToFile(Dbase temp){
        File file = new File(getExternalFilesDir("data"),MainActivity.Filename);
        String row;
        StringBuilder sb = new StringBuilder();
        row = String.valueOf(temp.price) + "$"+ (temp.cat) + "$"
                + (temp.date) + "$" + (temp.dis) + "$" + (temp.payment) + "\n";

        String already =  getFileToString(file);

        sb.append(row);
        sb.append(already);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(sb.toString());
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //after submitting successfully
        Toast.makeText(this,temp.dis+" is stored in history" , Toast.LENGTH_SHORT).show();
        ((EditText)findViewById(R.id.editText)).setText("");
        ((EditText)findViewById(R.id.editText4)).setText("");


    }

    //todo use me for retrieval
    public String getFileToString(File file) {
        String row = "";
        try {
            FileInputStream fin = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));

            String op;
            StringBuilder sb = new StringBuilder();
            while ( (op = br.readLine()) != null ){
                sb.append(op+"\n");

            }

            row = sb.toString();
        } catch (FileNotFoundException e) {
            Log.e("File err" ,  "reading file");
        } catch (IOException e) {
            Log.e("File err" ,  e.toString());
        }
        return row;
    }
}