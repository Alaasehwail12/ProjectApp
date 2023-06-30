package com.example.projectlabandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cretae_applied_student#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cretae_applied_student extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cretae_applied_student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cretae_applied_student.
     */
    // TODO: Rename and change types and number of parameters
    public static cretae_applied_student newInstance(String param1, String param2) {
        cretae_applied_student fragment = new cretae_applied_student();
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


    public static String string = "";
    TextView course_titel;
    String[] text2Elements;
    String preq;
    public static boolean isapplied= false;

    String value="";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView text = (TextView) getActivity().findViewById(R.id.materials);
        TextView text2 = (TextView) getActivity().findViewById(R.id.textView9);
        course_titel = (TextView) getActivity().findViewById(R.id.textView13);
        Button back = (Button)  getActivity().findViewById(R.id.back);


        String [] items = {"Java", "C", "Calculus I", "Calculus II", "Physics I", "Physics II", "Data Structure","Differential","Linear","Statistics"};

        boolean[] selecteditems = new boolean[items.length];
        ArrayList<Integer> itemsList = new ArrayList<>();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose the finished materials:");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(items, selecteditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // Item selected
                            itemsList.add(which);
                            Collections.sort(itemsList);
                        } else {
                            // Item deselected
                            itemsList.remove(Integer.valueOf(which));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < items.length; i++) {
                            if (selecteditems[i]){
                                string += items[i]+",";

                            }
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Arrays.fill(selecteditems, false);
                        itemsList.clear();
                    }
                });

                builder.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = getArguments();
                if (b != null) {
                    value= b.getString("title");
                    Toast.makeText(requireContext() , "the args is not null", Toast.LENGTH_SHORT).show();
                }
                course_titel.setText(value);
//                text2.append(preq);
//                text2.append("\n");
//                text2.append(string);
//                preq =preq.replaceAll("\\s+", "").replaceAll(",", "");
//                string = string.replaceAll("\\s+", "").replaceAll(",", "");
//                text2Elements = string.split(",");
//                String[] text1Elements = preq.split(",");
//
//                Set<String> set1 = new HashSet<>(Arrays.asList(text1Elements));
//                Set<String> set2 = new HashSet<>(Arrays.asList(text2Elements));
//                text2.append("\n");
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout1, new student_apply_for_course());
//                fragmentTransaction.commit();

//                if (set1.equals(set2)) {
//                    text2.append("The contents of the texts are the same.");
//                } else {
//                    text2.append("The contents of the texts are not the same.");
//                    ;
//                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_cretae_applied_student, container, false);

        return view;
    }

}