package com.example.projectlabandroid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.projectlabandroid.databinding.ActivityInstructorPageBinding;

public class InstructorPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityInstructorPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_page);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

       // getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
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
                    case R.id.coursetaught:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InstructorCourseTaught()).commit();
                        break;
                    case R.id.schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InstructorScheduleFragment()).commit();
                        break;
                    case R.id.liststudent:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new student_for_instuctore()).commit();
                        break;

                    case R.id.profile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InstructorViewHisProfile()).commit();
                        break;
                    case R.id.logout:
                        startActivity(new Intent(InstructorPage.this, logIn.class));
                        break;

                    default:
                        return true;

                }
                return false;
            }
        });
    }

}
