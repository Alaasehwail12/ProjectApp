package com.example.projectlabandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TraineeProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TraineeProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TraineeProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TraineeProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static TraineeProfile newInstance(String param1, String param2) {
        TraineeProfile fragment = new TraineeProfile();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_trainee_profile, container, false);

        TextView emailEditText = (TextView) v.findViewById(R.id.email_address);
        emailEditText.setText(trineelogin.tr.getEmail());

        TextView firstNameEditText = (TextView) v.findViewById(R.id.fisrt_address);
        firstNameEditText.setText(trineelogin.tr.getFirst_name());

        TextView lastNameEditText = (TextView) v.findViewById(R.id.last_address);
        lastNameEditText.setText(trineelogin.tr.getLast_name());


        TextView mobileEditText = (TextView) v.findViewById(R.id.phone_address);
        mobileEditText.setText(trineelogin.tr.getMobile_number());

        TextView addressEditText = (TextView) v.findViewById(R.id.address);
        addressEditText.setText(trineelogin.tr.getAddress());

        ImageView image_view2 = (ImageView) v.findViewById(R.id.imageView14);


        byte [] bytes = trineelogin.tr.getPhoto();

        if(bytes != null){
            Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            image_view2.setImageBitmap(bitmapImageDB);
        }else{
            image_view2.setImageResource(R.drawable.user_phue);
        }

        Button b = (Button) v.findViewById(R.id.edit_profile);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new EdittraineeProfile());
                fragmentTransaction.commit();
            }
        });

        return v;
    }

}