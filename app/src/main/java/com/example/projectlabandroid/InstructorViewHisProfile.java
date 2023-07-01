package com.example.projectlabandroid;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructorViewHisProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructorViewHisProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InstructorViewHisProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstructorViewHisProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static InstructorViewHisProfile newInstance(String param1, String param2) {
        InstructorViewHisProfile fragment = new InstructorViewHisProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView emailEditText = (TextView) getActivity().findViewById(R.id.email_address1);
        emailEditText.setText(instructorsignin.ins.getEmail());

        TextView firstNameEditText = (TextView) getActivity().findViewById(R.id.fisrt_address1);
        firstNameEditText.setText(instructorsignin.ins.getFirst_name());

        TextView lastNameEditText = (TextView) getActivity().findViewById(R.id.last_address2);
        lastNameEditText.setText(instructorsignin.ins.getLast_name());


        TextView mobileEditText = (TextView) getActivity().findViewById(R.id.phone_address1);
        mobileEditText.setText(instructorsignin.ins.getMobile_number());

        TextView addressEditText = (TextView) getActivity().findViewById(R.id.address3);
        addressEditText.setText(instructorsignin.ins.getAddress());

        TextView degreeEditText = (TextView) getActivity().findViewById(R.id.degree);
        degreeEditText.setText(instructorsignin.ins.getDegree());

        TextView specialEditText = (TextView) getActivity().findViewById(R.id.graduate);
        specialEditText.setText(instructorsignin.ins.getSpecialization());

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        String email = instructorsignin.ins.getEmail();
        Cursor cursor = dbHelper.getAllCoursesforInstructor(email);
        TextView corsesEditText = (TextView) getActivity().findViewById(R.id.list);
        corsesEditText.setText("");


        while (cursor.moveToNext()) {
            @SuppressLint("Range") String cTitle = cursor.getString(cursor.getColumnIndex("Ctitle"));
            String currentText = corsesEditText.getText().toString();
            String newText = currentText + cTitle + ", \n";
            corsesEditText.setText(newText);
        }

        cursor.close();

        ImageView image_view2 = (ImageView) getActivity().findViewById(R.id.imageView15);

        byte [] bytes = instructorsignin.ins.getPhoto();
        if(bytes != null ){
            Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            image_view2.setImageBitmap(bitmapImageDB);
        }

        Button b = (Button) getActivity().findViewById(R.id.edit_profile1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new EditInstructorProfile());
                fragmentTransaction.commit();
            }
        });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_view_his_profile, container, false);
    }
}