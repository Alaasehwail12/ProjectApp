package com.example.projectlabandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        RadioButton admin = (RadioButton) findViewById(R.id.admin);
        RadioButton inst = (RadioButton) findViewById(R.id.instructor);
        RadioButton traniee = (RadioButton) findViewById(R.id.trainee);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final adminSignUpFragment adminFragment = new adminSignUpFragment();
        final traineeSignUpFragment trainee = new traineeSignUpFragment();
        final instructorSignupFragment instructor = new instructorSignupFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_layout, adminFragment, "adminFragment");
        fragmentTransaction.commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {

                    case R.id.admin:
                        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                        fragmentTransaction3.replace(R.id.root_layout, adminFragment, "adminFragment");
                        fragmentTransaction3.commit();
                        break;

                    case R.id.trainee:
                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                        fragmentTransaction2.replace(R.id.root_layout, trainee, "traineeFragment");
                        fragmentTransaction2.commit();
                        break;
                    case R.id.instructor:
                        FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                        fragmentTransaction4.replace(R.id.root_layout, instructor, "traineeFragment");
                        fragmentTransaction4.commit();
                        break;


                }

            }
        });
    }
}