package com.example.projectlabandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link trineelogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class trineelogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public trineelogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment trineelogin.
     */
    // TODO: Rename and change types and number of parameters
    public static trineelogin newInstance(String param1, String param2) {
        trineelogin fragment = new trineelogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static trainee tr = new trainee();

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataBaseHelper dbHelper =new DataBaseHelper(requireContext(),"Database", null,1);
        EditText email = (EditText) getActivity().findViewById(R.id.emailtrainee);
        EditText pass = (EditText) getActivity().findViewById(R.id.pass_trainee);
        CheckBox rememberme = (CheckBox) getActivity().findViewById(R.id.remembermetrainne);
        Button signin =(Button) getActivity().findViewById(R.id.signintainee);
        Button signUP =(Button) getActivity().findViewById(R.id.signuptrinee2);

        rememberme.setChecked(true);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(requireContext());
        if (!Objects.equals(sharedPrefManager.readString("email", "noValue"), "noValue")){
            email.setText(sharedPrefManager.readString("email", "noValue"));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = email.getText().toString().trim();
                String enterPassword = pass.getText().toString().trim();

                if (!dbHelper.istraineeRegistered(enteredEmail))
                {
                    Toast.makeText(requireContext(), "This email is not registered for trainee!", Toast.LENGTH_SHORT).show();
                }else if(!dbHelper.correcttranieeSignIn(enteredEmail, enterPassword)){
                    Toast.makeText(requireContext(), "Incorrect password for trainee!", Toast.LENGTH_SHORT).show();
                }else{
                    if (rememberme.isChecked())
                        sharedPrefManager.writeString("email", email.getText().toString().trim());
                    else
                        sharedPrefManager.writeString("email", "noValue");

                    //email.setText("alaa@gmail.com");
                    tr= dbHelper.gettraineeByEmail(email.getText().toString().trim());
                    Toast.makeText(requireContext(), "the login procsess succesfull for trainee!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(requireContext(), HomeActivityTrainee.class));
                }
            }
        });


        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), SignUp.class));
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
        return inflater.inflate(R.layout.fragment_trineelogin, container, false);
    }
}