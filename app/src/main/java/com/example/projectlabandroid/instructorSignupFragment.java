package com.example.projectlabandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link instructorSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class instructorSignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  ImageView image ;

    public instructorSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment instructorSignup.
     */
    // TODO: Rename and change types and number of parameters
    public static instructorSignupFragment newInstance(String param1, String param2) {
        instructorSignupFragment fragment = new instructorSignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private ActivityResultLauncher<Intent> activityResultLauncher2;
    boolean isButtonNotPressed = false;

    private Bitmap bitmap;
    private byte [] bytes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                        image.setImageBitmap(bitmap);
                    }catch (IOException a){
                        a.printStackTrace();

                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        EditText emailEditText = (EditText) getActivity().findViewById(R.id.editTextText4);
        EditText firstNameEditText = (EditText) getActivity().findViewById(R.id.firstname);
        EditText lastNameEditText = (EditText) getActivity().findViewById(R.id.lastname);
        EditText passwordEditText = (EditText) getActivity().findViewById(R.id.password);
        EditText confirmPasswordEditText = (EditText) getActivity().findViewById(R.id.cpassword);
        EditText mobileEditText = (EditText) getActivity().findViewById(R.id.phone);
        EditText addressEditText = (EditText) getActivity().findViewById(R.id.address1);
        EditText specilazationEditText = (EditText) getActivity().findViewById(R.id.specialliaztion);
        TextView listText = (TextView) getActivity().findViewById(R.id.editTextText11);
        Button photo_button = (Button) getActivity().findViewById(R.id.button3);
        image = (ImageView) getActivity().findViewById(R.id.imageupload1);
        Button signUpButton = (Button) getActivity().findViewById(R.id.signupbtn);
        String[] options = { " ","BSc", "MSc" , "PhD" };
        final Spinner degreeSpinner =(Spinner)getActivity().findViewById(R.id.spinner);
        ArrayAdapter<String> objdegreeArr = new ArrayAdapter<>(requireContext(),android.R.layout.simple_spinner_item, options);
        degreeSpinner.setAdapter(objdegreeArr);
        TextView error = (TextView) getActivity().findViewById(R.id.error1);


        String [] courses = {"Java programming", "C programming", "C++ programming", "Python", "AI", "Project Management", "C#","Javascript","Frontend","Web","Backend","DataEngineering"};
        String[] updatedCourses = new String[courses.length + 1];

        boolean[] selectedCoures = new boolean[updatedCourses.length];
        ArrayList <Integer> coursesList = new ArrayList<>();

        listText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose the Courses you want to teach:");
                builder.setCancelable(false);

                // Create a ScrollView
//                ScrollView scrollView = new ScrollView(requireContext());
//
//                // Create a LinearLayout to hold the content inside the ScrollView
//                LinearLayout linearLayout = new LinearLayout(requireContext());
//                linearLayout.setOrientation(LinearLayout.VERTICAL);

                // Create the inputEditText
                final EditText inputEditText = new EditText(requireContext());
                inputEditText.setHint("Others: Enter course");
                inputEditText.setTextSize(20);
                builder.setView(inputEditText);
                System.arraycopy(courses, 0, updatedCourses, 0, courses.length);
                updatedCourses[courses.length] = inputEditText.getText().toString();

                //   courses;

                // Add the inputEditText to the LinearLayout
//                linearLayout.addView(inputEditText);

                builder.setMultiChoiceItems(updatedCourses, selectedCoures, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // Item selected
                            coursesList.add(which);
                            Collections.sort(coursesList);
                        } else {
                            // Item deselected
                            coursesList.remove(Integer.valueOf(which));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
                        Arrays.fill(selectedCoures, false);
                        coursesList.clear();
                    }
                });



                builder.show();
            }

        });



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher2.launch(intent);
            }
        });
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
        instructor user = new instructor();

        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream byteoutput;
                byteoutput = new ByteArrayOutputStream();
                isButtonNotPressed = true;
                if(bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteoutput);
                    bytes = byteoutput.toByteArray();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        final String base64image = Base64.getEncoder().encodeToString(bytes);

                    }
                    // Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }
                else{
                }

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] somethingWrong = {false};
                final String[] errorMessage = new String[1];
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setStroke(5, Color.RED);
                drawable.setCornerRadius(10f);
                GradientDrawable d2 = new GradientDrawable();
                d2.setShape(GradientDrawable.RECTANGLE);
                d2.setStroke(3, Color.blue(5));
                d2.setCornerRadius(10f);

                if(degreeSpinner.getSelectedItem().toString() == " "){
                    degreeSpinner.setBackground(drawable);
                }
                else{
                    degreeSpinner.setBackground(d2);
                }


                if (!isButtonNotPressed) {
                    image.setBackground(drawable);
                }
                else{
                    image.setBackground(d2);
                }

                if (firstNameEditText.getText().toString().trim().length() < 3 || firstNameEditText.getText().toString().trim().length() > 20) {
                    firstNameEditText.setBackground(drawable);

                }else {
                    firstNameEditText.setBackground(d2);
                }

                if (lastNameEditText.getText().toString().trim().length() < 3 || lastNameEditText.getText().toString().trim().length() >20) {
                    lastNameEditText.setBackground(drawable);
                } else {
                    lastNameEditText.setBackground(d2);
                }

                if (!isEmailValid(emailEditText.getText().toString().trim())) {
                    emailEditText.setBackground(drawable);
                } else {
                    emailEditText.setBackground(d2);
                }

                if (!isPasswordValid(passwordEditText.getText().toString())) {
                    passwordEditText.setBackground(drawable);
                } else {
                    passwordEditText.setBackground(d2);
                }

                if (!isPasswordValid(confirmPasswordEditText.getText().toString()) || !confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString())) {
                    confirmPasswordEditText.setBackground(drawable);
                } else {
                    confirmPasswordEditText.setBackground(d2);
                }

                if ((mobileEditText.getText().toString()).equals("")) {
                    mobileEditText.setBackground(drawable);
                } else {
                    mobileEditText.setBackground(d2);
                }

                if ((addressEditText.getText().toString()).equals("")) {
                    addressEditText.setBackground(drawable);
                } else {
                    addressEditText.setBackground(d2);
                }

                if ((specilazationEditText.getText().toString()).equals("")) {
                    specilazationEditText.setBackground(drawable);
                } else {
                    specilazationEditText.setBackground(d2);
                }
//                if ((listEditText.getText().toString()).equals("")) {
//                    listEditText.setBackground(drawable);
//                } else {
//                    listEditText.setBackground(d2);
//                }





                if (!isEmailValid(emailEditText.getText().toString().trim())) {
                    somethingWrong[0] = true;
                    errorMessage[0] = "Email Address should be in correct format.";
                } else {
                    user.setEmail(emailEditText.getText().toString().trim());
                }


                if (!somethingWrong[0]) {
                    if (firstNameEditText.getText().toString().trim().length() < 3) {
                        somethingWrong[0] = true;
                        errorMessage[0] = "First Name should be at least 3 characters.";
                    } else {
                        user.setFirst_name(firstNameEditText.getText().toString());
                    }

                    if (!somethingWrong[0]) {
                        if (lastNameEditText.getText().toString().trim().length() < 3) {
                            somethingWrong[0] = true;
                            errorMessage[0] = "Last Name should be at least 3 characters.";
                        } else {
                            user.setLast_name(lastNameEditText.getText().toString());
                        }

                        if (!somethingWrong[0]) {
                            if (!isPasswordValid(passwordEditText.getText().toString().trim())) {
                                somethingWrong[0] = true;
                                errorMessage[0] = "The password must contain at least one number, one lowercase letter, and one uppercase letter.";
                            } else {
                                user.setPassword(passwordEditText.getText().toString());
                            }

                            if (!somethingWrong[0]) {
                                if (!confirmPasswordEditText.getText().toString().trim().equals(passwordEditText.getText().toString().trim())) {
                                    somethingWrong[0] = true;
                                    errorMessage[0] = "The password does not match the confirmation.";
                                } else {
                                    user.setPassword(confirmPasswordEditText.getText().toString());
                                }
                                if (!somethingWrong[0]) {
                                    if ((mobileEditText.getText().toString()).equals("")) {
                                        somethingWrong[0] = true;
                                        errorMessage[0] = "Enter the Mobile Number please.";
                                    } else {
                                        user.setMobile_number(mobileEditText.getText().toString());
                                    }


                                    if (!somethingWrong[0]) {
                                        if (addressEditText.getText().toString().equals("")) {
                                            somethingWrong[0] = true;
                                            errorMessage[0] = "Enter the Address please.";
                                        } else {
                                            user.setAddress(addressEditText.getText().toString());
                                        }
                                        if (!somethingWrong[0]) {
                                            if (specilazationEditText.getText().toString().equals("")) {
                                                somethingWrong[0] = true;
                                                errorMessage[0] = "Enter the Specialization please.";
                                            } else {
                                                user.setSpecialization(specilazationEditText.getText().toString());
                                            }
                                            if (!somethingWrong[0]) {
                                                if (degreeSpinner.getSelectedItem().toString() == " ") {
                                                    somethingWrong[0] = true;
                                                    errorMessage[0] = "You should select a degree .";
                                                } else {
                                                    user.setDegree(degreeSpinner.getSelectedItem().toString());
                                                }
                                                if (!somethingWrong[0]) {
                                                    if (coursesList.size() < 1) {
                                                        somethingWrong[0] = true;
                                                        errorMessage[0] = "You should select at least one course .";
                                                    } else {
                                                        for (int i = 0; i < updatedCourses.length; i++) {
                                                            if (selectedCoures[i]){
                                                                dbHelper.insert_instructor_courses(user, updatedCourses[i]);
                                                            }
                                                        }
                                                    }

                                                    if (!somethingWrong[0]) {
                                                        if (!isButtonNotPressed) {
                                                            somethingWrong[0] = true;
                                                            errorMessage[0] = "You should click on the upload Photo first .";
                                                        } else {
                                                            user.setPhoto(bytes);
                                                            dbHelper.insertinstructor(user);

                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

                Cursor allInstructorCursor = dbHelper.getAllInstructors();
                while (allInstructorCursor.moveToNext()){ //this function allow me to move between data

                    System.out.println("Email= "+allInstructorCursor.getString(0) +"\nFirstName= "+allInstructorCursor.getString(1)
                            +"\nLastName= "+allInstructorCursor.getString(2)
                            +"\nPassword= "+allInstructorCursor.getString(3)
                            +"\nPhoto= "+allInstructorCursor.getBlob(4)+
                            "\nMobile Number= "+allInstructorCursor.getString(5)+
                            "\nAddress= "+allInstructorCursor.getString(6)+
                            "\nSpecialization= "+allInstructorCursor.getString(7)+
                            "\nDegree= "+allInstructorCursor.getString(8)+
                            "\n\n" );
                }
                if (somethingWrong[0]) {
                    error.setTextColor(Color.RED);
                    error.setText(errorMessage[0]);
                    Toast toast =Toast.makeText(getActivity(),"Error in Information",Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    error.setTextColor(Color.BLACK);
                    error.setText("Signing up is done successfully!");
                    Toast toast =Toast.makeText(getActivity(),"Signing up is done successfully!",Toast.LENGTH_SHORT);
                    toast.show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(  requireContext());
                    builder.setTitle("Login");
                    builder.setMessage("Do you want to login in !!");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(requireContext(), logIn.class));

                            firstNameEditText.setText("");
                            lastNameEditText.setText("");
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            confirmPasswordEditText.setText("");
                            mobileEditText.setText("");
                            addressEditText.setText("");
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();


                }

            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_signup, container, false);
    }

    static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).{8,15}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

}