package com.example.projectlabandroid;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProfile.
     */
    LinearLayout secondLinearLayout;

    // TODO: Rename and change types and number of parameters
    public static ViewProfileFragment newInstance(String param1, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
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

     //   secondLinearLayout = getActivity().findViewById(R.id.secondLinearLayout);

    }

    @Override
    public void onResume() {
        super.onResume();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        Cursor allCourses = dbHelper.getAllInstructors();
        int borderColor = getResources().getColor(R.color.purple_200);
       // GradientDrawable border = new GradientDrawable();
       // border.setStroke(5, borderColor);
      //  border.setCornerRadius(25);
        secondLinearLayout.removeAllViews();
        secondLinearLayout.setGravity(Gravity.CENTER);
        // secondLinearLayout.setBackground(border);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 700);
        layoutParams.setMargins(0, 30, 0, 0);
        // secondLinearLayout.setLayoutParams(layoutParams);
        while(allCourses.moveToNext()){
            TextView textView = new TextView(requireContext());
            ImageView imageView = new ImageView(requireContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            LinearLayout horizantal = new LinearLayout(requireContext());
            horizantal.setOrientation(LinearLayout.HORIZONTAL);
            horizantal.setGravity(Gravity.CENTER); // Set the gravity to center horizontally

            byte [] bytes = allCourses.getBlob(4);
            Bitmap bitmapImageDB = null;
            if (bytes != null) {
                bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmapImageDB);
               // secondLinearLayout.addView(imageView);

            } else {
                imageView.setImageResource(R.drawable.profile);
                //secondLinearLayout.addView(imageView);
            }

            textView.setTextColor(borderColor);
            //textView.setLayoutParams(layoutParams);
            textView.append("Email: "+allCourses.getString(0) +
                    "\nFirst Name: "+allCourses.getString(1)+
                    "\nLast Name: "+allCourses.getString(2)
                    +"\nMobile Number: "+allCourses.getString(5)
                    +"\nAddress: "+allCourses.getString(6)+
                    "\n"  );

            horizantal.addView(imageView);
            horizantal.addView(textView);
            horizantal.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.border)); // Set the border as desired (create a drawable XML file)
            secondLinearLayout.addView(horizantal);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Adjust the text size as desired
            textView.setGravity(Gravity.CENTER); // Set the text alignment to center


           // secondLinearLayout.addView(textView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout);
    }


}