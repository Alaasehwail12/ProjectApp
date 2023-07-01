package com.example.projectlabandroid;

import android.database.Cursor;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link withdraw_course_regestration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class withdraw_course_regestration extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public withdraw_course_regestration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment withdraw_course_regestration.
     */
    // TODO: Rename and change types and number of parameters
    public static withdraw_course_regestration newInstance(String param1, String param2) {
        withdraw_course_regestration fragment = new withdraw_course_regestration();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    LinearLayout secondLinearLayout;
    DataBaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Course c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_withdraw_course_regestration, container, false);
        secondLinearLayout = v.findViewById(R.id.secondLinearLayout80);

        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        Cursor allCourses2 = dbHelper.getAllAvailableCourses_trinee(trineelogin.tr);


        while (allCourses2.moveToNext()) {
            LinearLayout horizantal = new LinearLayout(requireContext());
            ImageView image= new ImageView(requireContext());
            horizantal.setOrientation(LinearLayout.HORIZONTAL);
            image.setImageResource(R.drawable.cancel);
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            image.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            TextView text2 = new TextView(requireContext());
            text2.setText("student Name: "+allCourses2.getString(2)+
                    "\nCourse Title: "+allCourses2.getString(1)+
                    "\nTime: "+allCourses2.getString(3)+
                    "\n");
            text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Adjust the text size as desired
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(600, 300);
            layoutParam.setMargins(0, 0, 20, 0);
            text2.setLayoutParams(layoutParam);
            horizantal.addView(text2);
            horizantal.addView(image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c=new Course();
                    c.setCtitle(allCourses2.getString(1));
                    image.setImageResource(R.drawable.cancel_fill);
                    dbHelper.removetrinee_byemail(trineelogin.tr,c);
                }
            });

            horizantal.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)

            secondLinearLayout.addView(horizantal);
        }

        return v;
    }
}