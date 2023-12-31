package com.example.projectlabandroid;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link applied_reject_student#newInstance} factory method to
 * create an instance of this fragment.
 */
public class applied_reject_student extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public applied_reject_student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment applied_reject_student.
     */
    // TODO: Rename and change types and number of parameters
    public static applied_reject_student newInstance(String param1, String param2) {
        applied_reject_student fragment = new applied_reject_student();
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
    Course c;
    Course c2;
    trainee t;

    public static int reject = 200;
    public static int accept = 100;
    public static String NOTIFICATION_TITLE_accepet = "";
    public static String NOTIFICATION_BODY_accept []  = new String [20];

    public static String NOTIFICATION_TITLE_reject = "";
    public static String NOTIFICATION_BODY_reject [] = new String [20];





    @Override
    public void onResume() {
        super.onResume();


        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        Cursor allCourses2 = dbHelper.getAllAvailableCourses_trinee_foradmin();

        while (allCourses2.moveToNext()) {
            LinearLayout horizantal = new LinearLayout(requireContext());
            ImageView image= new ImageView(requireContext());
            ImageView image2= new ImageView(requireContext());

            horizantal.setOrientation(LinearLayout.HORIZONTAL);

            image.setImageResource(R.drawable.cross);
            image2.setImageResource(R.drawable.check);
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            image.setLayoutParams(new ViewGroup.LayoutParams(100, 100));

            image2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT));
            image2.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            String email = allCourses2.getString(2);
            String title = allCourses2.getString(1);
            TextView text2 = new TextView(requireContext());

            c2 = new Course();
            c2.setCtitle(title);

                text2.setText("CNum: " + allCourses2.getInt(0) +
                        "\nE-mail: " + allCourses2.getString(2) +
                        "\nCourse Title: " + allCourses2.getString(1) +
                        "\nTime: " + allCourses2.getString(3) +
                        "\n");
                text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Adjust the text size as desired
                LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(700, 300);
                LinearLayout.LayoutParams layoutParam2 = new LinearLayout.LayoutParams(150, 150);
                layoutParam2.setMargins(10, 10, 20, 0);
                layoutParam.setMargins(0, 20, 20, 0);
                text2.setLayoutParams(layoutParam);
                image.setLayoutParams(layoutParam2);
                image2.setLayoutParams(layoutParam2);
                horizantal.addView(text2);
                horizantal.addView(image);
                horizantal.addView(image2);

                int cnum = allCourses2.getInt(0);

            Course c1=new Course();
            c1.setCtitle(title);


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getActivity(), "You Rejected Student ", Toast.LENGTH_SHORT);
                        toast.show();
                        c=new Course();
                        c.setCtitle(title);
                        t=new trainee();
                        t.setEmail(email);
                        c.setCNum(String.valueOf(cnum));
                        dbHelper.removewaitingtrinee_byemail(t,c);

                        secondLinearLayout.removeView(horizantal);
                        NOTIFICATION_TITLE_reject = "YOU ARE REJECTED!!";
                        NOTIFICATION_BODY_reject [reject-200] = "YOU ARE REJECTED IN "+c.getCtitle()+" COURSE!!";
                        ++reject;
                    }
                });


                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getActivity(), "You accepted Student ", Toast.LENGTH_SHORT);
                        toast.show();
                        c=new Course();
                        c.setCtitle(title);
                        t=new trainee();
                        t.setEmail(email);
                        c.setCNum(String.valueOf(cnum));

                        dbHelper.insertcourse_trinee_admin(t,c);
                        dbHelper.removewaitingtrinee_byemail(t,c);

                        secondLinearLayout.removeView(horizantal);
                        NOTIFICATION_TITLE_accepet = "YOU ARE ACCEPTED!!";
                        NOTIFICATION_BODY_accept[accept-100] = "YOU ARE ACCEPTED IN "+c.getCtitle()+" COURSE!!";
                        ++accept;
                    }
                });
                horizantal.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border));

            secondLinearLayout.addView(horizantal);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applied_reject_student, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearL);
    }
}