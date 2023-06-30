package com.example.projectlabandroid;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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

    public static String trinee_user1 = "alaa@gmail.com";
    public static String trinee_user2 = "shahd@gmail.com";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final adminlogin admin = new adminlogin();
        final trineelogin trainee = new trineelogin();
        final instructorsignin instructor = new instructorsignin();
        final FragmentManager fragmentM = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentM.beginTransaction();


        RadioGroup radioGroup = findViewById(R.id.radioGroup2);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.admin2:
                        FragmentTransaction fragmentT = fragmentM.beginTransaction();
                        fragmentT.replace(R.id.login_root_layout, admin, "adminLogin");
                        fragmentT.commit();
                        break;
                    case R.id.trainee2:
                        FragmentTransaction fragmentT1 = fragmentM.beginTransaction();
                        fragmentT1.replace(R.id.login_root_layout, trainee, "traineeLogin");
                        fragmentT1.commit();
                        break;
                    case R.id.instructor2:
                        FragmentTransaction fragmentT2 = fragmentM.beginTransaction();
                        fragmentT2.replace(R.id.login_root_layout, instructor, "inasLogin");
                        fragmentT2.commit();
                        break;

                }
            }
        });
    }
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

