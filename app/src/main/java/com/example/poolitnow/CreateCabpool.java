package com.example.poolitnow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CreateCabpool extends AppCompatActivity {

    //variable initialization
    EditText from_EditText, to_EditText;
    Button date_Button,time_Button,add_Button;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int day, month, year, hour, minute;
    String date,time,from, to;

    ArrayList<Cabpools> cabpools;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cabpool);

        //connecting variables with respective views
        from_EditText = findViewById(R.id.PickupPoint_Create);
        to_EditText = findViewById(R.id.DropLocation_Create);
        date_Button = findViewById(R.id.DateButton_Create);
        time_Button = findViewById(R.id.TimeButton_Create);
        add_Button = findViewById(R.id.AddButton_Create);

        cabpools = new ArrayList<>();

        //code to input date
        date_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(
                        CreateCabpool.this,
                        android.R.style.Theme_Holo_Light_Panel,
                        onDateSetListener,year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                date = day+"/"+month+"/"+year;
                date_Button.setText(date);
            }
        };
        //date input ends here

        //block to input time via timePicker
        time_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(CreateCabpool.this,android.R.style.Theme_Holo_Light_Panel, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        String suffix;
                        if(hour>=12) suffix="PM";
                        else suffix="AM";

                        time = hour+":"+minutes+" "+suffix;
                        time_Button.setText(time);

                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });
        //time input ends here

        //add button functionality
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from_EditText.getText().toString().isEmpty() || to_EditText.getText().toString().isEmpty() || date_Button.getText().toString().equalsIgnoreCase("Select Date") || time_Button.getText().toString().equalsIgnoreCase("Select Time") )
                    Toast.makeText(getApplicationContext(),"Fields are empty", Toast.LENGTH_SHORT).show();
                else if(from_EditText.getText().toString().equalsIgnoreCase(to_EditText.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Destination cant be same as Pickup Point",Toast.LENGTH_SHORT).show();
                else
                    {
                        from = from_EditText.getText().toString();
                        to = to_EditText.getText().toString();
                                                                    //for uploading data to firebase
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cabpools");
                        HashMap<String,String> hashMap = new HashMap<>();

                        hashMap.put("from",from);
                        hashMap.put("to",to);
                        hashMap.put("date",date);
                        hashMap.put("time",time);

                        databaseReference.push().setValue(hashMap);
                        String key = databaseReference.getKey();

                        Intent toMainActivity = new Intent(getApplicationContext(), mainApp_activity.class);
                        startActivity(toMainActivity);
                }
            }
        });



    }



}
