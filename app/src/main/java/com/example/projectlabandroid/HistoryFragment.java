package com.example.projectlabandroid;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    LinearLayout secondLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public void onResume() {
        super.onResume();

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        Cursor allCourses = dbHelper.history();
        secondLinearLayout.removeAllViews();

        while (allCourses.moveToNext()) {
            TextView textView = new TextView(requireContext());

            textView.append("CNum: "+allCourses.getInt(0) +
                    "\nCourse Title: "+allCourses.getString(1)+
                    "\nCourse Start Date: "+allCourses.getString(2)+
                    "\nVenue: "+allCourses.getString(3)+
                    "\nName: "+allCourses.getString(4)+
                    "\nNumber of student: "+allCourses.getInt(5)+
                    "\n\n");

            textView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Adjust the text size as desired
            textView.setGravity(Gravity.CENTER);
            secondLinearLayout.addView(textView);

        }
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout2);
    }

}