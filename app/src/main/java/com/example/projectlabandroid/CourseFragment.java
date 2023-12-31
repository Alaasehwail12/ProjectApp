package com.example.projectlabandroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
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

    ImageView image_view_minus ;;
    ImageView image_view_edit;
    //Button updateButton;
    DataBaseHelper dbHelper ;

    public static int j_edit = 400;
    public static String NOTIFICATION_TITLE_edit = "";
    public static String NOTIFICATION_BODY_edit []  = new String [20];

    public static int j_delete = 500;
    public static String NOTIFICATION_TITLE_delete = "";
    public static String NOTIFICATION_BODY_delete []  = new String [20];


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView image_view_plues = (ImageView) getActivity().findViewById(R.id.imageView3);

    //    secondLinearLayout=getActivity().findViewById(R.id.secondLinearLayout);


        image_view_plues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new CreateCourseFragment());
                fragmentTransaction.commit();
            }
        });



    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_course, container, false);
        return view;
    }

    public static Course c ;
    @Override
    public void onResume() {
        super.onResume();
//        Toast toast =Toast.makeText(getActivity(),"inside the resume function true",Toast.LENGTH_SHORT);
//        toast.show();
        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        Cursor allCourses = dbHelper.getAllCourses();
        secondLinearLayout.removeAllViews();

        while(allCourses.moveToNext()){
            ImageView imageView = new ImageView(requireContext());

            TextView textView = new TextView(requireContext());
            TextView textView2 = new TextView(requireContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            LinearLayout horizantal = new LinearLayout(requireContext());
            horizantal.setOrientation(LinearLayout.HORIZONTAL);
            horizantal.setGravity(Gravity.CENTER); // Set the gravity to center horizontally

            byte [] bytes = allCourses.getBlob(4);
            Bitmap bitmapImageDB ;
            if (bytes != null) {
                bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmapImageDB);
            } else {
               // textView.setText("Photo=" +allCourses.getString(4) +"\n");
                imageView.setImageResource(R.drawable.homework);
            }
            textView.append("Course Number= "+allCourses.getInt(0) +
                    "\nCourse Title= "+allCourses.getString(1)
                    +"\nCourse Topics= "+allCourses.getString(2)
                    +"\nPrerequisites= "+allCourses.getString(3)+
                    "\nDeadline= "+allCourses.getString(5)+
                    "\nCourse Start Date= "+allCourses.getString(6)+
                    "\nSchedule= "+allCourses.getString(7)+
                    "\nVenue= "+allCourses.getString(8)+
                    "\n\n");
           // secondLinearLayout.addView(imageView);

            horizantal.addView(imageView);
            horizantal.addView(textView);
            horizantal.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            secondLinearLayout.addView(horizantal);
            textView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Adjust the text size as desired
            textView.setGravity(Gravity.CENTER); // Set the text alignment to center


            image_view_minus = new ImageView(requireContext());
            image_view_edit = new ImageView(requireContext());
            image_view_edit.setImageResource(R.drawable.edit);
            image_view_minus.setImageResource(R.drawable.minus);

            LinearLayout horizontalLayout = new LinearLayout(requireContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);

            image_view_minus.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            image_view_minus.setLayoutParams(new ViewGroup.LayoutParams(100, 100));

            image_view_edit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            image_view_edit.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            layoutParams.setMargins(0, 0, 70, 0);
            image_view_minus.setLayoutParams(layoutParams);

            textView.setTextSize(20);
            horizontalLayout.addView(image_view_minus);
            horizontalLayout.addView(image_view_edit);
            //secondLinearLayout.addView(textView);
            horizontalLayout.setGravity(Gravity.CENTER);
            secondLinearLayout.addView(horizontalLayout);


            int  num = allCourses.getInt(0);
            String  title = allCourses.getString(1);
            String  topice = allCourses.getString(2);
            String  venu = allCourses.getString(8);
            String  preq = allCourses.getString(3);
            byte [] bytes2 = allCourses.getBlob(4);
            String Start_date = allCourses.getString(6);
            String dedline = allCourses.getString(5);
            String schedual = allCourses.getString(7);




            image_view_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (allCourses.moveToFirst()) {
                        c = new Course();

                        c.setCtitle(title);
                        c.setCNum(String.valueOf(num));

                        int courseIdIndex = allCourses.getColumnIndex("CNum");
                        int courseId = allCourses.getInt(courseIdIndex);

                        dbHelper.deleteCourseByCNum(Integer.parseInt(c.getCNum()));
                        dbHelper.deleteavailableCourseByCNum(Integer.parseInt(c.getCNum()));
                        dbHelper.deletetrineeCourseByCNum(Integer.parseInt(c.getCNum()));
                        dbHelper.deletetracceptedineeCourseByCNum(Integer.parseInt(c.getCNum()));

                        NOTIFICATION_TITLE_delete = "The Course You applied Deleted!!!!";
                        NOTIFICATION_BODY_delete[j_delete-500] = "The "+c.getCtitle()+" COURSE Canceled!!";
                        ++j_delete;

                        textView.setText("");
                        imageView.setImageDrawable(null);
                        Toast.makeText(requireContext(), "Course Number " + Integer.parseInt(c.getCNum())+ " Deleted !", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            image_view_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = new Course();
                    c.setCNum(String.valueOf(num));
                    c.setDeadline(dedline);
                    c.setStartDateCourse(Start_date);
                    c.setSchedule(schedual);
                    c.setCtitle(title);
                    c.setCTopics(topice);
                    c.setPrerequisites(preq);
                    c.setVenue(venu);
                    c.setPhoto(bytes2);

                    NOTIFICATION_TITLE_edit = "There is changes in course you applied!!";
                    NOTIFICATION_BODY_edit[j_edit-400] = "There is a changes in "+c.getCtitle()+" COURSE!!";
                    ++j_edit;

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new EditCourseFragment());
                    fragmentTransaction.commit();
                }
            });
            secondLinearLayout.addView(textView2);

        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout);
    }


}

