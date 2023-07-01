package com.example.projectlabandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link create_course_avaliable_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create_course_avaliable_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public create_course_avaliable_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment create_course_avaliable_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static create_course_avaliable_fragment newInstance(String param1, String param2) {
        create_course_avaliable_fragment fragment = new create_course_avaliable_fragment();
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
    public  String[] options = null;
    public List<String>  instructorNames;
    public String course_t;
    ArrayAdapter<String> objdegreeArr;

    Course c;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText course_title = (EditText) getActivity().findViewById(R.id.editTextTextPersonName);
        Button getnames= (Button) getActivity().findViewById(R.id.button6);
        Button done= (Button) getActivity().findViewById(R.id.button7);
        Button get= (Button) getActivity().findViewById(R.id.button8);
        final Spinner degreeSpinner =(Spinner)getActivity().findViewById(R.id.spinner2);

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course_t = course_title.getText().toString();
                instructorNames = get_names(course_t);
                instructorNames.add(" ");
                options=instructorNames.toArray(new String[instructorNames.size()]);
            }
        });

        getnames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               objdegreeArr = new ArrayAdapter<>(requireContext(),android.R.layout.simple_spinner_item, options);
                degreeSpinner.setAdapter(objdegreeArr);
                c = dbHelper.getAllCoursesbytitel(course_t);
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(degreeSpinner.getSelectedItem().toString().equals(" ")){
                    Toast toast =Toast.makeText(getActivity(),"No instructors to tech that course",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    dbHelper.insertavailableCourse(c,degreeSpinner.getSelectedItem().toString());
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new make_courses_avaliableFragment());
                    fragmentTransaction.commit();
                }


            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_course_avaliable_fragment, container, false);
    }



    public List<String> get_names(String name){
        List<String>  instructorNames2;
       // instructorNames2 = new ArrayList<>();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        instructorNames2= dbHelper.make_course_avalabile(name);
        return instructorNames2;
        //options = instructorNames.toArray(new String[instructorNames.size()]);
    }
}