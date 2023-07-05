package com.example.projectlabandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
  //  String[] text2Elements;
    String preq;
    public static boolean isapplied= false;
    public static boolean time_conflict= false;
    TextView text2;

    Course c ;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView text = (TextView) getActivity().findViewById(R.id.materials);
         text2 = (TextView) getActivity().findViewById(R.id.textView9);
        course_titel = (TextView) getActivity().findViewById(R.id.textView13);
        Button back = (Button)  getActivity().findViewById(R.id.back);

        course_titel.setText(student_apply_for_course.c2.getCtitle());

        String [] items = {"Java", "C", "Calculus I", "Calculus II", "Physics I", "Physics II", "Data Structure","Differential","Linear","Statistics"};

        boolean[] selecteditems = new boolean[items.length];
        ArrayList<Integer> itemsList = new ArrayList<>();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

        c = new Course();
        c.setCtitle("Web");


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


                String preq = dbHelper.preq(student_apply_for_course.c2.getCtitle());

                preq =preq.replaceAll("\\s+", "").replaceAll(",", "");
                string = string.replaceAll("\\s+", "").replaceAll(",", "");
                String[] text2Elements = string.split(",");
                String[] text1Elements = preq.split(",");

                Set<String> set1 = new HashSet<>(Arrays.asList(text1Elements));
                Set<String> set2 = new HashSet<>(Arrays.asList(text2Elements));
                text2.setText(preq +"\n" + string +"\n");
//                text2.append("\n");
//                text2.append(string);
//                text2.append("\n");

                if (set1.equals(set2) && time_conflict == false) {
                    Toast.makeText(requireContext(), "You  applied to this course", Toast.LENGTH_SHORT).show();
                    dbHelper.insertcourse_trinee(trineelogin.tr,student_apply_for_course.c2);
                }
                else if(time_conflict == true){
                    Toast.makeText(requireContext(), "there is a time conflict", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                }
                else {
                    Toast.makeText(requireContext(), "You cant applied to this course,You did not finish the prerequisites", Toast.LENGTH_SHORT).show();
                  //  text2.append("The contents of the texts are not the same.");
                }
                string = "";
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new student_apply_for_course());
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_cretae_applied_student, container, false);

        return view;
    }

    public void onResume() {
        super.onResume();

        Toast toast = Toast.makeText(getActivity(), "inside the resume function true", Toast.LENGTH_SHORT);
        toast.show();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        Cursor allCourses = dbHelper.getAllAvailableCourses_trinee(trineelogin.tr);

        while (allCourses.moveToNext()) {
//            text2.setText("\nstudent Name: "+allCourses.getString(2)+
//                    "\nCourse Title: "+allCourses.getString(1)+
//                    "\n Time: "+allCourses.getString(3)+
//                    "\n");
//            text2.append(student_apply_for_course.c2.getSchedule());
//            text2.append("\n");
//            text2.append(allCourses.getString(3));


            String current = allCourses.getString(3);
//            text2.append("current "+current);
//            text2.append("\n");
//            text2.append("mesh current "+student_apply_for_course.c2.getSchedule());
            if(current.equals(student_apply_for_course.c2.getSchedule()) && trineelogin.tr.getEmail()==allCourses.getString(2) && student_apply_for_course.c2 != null ){
                time_conflict=true;
            }
       }
    }

        }