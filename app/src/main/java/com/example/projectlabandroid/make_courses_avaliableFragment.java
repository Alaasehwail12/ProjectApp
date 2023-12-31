package com.example.projectlabandroid;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link make_courses_avaliableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class make_courses_avaliableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public make_courses_avaliableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment make_courses_avaliableFragment.
     */
    // TODO: Rename and change types and number of parameters

    LinearLayout secondLinearLayout;

    public static make_courses_avaliableFragment newInstance(String param1, String param2) {
        make_courses_avaliableFragment fragment = new make_courses_avaliableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String NOTIFICATION_TITLE = "";
    public static String NOTIFICATION_BODY []  = new String [20];

    public static  int i  = 300;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

       ;
    public  String[] options;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        TextView new_text = (TextView) getActivity().findViewById(R.id.textt);
//        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
//        List<String> instructorNames = new ArrayList<>();
//        ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView3);
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout, new create_course_avaliable_fragment());
//                fragmentTransaction.commit();
//            }
//        });

       // secondLinearLayout = getActivity().findViewById(R.id.secondLinearLayout);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_courses_avaliable, container, false);
    }
    public static Course coursera;
    DataBaseHelper dbHelper;

    @Override
    public void onResume() {
        super.onResume();
        Toast toast =Toast.makeText(getActivity(),"inside the resume function true",Toast.LENGTH_SHORT);
        toast.show();
        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        Cursor allCourses = dbHelper.getAllCourses();
        int borderColor = getResources().getColor(R.color.purple_700);
        secondLinearLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 700);
        layoutParams.setMargins(0, 30, 0, 0);
        layoutParams.gravity = Gravity.CENTER;
        while(allCourses.moveToNext()){
            ImageView imageView = new ImageView(requireContext());
            TextView textView = new TextView(requireContext());
            ImageView applied = new ImageView(requireContext());
            LinearLayout horizantal = new LinearLayout(requireContext());
            horizantal.setOrientation(LinearLayout.HORIZONTAL);
            horizantal.setGravity(Gravity.CENTER); // Set the gravity to center horizontally

            applied.setImageResource(R.drawable.tick);

            applied.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            applied.setLayoutParams(new ViewGroup.LayoutParams(140, 200));
            textView.setLayoutParams(layoutParams);


            byte [] bytes = allCourses.getBlob(4);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            Bitmap bitmapImageDB = null;
            if (bytes != null) {
                bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmapImageDB);
            } else {
                imageView.setImageResource(R.drawable.onlinelearning);
            }

            textView.append("Course Number: "+allCourses.getInt(0) +
                    "\nCourse Title: "+allCourses.getString(1)
                    +"\nCourse Topics: "+allCourses.getString(2)
                    +"\nPrerequisites: "+allCourses.getString(3)+
                    "\nDeadline: "+allCourses.getString(5)+
                    "\nCourse Start Date: "+allCourses.getString(6)+
                    "\nSchedule: "+allCourses.getString(7)+
                    "\nVenue: "+allCourses.getString(8)+
                    "\n"  );

            horizantal.addView(imageView);
            horizantal.addView(textView);
            horizantal.addView(applied);

            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Adjust the text size as desired
            textView.setTextColor(borderColor);
            textView.setGravity(Gravity.CENTER);

            String title = allCourses.getString(1);
            int cnum = allCourses.getInt(0);


            String inputDate = allCourses.getString(5);


            String outputFormat = "yyyy-MM-dd";
            if(inputDate == null){
                inputDate ="00/0/0000";
            }

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/M/yyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            Date date = null;
            try {
                date = inputDateFormat.parse(inputDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String outputDate = outputDateFormat.format(date);
            LocalDate currentDate = LocalDate.now();
//            textView.append(currentDate.toString());
//            textView.append("\n");
//            textView.append(outputDate);


            applied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    coursera = new Course();
                    coursera.setCtitle(title);
                    coursera.setCNum(String.valueOf(cnum));
                    textView.setTextSize(22);
                    NOTIFICATION_TITLE ="New Course is available";
                    NOTIFICATION_BODY[i-300]= "The Course "+coursera.getCtitle()+"is available now! ";
                    ++i;
                    if(dbHelper.isCourseTitleAvailable(coursera.getCtitle())){
                        applied.setImageResource(R.drawable.tick_fill);

                    }else if(currentDate.toString().compareTo(outputDate) == 0){
                            Toast.makeText(requireContext(), "The Deadline Ended!", Toast.LENGTH_SHORT).show();
                            dbHelper.deleteavailableCourseByCNum(cnum);
                            applied.setImageResource(R.drawable.tick_fill);

                    }else {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new create_course_avaliable_fragment());
                        fragmentTransaction.commit();
                    }


                }
            });

            horizantal.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border));
            secondLinearLayout.addView(horizantal);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout);
    }
}