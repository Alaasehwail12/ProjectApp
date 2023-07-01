package com.example.projectlabandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeActivityTrainee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_trainee);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar1);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.navigation_view1);

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
                switch (id) {
                    case R.id.serach:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new search_for_course()).commit();
                        break;
                    case R.id.Apply:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new student_apply_for_course()).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TraineeProfile()).commit();
                        break;
                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();
                        break;
                    case R.id.donecourses:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new View_studied_courses()).commit();
                        break;

                    case R.id.logout:
                        startActivity(new Intent(HomeActivityTrainee.this, logIn.class));
                        break;

                    default:
                        return true;
                }
                return false; // Return true to indicate the selection has been handled
            }
        });
    }


}