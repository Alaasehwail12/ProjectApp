package com.example.projectlabandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
////        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
////        NavigationView navigationView = findViewById(R.id.navigation_view);
//
//        MaterialToolbar toolbar = findViewById(R.id.topAppBar1);
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout1);
//        NavigationView navigationView = findViewById(R.id.navigation_view1);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                drawerLayout.closeDrawer(GravityCompat.START);
//                switch (id){
//                    case R.id.serach:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new search_for_course()).commit();
//                        break;
//                    case R.id.Apply:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new student_apply_for_course()).commit();
//                        break;
//                    case R.id.profile:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TraineeProfile()).commit();
//                        break;
//                    case R.id.history:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();
//                        break;
//
//                    case R.id.logout:
//                        startActivity(new Intent(MainActivity.this, logIn.class));
//                        break;
////                    case R.id.history:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment()).commit();
////                        break;
////                    case R.id.makeavailable:
////
////                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new make_courses_avaliableFragment()).commit();
////                        break;
////                    case R.id.insprofile:
////
////                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ViewProfileFragment()).commit();
////                        break;
////                    case R.id.trineeprofile:
////
////                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ViewTraineeProfileFragment()).commit();
////                        break;
//
//                    default:
//                        return true;
//                }
//                return false;
//            }
//        });
//

        Button startButton =(Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInSignUp.class));
            }
        });
    }
}