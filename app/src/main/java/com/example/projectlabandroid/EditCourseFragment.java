package com.example.projectlabandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCourseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DataBaseHelper dbHelper ;

    private ImageView imagephoto ;

    private Bitmap bitmap;
    private byte [] bytes;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    public EditCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditCourseFragment newInstance(String param1, String param2) {
        EditCourseFragment fragment = new EditCourseFragment();
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


        EditText courseTpoics = (EditText) getActivity().findViewById(R.id.editTextTextPersonName2);
        EditText courseTitle = (EditText) getActivity().findViewById(R.id.editTextTextPersonName);
        ImageView preq = (ImageView) getActivity().findViewById(R.id.imageView8);
        TextView preqe = (TextView) getActivity().findViewById(R.id.editTextTextPersonName3);
        imagephoto = (ImageView) getActivity().findViewById(R.id.imageView6);
        Button uploadphoto = (Button) getActivity().findViewById(R.id.button4);
        Button addcourse = (Button) getActivity().findViewById(R.id.button5);

        CalendarView deadline =(CalendarView) getActivity().findViewById(R.id.etDeadline);
        CalendarView courseStartDate =(CalendarView) getActivity().findViewById(R.id.etStartDate);
        TimePicker schedule = (TimePicker)getActivity().findViewById(R.id.etCourseSchedule);
        EditText venue = (EditText) getActivity().findViewById(R.id.etVenue);

        final String[] prequsites_string = {""};

        Course course = new Course();
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);


        imagephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });



        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream byteoutput;
                byteoutput = new ByteArrayOutputStream();
                if(bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteoutput);
                    bytes = byteoutput.toByteArray();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        final String base64image = Base64.getEncoder().encodeToString(bytes);
                    }
                    // Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }
                course.setPhoto(bytes);
            }
        });

        deadline.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                course.setDeadline(selectedDate);
            }
        });

        courseStartDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                course.setStartDateCourse(selectedDate);
            }
        });

        schedule.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Handle the selected time
                String selectedTime = hourOfDay + ":" + minute;
                course.setSchedule(selectedTime);
            }
        });

        String [] prequisites = {"Java", "C", "Calculus I", "Calculus II", "Physics I", "Physics II", "Data Structure","Differential","Linear","Statistics"};

        boolean[] selectedprequisites = new boolean[prequisites.length];
        ArrayList<Integer> prequisitesList = new ArrayList<>();

        preq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose the Prerequisites for each course:");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(prequisites, selectedprequisites, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // Item selected
                            prequisitesList.add(which);
                            Collections.sort(prequisitesList);
                        } else {
                            // Item deselected
                            prequisitesList.remove(Integer.valueOf(which));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < prequisites.length; i++) {
                            if (selectedprequisites[i]){
                                prequsites_string[0] += prequisites[i]+" , ";
                            }
                        }
                        preqe.append(prequsites_string[0]);
                        course.setPrerequisites(prequsites_string[0]);
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
                        Arrays.fill(selectedprequisites, false);
                        prequisitesList.clear();
                    }
                });

                builder.show();
            }
        });



        addcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                course.setCtitle(courseTitle.getText().toString());
                course.setCTopics(courseTpoics.getText().toString());
                course.setVenue(venue.getText().toString());
                Toast toast =Toast.makeText(getActivity(),"You create a course successfully!",Toast.LENGTH_SHORT);
                toast.show();
                dbHelper.insertCourse(course);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new CourseFragment());
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_course, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        Course newCourse = new Course();
        dbHelper.editCoursebyCnum(CourseFragment.course, newCourse);
        CourseFragment.course = newCourse;
    }
}

