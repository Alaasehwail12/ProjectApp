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
 * Use the {@link adminSignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adminSignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public adminSignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adminSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static adminSignUpFragment newInstance(String param1, String param2) {
        adminSignUpFragment fragment = new adminSignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private  ActivityResultLauncher<Intent> activityResultLauncher;
    private  ImageView image_view ;
    private Bitmap bitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                        image_view.setImageBitmap(bitmap);
                    }catch (IOException a){
                        a.printStackTrace();

                    }
                }
            }
        });

    }

    boolean isButtonNotPressed = false;
    private byte [] bytes;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        EditText emailadmin = (EditText) getActivity().findViewById(R.id.emialedit);
        EditText fname = (EditText) getActivity().findViewById(R.id.editfname);
        EditText lname = (EditText) getActivity().findViewById(R.id.editlname);
        EditText pass1 = (EditText) getActivity().findViewById(R.id.pass1);
        EditText pass2 = (EditText) getActivity().findViewById(R.id.pass2);
        Button signup2 = (Button) getActivity().findViewById(R.id.signupadmin);
        Button photo = (Button) getActivity().findViewById(R.id.photo);
        TextView error = (TextView) getActivity().findViewById(R.id.error2);
        image_view = (ImageView) getActivity().findViewById(R.id.imageView2);

        GradientDrawable d2 = new GradientDrawable();

        photo.setOnClickListener(new View.OnClickListener() {
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
                        //showphoto.setText(base64image);
                        image_view.setBackground(d2);

                    }
                    // Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }

            }
        });
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });


        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] somethingWrong = {false};
                final String[] errorMessage = new String[1];
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setStroke(5, Color.RED);
                drawable.setCornerRadius(10f);
                //Drawable d2 = fname.getBackground();
                d2.setShape(GradientDrawable.RECTANGLE);
                d2.setStroke(5, Color.blue(5));
                d2.setCornerRadius(10f);



                if (fname.getText().toString().trim().length() < 3 || fname.getText().toString().trim().length() >20 ) {
                    fname.setBackground(drawable);
                }
                else {
                    fname.setBackground(d2);
                }
                if (lname.getText().toString().trim().length() < 3 || lname.getText().toString().trim().length() >20) {
                    lname.setBackground(drawable);
                }

                else {
                    lname.setBackground(d2);
                }

                if (!isEmailValid(emailadmin.getText().toString().trim())) {
                    emailadmin.setBackground(drawable);
                }
                else
                {
                    emailadmin.setBackground(d2);
                }
                if (!isPasswordValid(pass1.getText().toString())) {
                    pass1.setBackground(drawable);
                } else {
                    pass1.setBackground(d2);
                }
                if (!isPasswordValid(pass2.getText().toString()) || !pass2.getText().toString().equals(pass1.getText().toString())) {
                    pass2.setBackground(drawable);
                } else {
                    pass2.setBackground(d2);
                }

                if (!isButtonNotPressed) {
                    image_view.setBackground(drawable);

                }
                else{
                    image_view.setBackground(d2);
                }



                DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Database", null, 1);
                admin user = new admin();

                if (!isEmailValid(emailadmin.getText().toString().trim())) {
                    somethingWrong[0] = true;
                    errorMessage[0] = "Email Address should be in correct format.";
                } else {
                    user.setEmail(emailadmin.getText().toString().trim());
                }

                if (!somethingWrong[0]) {
                    if (fname.getText().toString().trim().length() < 3 ) {
                        somethingWrong[0] = true;
                        errorMessage[0] = "First Name should be at least 3 characters.";
                    }else if(fname.getText().toString().trim().length()>20 ){
                        somethingWrong[0] = true;
                        errorMessage[0] = "First Name should be at max 20 characters.";
                    }
                    else {
                        user.setFirst_name(fname.getText().toString().trim());
                    }

                    if (!somethingWrong[0]) {
                        if (lname.getText().toString().trim().length() < 3) {
                            somethingWrong[0] = true;
                            errorMessage[0] = "Last Name should be at least 3 characters.";
                        }
                        else if(lname.getText().toString().trim().length()>20 ){
                            somethingWrong[0] = true;
                            errorMessage[0] = "Lsat Name should be at max 20 characters.";
                        }
                        else {
                            user.setLast_name(lname.getText().toString().trim());
                        }

                        if (!somethingWrong[0]) {
                            if (!isPasswordValid(pass1.getText().toString().trim())) {
                                somethingWrong[0] = true;
                                errorMessage[0] = "The password must contain at least one number, one lowercase letter, and one uppercase letter.";
                            }
                            else {
                                user.setPassword(pass1.getText().toString().trim());
                            }

                            if (!somethingWrong[0]) {
                                if (!pass2.getText().toString().trim().equals(pass1.getText().toString().trim())) {
                                    somethingWrong[0] = true;
                                    errorMessage[0] = "The password does not match the confirmation.";
                                } else {
                                    user.setPassword(pass2.getText().toString().trim());
                                }
                                if (!somethingWrong[0]) {
                                    if (!isButtonNotPressed) {
                                        somethingWrong[0] = true;
                                        errorMessage[0] = "You should click on the upload Photo first .";
                                    }else{
                                        user.setBytes(bytes);
                                        dbHelper.insertadmin(user);

                                    }
                                }
                            }
                        }
                    }
                }
                Cursor allAdminCursor = dbHelper.getAllAdmins();
                while (allAdminCursor.moveToNext()){ //this function allow me to move between data
                    System.out.println("Email= "+allAdminCursor.getString(0) +"\nFirstName= "+allAdminCursor.getString(1)
                            +"\nLastName= "+allAdminCursor.getString(2)
                            +"\nPassword= "+allAdminCursor.getString(3)
                            +"\nPhoto= "+allAdminCursor.getBlob(4)+
                            "\n\n" );
                }

                if (somethingWrong[0]) {
                    error.setTextColor(Color.RED);
                    error.setText(errorMessage[0]);
                    Toast toast =Toast.makeText(getActivity(),"Error in Information",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast =Toast.makeText(getActivity(),"Signing up is done successfully!",Toast.LENGTH_SHORT);
                    toast.show();
                    error.setTextColor(Color.BLACK);
                    error.setText("Signing up is done successfully!");
                    AlertDialog.Builder builder = new AlertDialog.Builder(  requireContext());
                    builder.setTitle("Login");
                    builder.setMessage("Do you want to login in !!");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(requireContext(), logIn.class));

                            fname.setText("");
                            lname.setText("");
                            emailadmin.setText("");
                            pass1.setText("");
                            pass2.setText("");
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
        return inflater.inflate(R.layout.fragment_admin_sign_up, container, false);

    }




}