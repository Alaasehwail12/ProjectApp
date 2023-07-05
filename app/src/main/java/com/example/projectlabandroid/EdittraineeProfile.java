package com.example.projectlabandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EdittraineeProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EdittraineeProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EdittraineeProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EdittraineeProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EdittraineeProfile newInstance(String param1, String param2) {
        EdittraineeProfile fragment = new EdittraineeProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView image_view2 ;
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
                        image_view2.setImageBitmap(bitmap);
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



        }
    static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).{8,15}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
     String base64image;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edittrainee_profile, container, false);
            EditText emailEditText = (EditText) v.findViewById(R.id.editT);
            emailEditText.setText(trineelogin.tr.getEmail());

            EditText firstNameEditText = (EditText) v.findViewById(R.id.ft1);
            firstNameEditText.setText(trineelogin.tr.getFirst_name());

            EditText lastNameEditText = (EditText) v.findViewById(R.id.lt2);
            lastNameEditText.setText(trineelogin.tr.getLast_name());

            EditText passwordEditText = (EditText) v.findViewById(R.id.passt2);
            passwordEditText.setText(trineelogin.tr.getPassword());

            EditText confirmPasswordEditText = (EditText) v.findViewById(R.id.confirmptext2);
            confirmPasswordEditText.setText(trineelogin.tr.getPassword());

            EditText mobileEditText = (EditText) v.findViewById(R.id.mobiltext2);
            mobileEditText.setText(trineelogin.tr.getMobile_number());

            EditText addressEditText = (EditText) v.findViewById(R.id.addresstext2);
            addressEditText.setText(trineelogin.tr.getAddress());

            image_view2 = (ImageView) v.findViewById(R.id.saved_image);

            TextView error = (TextView)  v.findViewById(R.id.error2);

            Button up= (Button) v.findViewById(R.id.save_profile);
            Button canel= (Button) v.findViewById(R.id.cancle);
            Button photo_button= (Button) v.findViewById(R.id.photoButton2);
            DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);


            image_view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher2.launch(intent);
                }
            });
            photo_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ByteArrayOutputStream byteoutput;
                    byteoutput = new ByteArrayOutputStream();
                    isButtonNotPressed = true;
                    if (bitmap != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteoutput);
                        bytes = byteoutput.toByteArray();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            //  final String base64image = Base64.getEncoder().encodeToString(bytes);
                          //  base64image = String.valueOf(Base64.getDecoder().decode(bytes));
                        }// Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    }
                }
            });
        //    byte[] bytes = trineelogin.tr.getPhoto();

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

                    } else {
                        image_view2.setBackground(d2);
                    }

                    if (firstNameEditText.getText().toString().trim().length() < 3 || firstNameEditText.getText().toString().trim().length() > 20) {
                        firstNameEditText.setBackground(drawable);
                    } else {
                        firstNameEditText.setBackground(d2);
                    }

                    if (lastNameEditText.getText().toString().trim().length() < 3 || lastNameEditText.getText().toString().trim().length() > 20) {
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


                    trainee user = new trainee();

                    if (!isEmailValid(emailEditText.getText().toString().trim())) {
                        somethingWrong[0] = true;
                        errorMessage[0] = "Email Address should be in correct format.";
                    } else {
                        user.setEmail(emailEditText.getText().toString());
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
                                        user.setPassword(passwordEditText.getText().toString());
                                    }
                                    if (!somethingWrong[0]) {
                                        if ((mobileEditText.getText().toString()).equals("")) {
                                            somethingWrong[0] = true;
                                            errorMessage[0] = "enter the mobile number please.";
                                        } else {
                                            user.setMobile_number(mobileEditText.getText().toString());
                                        }

                                        if (!somethingWrong[0]) {
                                            if (addressEditText.getText().toString().equals("")) {
                                                somethingWrong[0] = true;
                                                errorMessage[0] = "enter the address please.";
                                            } else {
                                                user.setAddress(addressEditText.getText().toString());
                                            }
                                            if (!somethingWrong[0]) {
                                                if (!isButtonNotPressed) {
                                                    somethingWrong[0] = true;
                                                    errorMessage[0] = "You should click on the upload Photo first .";
                                                } else {
                                                    user.setPhoto(bytes);
                                                    dbHelper.edittrainee(trineelogin.tr, user);
                                                    dbHelper.edittraineeemail(trineelogin.tr, user);
                                                    dbHelper.editacceptedtraineeemail(trineelogin.tr, user);
                                                    trineelogin.tr = user;
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
                        Toast toast = Toast.makeText(getActivity(), "Error in Information", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        error.setTextColor(Color.BLACK);
                        error.setText("Editing is done successfully!");
                        Toast toast = Toast.makeText(getActivity(), "Edit is done successfully!", Toast.LENGTH_SHORT);
                        toast.show();

                        getParentFragmentManager().beginTransaction().replace(R.id.frameLayout, new TraineeProfile()).commit();

                    }
                }

            });




            return v;
    }
}