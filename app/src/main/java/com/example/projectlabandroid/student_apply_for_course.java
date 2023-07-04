package com.example.projectlabandroid;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link student_apply_for_course#newInstance} factory method to
 * create an instance of this fragment.
 */
public class student_apply_for_course extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public student_apply_for_course() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_apply_for_course.
     */
    // TODO: Rename and change types and number of parameters
    public static student_apply_for_course newInstance(String param1, String param2) {
        student_apply_for_course fragment = new student_apply_for_course();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    LinearLayout secondLinearLayout;
    public static String preq;
    String applied;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_apply_for_course, container, false);
    }
//    @Override
//    public void respond(String data) {
//        cretae_applied_student secondFragment = (cretae_applied_student) getSupportFragmentManager().findFragmentById(R.id.fragment2);
//        secondFragment.changeData(data);
//    }

    public static Course c2;


    @Override
    public void onResume() {
        super.onResume();

        Toast toast =Toast.makeText(getActivity(),"inside the resume function true",Toast.LENGTH_SHORT);
        toast.show();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        Cursor allCourses = dbHelper.getAllAvailableCourses();
        int borderColor = getResources().getColor(R.color.purple_200);
        GradientDrawable border = new GradientDrawable();
       // border.setStroke(5, borderColor);
      //  border.setCornerRadius(25);
        secondLinearLayout.removeAllViews();
        //  secondLinearLayout.setBackground(border);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 600);
        layoutParams.setMargins(0, 30, 0, 0);
        //layoutParams.gravity = Gravity.CENTER;
        // secondLinearLayout.setLayoutParams(layoutParams);
        while(allCourses.moveToNext()){
            ImageView imageView = new ImageView(requireContext());
            TextView textView = new TextView(requireContext());
            textView.setBackground(border);
            byte [] bytes = allCourses.getBlob(5);
            Bitmap bitmapImageDB = null;
            if (bytes != null) {
                bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
                imageView.setImageBitmap(bitmapImageDB);
            } else {
                imageView.setImageResource(R.drawable.online_learning);

                imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
                // textView.setText(allCourses.getString(5) +"\n");
            }
            //  textView.setBackground(border);
           // textView.setGravity(Gravity.CENTER);
            textView.setTextColor(borderColor);
            textView.setGravity(Gravity.CENTER);
            //textView.setLayoutParams(layoutParams);
            textView.append("\nInstructor Name: "+allCourses.getString(2)+
                            "\nCourse Title: "+allCourses.getString(1)+
                    "\nCourse Topics: "+allCourses.getString(3)+
                    "\n"  );
            textView.setTextSize(20);
            ImageView apply = new ImageView(requireContext());
            apply.setImageResource(R.drawable.apply);
            apply.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            apply.setLayoutParams(new ViewGroup.LayoutParams(200, 200));

            String t = allCourses.getString(1);
            String time = allCourses.getString(8);

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c2 = new Course();
                    c2.setCtitle(t);
                    c2.setSchedule(time);
                   if(dbHelper.isStudentRegesterd(c2.getCtitle(),trineelogin.tr.getEmail())){
                       apply.setImageResource(R.drawable.apply_fill);
                       Toast.makeText(requireContext(), "You already tried to apply!!", Toast.LENGTH_SHORT).show();
                  }
                   else {
                       FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction.replace(R.id.frameLayout, new cretae_applied_student());
                       fragmentTransaction.commit();
                   }
                }
            });

            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(700, 700);
            layoutParam.setMargins(0, 0, 20, 0);

            LinearLayout verticalLayout = new LinearLayout(requireContext());
            verticalLayout.setOrientation(LinearLayout.VERTICAL);
            verticalLayout.addView(imageView);
            verticalLayout.addView(textView);
            verticalLayout.setLayoutParams(layoutParam);

            LinearLayout horizontalLayout = new LinearLayout(requireContext());
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            horizontalLayout.addView(verticalLayout);
            horizontalLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border));
            horizontalLayout.addView(apply);
            secondLinearLayout.addView(horizontalLayout);

        }

//        Cursor allCourses2 = dbHelper.getAllAvailableCourses_trinee();
//        while (allCourses2.moveToNext()) {
//            TextView text2 = new TextView(requireContext());
//
//            text2.setText("\nstudent Name: "+allCourses2.getString(2)+
//                    "\nCourse Title: "+allCourses2.getString(1)+
//                    "\n Time: "+allCourses2.getString(3)+
//                    "\n");
//            ImageView apply = new ImageView(requireContext());
//            apply.setImageResource(R.drawable.apply);
//            secondLinearLayout.addView(text2);
//
//        }

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout);
    }
}