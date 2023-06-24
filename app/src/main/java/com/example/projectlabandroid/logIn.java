package com.example.projectlabandroid;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class logIn extends AppCompatActivity {

    DataBaseHelper dbHelper =new DataBaseHelper(logIn.this,"Database", null,1);
    public static admin ad = new admin();
    public static trainee tr = new trainee();
    public static instructor ins = new instructor();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.pass);
        CheckBox rememberme = (CheckBox) findViewById(R.id.rememberme);
        Button signin =(Button) findViewById(R.id.signin);
        Button signUP =(Button) findViewById(R.id.signup);
        RadioGroup radioGroup = findViewById(R.id.radioGroup2);
        rememberme.setChecked(true);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(logIn.this);
        if (!Objects.equals(sharedPrefManager.readString("email", "noValue"), "noValue")){
            email.setText(sharedPrefManager.readString("email", "noValue"));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = email.getText().toString().trim();
                String enterPassword = pass.getText().toString().trim();

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        switch (checkedId) {

                            case R.id.admin2:
                                if (!dbHelper.isadminRegistered(enteredEmail))
                                {
                                    Toast.makeText(logIn.this, "This email is not registered for admin!", Toast.LENGTH_SHORT).show();
                                }else if(!dbHelper.correctadminSignIn(enteredEmail, enterPassword)){
                                    Toast.makeText(logIn.this, "Incorrect password for admin!", Toast.LENGTH_SHORT).show();
                                }else{
                                    if (rememberme.isChecked())
                                        sharedPrefManager.writeString("email", email.getText().toString().trim());
                                    else
                                        sharedPrefManager.writeString("email", "noValue");
                                    ad= dbHelper.getadminByEmail(email.getText().toString().trim());
                                    Toast.makeText(logIn.this, "the login procsess succesfull for admin!", Toast.LENGTH_SHORT).show();

                                    //startActivity(new Intent(logIn.this, HomeScreen.class));
                                }

                                break;

                            case R.id.trainee2:
                                if (!dbHelper.istraineeRegistered(enteredEmail))
                                {
                                    Toast.makeText(logIn.this, "This email is not registered for trainee!", Toast.LENGTH_SHORT).show();
                                }else if(!dbHelper.correcttranieeSignIn(enteredEmail, enterPassword)){
                                    Toast.makeText(logIn.this, "Incorrect password for trainee!", Toast.LENGTH_SHORT).show();
                                }else{
                                    if (rememberme.isChecked())
                                        sharedPrefManager.writeString("email", email.getText().toString().trim());
                                    else
                                        sharedPrefManager.writeString("email", "noValue");
                                    tr= dbHelper.gettraineeByEmail(email.getText().toString().trim());
                                    Toast.makeText(logIn.this, "the login procsess succesfull for trainee!", Toast.LENGTH_SHORT).show();

                                    //startActivity(new Intent(logIn.this, HomeScreen.class));
                                }

                                break;
                            case R.id.instructor2:
                                if (!dbHelper.isinstructorRegistered(enteredEmail))
                                {
                                    Toast.makeText(logIn.this, "This email is not registered for inst!", Toast.LENGTH_SHORT).show();
                                }else if(!dbHelper.correctinstructoSignIn(enteredEmail, enterPassword)){
                                    Toast.makeText(logIn.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                }else{
                                    if (rememberme.isChecked())
                                        sharedPrefManager.writeString("email", email.getText().toString().trim());
                                    else
                                        sharedPrefManager.writeString("email", "noValue");
                                    ins= dbHelper.getinstructoByEmail(email.getText().toString().trim());
                                    Toast.makeText(logIn.this, "the login procsess succesfull for instructor!", Toast.LENGTH_SHORT).show();

                                    //startActivity(new Intent(logIn.this, HomeScreen.class));
                                }

                                break;

                        }

                    }
                });
            }
        });


        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(logIn.this, SignUp.class));
            }
        });



    }
//    protected void onResume() {
//        super.onResume();
//        DataBaseHelper dbHelper =new DataBaseHelper(logIn.this,"Database", null,1);
//        Cursor allInstructorCursor = dbHelper.getAllInstructors();
//
//        while (allInstructorCursor.moveToNext()){ //this function allow me to move between data
//
//                        t.setText("Email= "+allInstructorCursor.getString(0) +"\nFirstName= "+allInstructorCursor.getString(1)
//                                +"\nLastName= "+allInstructorCursor.getString(2)
//                                +"\nPassword= "+allInstructorCursor.getString(3)
//                                +"\nPhoto= "+allInstructorCursor.getBlob(4)+
//                                "\nMobile Number= "+allInstructorCursor.getString(5)+
//                                "\nAddress= "+allInstructorCursor.getString(6)+
//                                "\nSpecialization= "+allInstructorCursor.getString(7)+
//                                "\nDegree= "+allInstructorCursor.getString(8)+
//                                "\n\n" );
//                    }
//    }

}