package com.example.projectlabandroid;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link search_for_course#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search_for_course extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public search_for_course() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search_for_course.
     */
    // TODO: Rename and change types and number of parameters
    public static search_for_course newInstance(String param1, String param2) {
        search_for_course fragment = new search_for_course();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText course_search = (EditText) getActivity().findViewById(R.id.search) ;
        TextView course_text = (TextView) getActivity().findViewById(R.id.textView14) ;
        ImageView im = (ImageView) getActivity().findViewById(R.id.imageView9);
        LinearLayout second = (LinearLayout) getActivity().findViewById(R.id.secondlinear);

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        ImageView imageView = new ImageView(requireContext());

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor allCourses = dbHelper.getAllAvailableCourses_bytitle(course_search.getText().toString());
                while(allCourses.moveToNext()) {
                    byte [] bytes = allCourses.getBlob(5);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
                    Bitmap bitmapImageDB = null;
                    if (bytes != null) {
                        bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmapImageDB);
                    } else {
                        imageView.setImageResource(R.drawable.online_learning);
                    }
                    second.addView(imageView);
                    course_text.append("Course Number: "+allCourses.getInt(0) +
                            "\nInstructor Name: "+allCourses.getString(2)+
                            "\nCourse Title: "+allCourses.getString(1)
                            +"\nCourse Topics: "+allCourses.getString(3)
                            +"\nPrerequisites: "+allCourses.getString(4)+
                            "\nDeadline: "+allCourses.getString(6)+
                            "\nCourse Start Date: "+allCourses.getString(7)+
                            "\nSchedule: "+allCourses.getString(8)+
                            "\nVenue: "+allCourses.getString(9)+
                            "\n"  );
                    course_text.setTextSize(22);

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_for_course, container, false);
    }
}