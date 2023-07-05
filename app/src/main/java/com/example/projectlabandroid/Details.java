package com.example.projectlabandroid;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notifiaction.
     */
    // TODO: Rename and change types and number of parameters
    public static Details newInstance(String param1, String param2) {
        Details fragment = new Details();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifiaction, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        final boolean[] descShow = {true};
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        Cursor allCourses = dbHelper.getAllAvailableCourses();
        secondLinearLayout.removeAllViews();

        while (allCourses.moveToNext()) {
            ImageView im = new ImageView(requireContext());
            im.setImageResource(R.drawable.baseline_arrow_right_24);
            LinearLayout horizantal = new LinearLayout(requireContext());

            TextView textView = new TextView(requireContext());
            textView.append("Course Title: "+allCourses.getString(1)+
                    "\n");

            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(600, 300);

            im.setLayoutParams(new ViewGroup.LayoutParams(120, 120));
            horizantal.addView(im);
            horizantal.addView(textView);

            int cnum=allCourses.getInt(0);
            String title = allCourses.getString(1);
            String name = allCourses.getString(2);
            String topics = allCourses.getString(3);
            String pre = allCourses.getString(4);
            String dedline =allCourses.getString(6);
            String start = allCourses.getString(7);
            String sec =allCourses.getString(8);
            String venue= allCourses.getString(9);

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(descShow[0]) {
                       textView.append("Course Number: "+cnum +
                               "\nCourse Title: "+title+
                               "\nName: "+name
                               +"\nCourse Topics: "+topics
                               +"\nPrerequisites: "+pre+
                               "\nDeadline: "+dedline+
                               "\nCourse Start Date: "+start+
                               "\nSchedule: "+sec+
                               "\nVenue: "+venue+
                               "\n"  );
                       im.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                       descShow[0] = false;
                   }else{
                       textView.setText("Course Title: " + title +
                               "\n");
                       im.setImageResource(R.drawable.baseline_arrow_right_24);
                       descShow[0] = true;
                   }
                }
            });

            horizantal.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Adjust the text size as desired
            secondLinearLayout.addView(horizantal);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearL2);
    }
}