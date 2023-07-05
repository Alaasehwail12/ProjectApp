package com.example.projectlabandroid;

import android.database.Cursor;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link student_for_instuctore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class student_for_instuctore extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public student_for_instuctore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_for_instuctore.
     */
    // TODO: Rename and change types and number of parameters
    public static student_for_instuctore newInstance(String param1, String param2) {
        student_for_instuctore fragment = new student_for_instuctore();
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
    DataBaseHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_for_instuctore, container, false);
        secondLinearLayout = v.findViewById(R.id.secondLinearLayout20);
        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        Cursor allCourses = dbHelper.view_student_for_any_ins(instructorsignin.ins);
        secondLinearLayout.removeAllViews();
        while (allCourses.moveToNext()) {
            TextView textView = new TextView(requireContext());
            textView.append("Course : "+allCourses.getString(2) +
                    "\n\nName : "+allCourses.getString(0) +" "+allCourses.getString(1));
            textView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22); // Adjust the text size as desired
            textView.setGravity(Gravity.CENTER);
            secondLinearLayout.addView(textView);
        }


        return v;
    }
}