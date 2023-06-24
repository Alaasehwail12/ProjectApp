package com.example.projectlabandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                        break;
                    case R.id.course:
                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CourseFragment()).commit();
                        break;
                    case R.id.history:
                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();
                        break;

                    default:
                        return true;
                }
                return false;
            }
        });
    }
}