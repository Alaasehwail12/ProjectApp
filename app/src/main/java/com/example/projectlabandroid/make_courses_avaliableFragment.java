package com.example.projectlabandroid;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        TextView new_text = (TextView) getActivity().findViewById(R.id.textt);
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        List<String> instructorNames = new ArrayList<>();
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView3);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new create_course_avaliable_fragment());
                fragmentTransaction.commit();
            }
        });

        secondLinearLayout = getActivity().findViewById(R.id.secondLinearLayout);

//        instructorNames= dbHelper.make_course_avalabile("AI");
//        options = instructorNames.toArray(new String[instructorNames.size()]);
//
//        create_course_avaliable_fragment fragment = new create_course_avaliable_fragment();
////        Bundle args = new Bundle();
////        args.putStringArray("options", options);
//       // fragment.setArguments(args);
//        new_text.setTextSize(30);
//        String optionsString = Arrays.toString(options);
    //    new_text.setText(optionsString);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_courses_avaliable, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        Toast toast =Toast.makeText(getActivity(),"inside the resume function true",Toast.LENGTH_SHORT);
        toast.show();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        Cursor allCourses = dbHelper.getAllAvailableCourses();
        int borderColor = getResources().getColor(R.color.purple_200);
        GradientDrawable border = new GradientDrawable();
        border.setStroke(5, borderColor);
        border.setCornerRadius(25);
        secondLinearLayout.removeAllViews();
      //  secondLinearLayout.setBackground(border);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 700);
        layoutParams.setMargins(0, 30, 0, 0);
        layoutParams.gravity = Gravity.CENTER;
       // secondLinearLayout.setLayoutParams(layoutParams);
        while(allCourses.moveToNext()){
            ImageView imageView = new ImageView(requireContext());
            TextView textView = new TextView(requireContext());
            textView.setBackground(border);
            byte [] bytes = allCourses.getBlob(5);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            Bitmap bitmapImageDB = null;
            if (bytes != null) {
                bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmapImageDB);
            } else {
               // textView.setText(allCourses.getString(5) +"\n");
                imageView.setImageResource(R.drawable.online_learning);
            }
          //  textView.setBackground(border);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(borderColor);
        //textView.setLayoutParams(layoutParams);
            textView.append("Course Number: "+allCourses.getInt(0) +
                            "\nInstructor Name: "+allCourses.getString(2)+
                    "\nCourse Title: "+allCourses.getString(1)
                    +"\nCourse Topics: "+allCourses.getString(3)
                    +"\nPrerequisites: "+allCourses.getString(4)+
                    "\nDeadline: "+allCourses.getString(6)+
                    "\nCourse Start Date: "+allCourses.getString(7)+
                    "\nSchedule: "+allCourses.getString(8)+
                    "\nVenue: "+allCourses.getString(9)+
                    "\n"  );
            textView.setTextSize(22);


            String inputDate =allCourses.getString(6);
            String outputFormat = "yyyy-MM-dd";
            if(inputDate == null){
                inputDate="00/0/0000";
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

            if(currentDate.toString().equals(outputDate)){
           //  Toast.makeText(requireContext(), " Deleted !", Toast.LENGTH_SHORT).show();

                if (allCourses.moveToFirst()) {
                    int courseIdIndex = allCourses.getColumnIndex("CNum");
                    int courseId = allCourses.getInt(courseIdIndex);
                    dbHelper.removeavailableCoursebyCnum(courseId);
                    textView.setText("");
                    Toast.makeText(requireContext(), "Course Number " + courseId + " Deleted !", Toast.LENGTH_SHORT).show();
                }
            }
            secondLinearLayout.addView(imageView);
            secondLinearLayout.addView(textView);





        }


    }
}