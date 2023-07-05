package com.example.projectlabandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

        if(applied_reject_student.NOTIFICATION_TITLE_accepet != "") {
            for (int i = 100; i < applied_reject_student.accept; i++) {
                createNotification(i, applied_reject_student.NOTIFICATION_TITLE_accepet, applied_reject_student.NOTIFICATION_BODY_accept[i - 100]);
            }
        }
        if(applied_reject_student.NOTIFICATION_TITLE_reject != "") {
            for (int i = 200; i < applied_reject_student.reject; i++) {
                createNotification(i, applied_reject_student.NOTIFICATION_TITLE_reject, applied_reject_student.NOTIFICATION_BODY_reject[i - 200]);
            }
        }
        if(make_courses_avaliableFragment.NOTIFICATION_TITLE !="") {
            for (int i = 300; i < make_courses_avaliableFragment.i; i++) {
                createNotification(i, make_courses_avaliableFragment.NOTIFICATION_TITLE, make_courses_avaliableFragment.NOTIFICATION_BODY[i - 300]);
            }
        }
        DataBaseHelper dbHelper = new DataBaseHelper(HomeActivityTrainee.this, "Database", null, 1);

//        TextView t = findViewById(R.id.textView15);
//        t.append(dbHelper.returntitlesfortrainee(trineelogin.tr.getEmail()));
//        t.append("\n");
//        t.append(EditCourseFragment.newCourse.getCtitle());

        if(EditCourseFragment.isedited == true) {
            if (dbHelper.returntitlesfortrainee(trineelogin.tr.getEmail()).compareTo(EditCourseFragment.newCourse.getCtitle()) == 0) {
                if (CourseFragment.NOTIFICATION_TITLE_edit != "") {
                    for (int i = 400; i < CourseFragment.j_edit; i++) {
                        createNotification(i, CourseFragment.NOTIFICATION_TITLE_edit, CourseFragment.NOTIFICATION_BODY_edit[i - 400]);
                    }
                }
                if (CourseFragment.NOTIFICATION_TITLE_delete != "") {
                    for (int i = 500; i < CourseFragment.j_delete; i++) {
                        createNotification(i, CourseFragment.NOTIFICATION_TITLE_delete, CourseFragment.NOTIFICATION_BODY_delete[i - 500]);
                    }
                }
            }
        }
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
                    case R.id.viewApplied:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new withdraw_course_regestration()).commit();
                        break;
                    case R.id.details:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Details()).commit();
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

    private static final String MY_CHANNEL_ID = "my_channel_1";
    private static final String MY_CHANNEL_NAME = "My channel";

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, MY_CHANNEL_NAME, importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(int notificationId, String title, String body) {
        String notificationTag = "my_notification_tag_" + notificationId;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationTag,notificationId, builder.build());
    }
}