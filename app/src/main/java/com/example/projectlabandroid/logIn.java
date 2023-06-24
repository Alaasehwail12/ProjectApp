package com.example.projectlabandroid;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class logIn extends AppCompatActivity {
    private   TextView t;
    //LinearLayout secondLinearLayout=new LinearLayout(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.pass);
        CheckBox rememberme = (CheckBox) findViewById(R.id.rememberme);
        Button signin =(Button) findViewById(R.id.signin);
        Button signUP =(Button) findViewById(R.id.signup);
        t =(TextView) findViewById(R.id.t);
        //  secondLinearLayout.setOrientation(LinearLayout.VERTICAL);

        rememberme.setChecked(true);

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(logIn.this, SignUp.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
    protected void onResume() {
        super.onResume();
        DataBaseHelper dbHelper =new DataBaseHelper(logIn.this,"Database", null,1);
        Cursor allInstructorCursor = dbHelper.getAllInstructors();
        //secondLinearLayout.removeAllViews();
        if(allInstructorCursor != null){
            Toast toast =Toast.makeText(logIn.this,"not null cusrssor!",Toast.LENGTH_SHORT);
            toast.show();
            Toast toast1 =Toast.makeText(logIn.this,"not null cusrssor!",Toast.LENGTH_SHORT);
            toast.show();

        }
        while (allInstructorCursor.moveToNext()){ //this function allow me to move between data
            t.setText("Email= "+allInstructorCursor.getString(0) +"\nFirstName= "+allInstructorCursor.getString(1)
                    +"\nLastName= "+allInstructorCursor.getString(2)
                    +"\nPassword= "+allInstructorCursor.getString(3)
                    +"\nPhoto= "+allInstructorCursor.getString(4)+
                    "\nMobile Number= "+allInstructorCursor.getString(5)+
                    "\nAddress= "+allInstructorCursor.getString(6)+
                    "\nSpecialization= "+allInstructorCursor.getString(7)+
                    "\nDegree= "+allInstructorCursor.getString(8)+
                    "\n\n" );
            //  secondLinearLayout.addView(t);
        }


    }

}