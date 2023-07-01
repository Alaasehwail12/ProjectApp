package com.example.projectlabandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id){

                    case R.id.course:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CourseFragment()).commit();
                        break;

                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();
                        break;
                    case R.id.makeavailable:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new make_courses_avaliableFragment()).commit();
                        break;
                    case R.id.insprofile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ViewProfileFragment()).commit();
                        break;
                    case R.id.trineeprofile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ViewTraineeProfileFragment()).commit();
                        break;
                    case R.id.viewstudents:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new View_student_for_any_course()).commit();
                        break;
                    case R.id.applied_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new applied_reject_student()).commit();
                        break;

                    case R.id.logout:
                        startActivity(new Intent(HomeLayout.this, logIn.class));

                        break;

                    default:
                        return true;
                }
                return false;
            }
        });


    }
}