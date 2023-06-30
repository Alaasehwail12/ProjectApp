package com.example.projectlabandroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView image_view_plues = (ImageView) getActivity().findViewById(R.id.imageView3);

        secondLinearLayout=getActivity().findViewById(R.id.secondLinearLayout);


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

            byte [] bytes = allCourses.getBlob(4);
            Bitmap bitmapImageDB = null;
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
            secondLinearLayout.addView(imageView);

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
            secondLinearLayout.addView(textView);
            horizontalLayout.setGravity(Gravity.CENTER);
            secondLinearLayout.addView(horizontalLayout);

            image_view_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (allCourses.moveToFirst()) {
                        int courseIdIndex = allCourses.getColumnIndex("CNum");
                        int courseId = allCourses.getInt(courseIdIndex);
                        dbHelper.removeCoursebyCnum(courseId);
                        textView.setText("");
                        Toast.makeText(requireContext(), "Course Number " + courseId + " Deleted !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
          String  title = allCourses.getString(1);
          String  topice = allCourses.getString(2);
          String  venu = allCourses.getString(8);
          String  preq = allCourses.getString(3);

            c = new Course();
            image_view_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    c.setCtitle(title);
                    c.setCTopics(topice);
                    c.setPrerequisites(preq);
                    c.setVenue(venu);

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new EditCourseFragment());
                    fragmentTransaction.commit();

                }
            });
            secondLinearLayout.addView(textView2);

        }
    }
}

