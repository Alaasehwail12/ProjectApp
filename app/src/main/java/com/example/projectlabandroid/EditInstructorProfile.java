package com.example.projectlabandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Use the {@link EditInstructorProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditInstructorProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView image_view2 ;
    private ActivityResultLauncher<Intent> activityResultLauncher2;
    boolean isButtonNotPressed = false;

    private Bitmap bitmap;
    private byte [] bytes;
    public static instructor user = new instructor();

    public static instructor newuser = new instructor();


    public EditInstructorProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditInstructorProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EditInstructorProfile newInstance(String param1, String param2) {
        EditInstructorProfile fragment = new EditInstructorProfile();
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
        activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                        image_view2.setImageBitmap(bitmap);
                    }catch (IOException a){
                        a.printStackTrace();

                    }
                }
            }
        });

    }
    public static String list_string = "";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText emailEditText = (EditText) getActivity().findViewById(R.id.editTextText4);
        emailEditText.setText(instructorsignin.ins.getEmail());

        EditText firstNameEditText = (EditText) getActivity().findViewById(R.id.firstname);
        firstNameEditText.setText(instructorsignin.ins.getFirst_name());

        EditText lastNameEditText = (EditText) getActivity().findViewById(R.id.lastname);
        lastNameEditText.setText(instructorsignin.ins.getLast_name());

        EditText passwordEditText = (EditText) getActivity().findViewById(R.id.password);
        passwordEditText.setText(instructorsignin.ins.getPassword());

        EditText confirmPasswordEditText = (EditText) getActivity().findViewById(R.id.cpassword);
        confirmPasswordEditText.setText(instructorsignin.ins.getPassword());

        EditText mobileEditText = (EditText) getActivity().findViewById(R.id.phone);
        mobileEditText.setText(instructorsignin.ins.getMobile_number());

        EditText addressEditText = (EditText) getActivity().findViewById(R.id.address1);
        addressEditText.setText(instructorsignin.ins.getAddress());

        EditText specializationEditText = (EditText) getActivity().findViewById(R.id.specialliaztion);
        specializationEditText.setText(instructorsignin.ins.getSpecialization());

        EditText listEditText = (EditText) getActivity().findViewById(R.id.editTextText11);
        String listCourses= instructorSignupFragment.list_string;
        listEditText.append(listCourses);

        ImageView listimg = (ImageView) getActivity().findViewById(R.id.imageView13);


        image_view2 = (ImageView) getActivity().findViewById(R.id.imageupload1);
        TextView error = (TextView)  getActivity().findViewById(R.id.error1);

        String[] options = { "BSc", "MSc" , "PhD" };
        final Spinner degreeSpinner =(Spinner)getActivity().findViewById(R.id.spinner);
        ArrayAdapter<String> objdegreeArr = new ArrayAdapter<>(requireContext(),android.R.layout.simple_spinner_item, options);
        degreeSpinner.setAdapter(objdegreeArr);

        String [] courses = {"Java programming", "C programming", "C++ programming", "Python", "AI", "Project Management", "C#","Javascript","Frontend","Web","Backend","DataEngineering"};
        String[] updatedCourses = new String[courses.length + 1];

        boolean[] selectedCoures = new boolean[updatedCourses.length];
        ArrayList<Integer> coursesList = new ArrayList<>();


        Button up= (Button) getActivity().findViewById(R.id.save_profile);
        Button canel= (Button) getActivity().findViewById(R.id.cancle);
        Button photo_button= (Button) getActivity().findViewById(R.id.button3);

        listimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose the Courses you want to teach:");
                builder.setCancelable(false);

                final EditText inputEditText = new EditText(requireContext());
                inputEditText.setHint("Others: Enter course");
                inputEditText.setTextSize(20);
                builder.setView(inputEditText);
                System.arraycopy(courses, 0, updatedCourses, 0, courses.length);
                updatedCourses[courses.length] = inputEditText.getText().toString();

                builder.setMultiChoiceItems(updatedCourses, selectedCoures, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            coursesList.add(which);
                            Collections.sort(coursesList);
                        } else {
                            coursesList.remove(Integer.valueOf(which));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < updatedCourses.length; i++) {
                            if (selectedCoures[i]){
                                list_string += updatedCourses[i]+" , ";
                            }
                        }
                         listEditText.append(list_string);
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

        image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher2.launch(intent);
            }
        });

        byte [] bytes = trineelogin.tr.getPhoto();
        if(bytes != null ){
            Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            image_view2.setImageBitmap(bitmapImageDB);
        }




        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new TraineeProfile());
                fragmentTransaction.commit();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
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
                d2.setStroke(5, Color.blue(5));
                d2.setCornerRadius(10f);

                if (!isButtonNotPressed) {
                    image_view2.setBackground(drawable);

                }
                else{
                    image_view2.setBackground(d2);
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

                if ((specializationEditText.getText().toString()).equals("")) {
                    specializationEditText.setBackground(drawable);
                } else {
                    specializationEditText.setBackground(d2);
                }



                DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);

                if (!isEmailValid(emailEditText.getText().toString().trim())) {
                    somethingWrong[0] = true;
                    errorMessage[0] = "Email Address should be in correct format.";
                } else {
                    newuser.setEmail(emailEditText.getText().toString());
                }


                if (!somethingWrong[0]) {
                    if (firstNameEditText.getText().toString().trim().length() < 3) {
                        somethingWrong[0] = true;
                        errorMessage[0] = "First Name should be at least 3 characters.";
                    } else {
                        newuser.setFirst_name(firstNameEditText.getText().toString());
                    }

                    if (!somethingWrong[0]) {
                        if (lastNameEditText.getText().toString().trim().length() < 3) {
                            somethingWrong[0] = true;
                            errorMessage[0] = "Last Name should be at least 3 characters.";
                        } else {
                            newuser.setLast_name(lastNameEditText.getText().toString());
                        }

                        if (!somethingWrong[0]) {
                            if (!isPasswordValid(passwordEditText.getText().toString().trim())) {
                                somethingWrong[0] = true;
                                errorMessage[0] = "The password must contain at least one number, one lowercase letter, and one uppercase letter.";
                            } else {
                                newuser.setPassword(passwordEditText.getText().toString());
                            }

                            if (!somethingWrong[0]) {
                                if (!confirmPasswordEditText.getText().toString().trim().equals(passwordEditText.getText().toString().trim())) {
                                    somethingWrong[0] = true;
                                    errorMessage[0] = "The password does not match the confirmation.";
                                } else {
                                    newuser.setPassword(confirmPasswordEditText.getText().toString());
                                }
                                if (!somethingWrong[0]) {
                                    if ((mobileEditText.getText().toString()).equals("")) {
                                        somethingWrong[0] = true;
                                        errorMessage[0] = "Enter the Mobile Number please.";
                                    } else {
                                        newuser.setMobile_number(mobileEditText.getText().toString());
                                    }


                                    if (!somethingWrong[0]) {
                                        if (addressEditText.getText().toString().equals("")) {
                                            somethingWrong[0] = true;
                                            errorMessage[0] = "Enter the Address please.";
                                        } else {
                                            newuser.setAddress(addressEditText.getText().toString());
                                        }
                                        if (!somethingWrong[0]) {
                                            if (specializationEditText.getText().toString().equals("")) {
                                                somethingWrong[0] = true;
                                                errorMessage[0] = "Enter the Specialization please.";
                                            } else {
                                                newuser.setSpecialization(specializationEditText.getText().toString());
                                            }
                                            if (!somethingWrong[0]) {
                                                if (degreeSpinner.getSelectedItem().toString() == " ") {
                                                    somethingWrong[0] = true;
                                                    errorMessage[0] = "You should select a degree .";
                                                } else {
                                                    newuser.setDegree(degreeSpinner.getSelectedItem().toString());
                                                }
                                                if (!somethingWrong[0]) {
                                                    dbHelper.removeCourse_instructor();
                                                    if (coursesList.size() < 1) {
                                                        somethingWrong[0] = true;
                                                        errorMessage[0] = "You should select at least one course .";
                                                    } else {
                                                        for (int i = 0; i < updatedCourses.length; i++) {
                                                            if (selectedCoures[i]){
                                                               // String email = instructorsignin.ins.getEmail();
                                                                dbHelper.insert_instructor_courses(newuser,updatedCourses[i]);
                                                            }
                                                        }
                                                    }

                                                    if (!somethingWrong[0]) {
                                                        if (!isButtonNotPressed) {
                                                            somethingWrong[0] = true;
                                                            errorMessage[0] = "You should click on the upload Photo first .";
                                                        } else {
                                                            newuser.setPhoto(bytes);
                                                            String email = instructorsignin.ins.getEmail();
                                                            dbHelper.editinstructor(newuser,email);
                                                            instructorsignin.ins=newuser;
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

                if (somethingWrong[0]) {
                    error.setTextColor(Color.RED);
                    error.setText(errorMessage[0]);
                    Toast toast =Toast.makeText(getActivity(),"Error in Information",Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    error.setTextColor(Color.BLACK);
                    error.setText("Signing up is done successfully!");
                    Toast toast = Toast.makeText(getActivity(), "Edit is done successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new InstructorViewHisProfile());
                    fragmentTransaction.commit();
                }


            }
        });


    }

    static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).{8,15}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_instructor_profile, container, false);
    }
}