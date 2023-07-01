package com.example.projectlabandroid;

import android.annotation.SuppressLint;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructorCourseTaught#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructorCourseTaught extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout secondLinearLayout;

    DataBaseHelper dbHelper;

    public InstructorCourseTaught() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstructorCourseTaught.
     */
    // TODO: Rename and change types and number of parameters
    public static InstructorCourseTaught newInstance(String param1, String param2) {
        InstructorCourseTaught fragment = new InstructorCourseTaught();
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

    }

        public void onResume() {
        super.onResume();
        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        secondLinearLayout.removeAllViews();
        String email = instructorsignin.ins.getEmail();
        Cursor cursor = dbHelper.getAllCoursesforInstructor(email);
        TextView textView = new TextView(requireContext());
        textView.setText(""); // Clear the initial text of the TextView

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String cTitle = cursor.getString(cursor.getColumnIndex("Ctitle"));
            String currentText = textView.getText().toString();
            String newText = currentText + cTitle + "\n";
            textView.setText(newText);
        }

        cursor.close();
            textView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Adjust the text size as desired
            textView.setGravity(Gravity.CENTER); // Set the text alignment to center

            secondLinearLayout.addView(textView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_course_taught, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout);
    }

}