package com.example.poolitnow;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class register_activity extends AppCompatActivity {

    EditText name_registerScreen_java, password_registerScreen_java, confirmPassword_registerScreen_java, email_registerScreen_java;
    int mYear, mMonth, mDay;
    String date;
    String name;
    String password;
    String confirmPassword, email;
    Button registerButton_registerScreen_java;
    // Creating Firebase authentication object
    ProgressDialog mProgress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        name_registerScreen_java = findViewById(R.id.name_registerActivity_xml);
        password_registerScreen_java = findViewById(R.id.password_registerActivity_xml);
        confirmPassword_registerScreen_java = findViewById(R.id.reenterPassword_registerActivity_xml);
        password_registerScreen_java.getTransformationMethod();
        confirmPassword_registerScreen_java.getTransformationMethod();
        email_registerScreen_java = findViewById(R.id.email_registerActivity_xml);
        registerButton_registerScreen_java = findViewById(R.id.registerButton_registerActivity_xml);

        registerButton_registerScreen_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = name_registerScreen_java.getText().toString();
                password = password_registerScreen_java.getText().toString();
                confirmPassword = confirmPassword_registerScreen_java.getText().toString();
                email = email_registerScreen_java.getText().toString();
                if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty())
                { Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();}
                else{
                    if (!password.equals(confirmPassword))
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    String uid= mAuth.getCurrentUser().getUid();
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                    mAuth.getCurrentUser().updateProfile(profileChangeRequest);

                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(),"E-mail sent your E-mail address, kindly check your E-mail", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                mAuth.signOut();
                                            }else Toast.makeText(getApplicationContext(), "E-mail Verification failed, please try again",Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } else {
                                    Toast.makeText(getApplicationContext(), "Some error occured, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });




                    }}

            }});
    }

}
