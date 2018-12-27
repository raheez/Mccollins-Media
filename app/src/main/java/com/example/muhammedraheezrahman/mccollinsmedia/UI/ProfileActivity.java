package com.example.muhammedraheezrahman.mccollinsmedia.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.mccollinsmedia.Model.LoginDetails;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiClient;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiInterface;
import com.example.muhammedraheezrahman.mccollinsmedia.PreferanceManager.PrefManager;
import com.example.muhammedraheezrahman.mccollinsmedia.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ProfileActivity extends RootActivity{

    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout profileLayout;
    ApiInterface apiInterface;
    private LoginDetails.User userData;
    String userId;
    PrefManager preferenceManager;


    private TextInputEditText firstNameEt,lastNameEt,emailEt,passwordEt,mobileEt;
    Spinner genderSpinner;
    CalendarView calendarView;
    String firstName,lastName,email,mobile,password,confirmPassword,gender,dob;
    Button signupButton;
    List<String> genderlist;
    boolean validEmail = false;
    long dateLong;
    public CircleImageView profilePic;
    private static int RESULT_LOAD_IMAGE = 1;
    public Button updateButton;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firstNameEt = (TextInputEditText) findViewById(R.id.firstNameEt);
        lastNameEt = (TextInputEditText) findViewById(R.id.lastNameEt);
        emailEt = (TextInputEditText) findViewById(R.id.emailEt);
        mobileEt = (TextInputEditText) findViewById(R.id.mobilenoEt);

        profilePic = (CircleImageView) findViewById(R.id.profilePic);
        calendarView = (CalendarView) findViewById(R.id.calanderDob);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        updateButton = (Button) findViewById(R.id.updateButton);

        genderlist = new ArrayList<>();
        genderlist.add("Male");
        genderlist.add("Female");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,genderlist);
        genderSpinner.setAdapter(genderAdapter);


        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_profile);
        profileLayout = (RelativeLayout) findViewById(R.id.belowShimmerprofile);


        preferenceManager = new PrefManager(ProfileActivity.this);
        userId = preferenceManager.getUserId();

        getUser(userId);




        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStoragePermissionGranted();

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateUser();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
        profileLayout.setVisibility(View.VISIBLE);
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission

            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
             selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

//            Glide.with(ProfileActivity.this).load(picturePath).into(profilePic);


        }
    }

    private void getUser(final String userId)  {


        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> jsonParams = new ArrayMap<>();

        ((ArrayMap<String, String>) jsonParams).put("id",userId);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        retrofit2.Call<LoginDetails> call = apiInterface.getUser(body);
        call.enqueue(new Callback<LoginDetails>() {
            @Override
            public void onResponse(retrofit2.Call<LoginDetails> call, Response<LoginDetails> response) {
                LoginDetails loginResponse = response.body();
                if(loginResponse.getIserror().equals("No")){

                    for (LoginDetails.User user : loginResponse.getData()){
                        userData = user;

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        profileLayout.setVisibility(View.VISIBLE);
                    }
                    setUserDetails(userData);
                    Toast.makeText(getApplicationContext(),"User Data Fetched Successfull",Toast.LENGTH_SHORT).show();
                }
                else if (loginResponse.getIserror().equals("Yes")){
                    Toast.makeText(getApplicationContext()," User Data Fetching Failed",Toast.LENGTH_SHORT).show();
                    profileLayout.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.setVisibility(View.VISIBLE);

                }



            }

            @Override
            public void onFailure(retrofit2.Call<LoginDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext()," User Data Fetching Failed",Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void setUserDetails(LoginDetails.User userData) {

        firstNameEt.setText(userData.getFname());
        lastNameEt.setText(userData.getLname());
        emailEt.setText(userData.getEmail());
        mobileEt.setText(userData.getMobile());

        String string_date = userData.getDob();

        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date d = f.parse(string_date);
            dateLong = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarView.setDate(dateLong);

    }
    private void updateUser(){

        Map<String, String> jsonParams = new ArrayMap<>();

        ((ArrayMap<String, String>) jsonParams).put("uer_id",userId);
        ((ArrayMap<String, String>) jsonParams).put("email",email);
        ((ArrayMap<String, String>) jsonParams).put("fname",firstName);
        ((ArrayMap<String, String>) jsonParams).put("lname",lastName);
        ((ArrayMap<String, String>) jsonParams).put("mobile",mobile);
        ((ArrayMap<String, String>) jsonParams).put("dob",dob);
        ((ArrayMap<String, String>) jsonParams).put("gender",gender);
        ((ArrayMap<String, String>) jsonParams).put("userimage",selectedImage.getPath().toString());





        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        retrofit2.Call<LoginDetails> call = apiInterface.updateUser(body);
        call.enqueue(new Callback<LoginDetails>() {
            @Override
            public void onResponse(retrofit2.Call<LoginDetails> call, Response<LoginDetails> response) {
                LoginDetails loginResponse = response.body();
                for (LoginDetails.User user : loginResponse.getData()){
                    userData = user;

                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    profileLayout.setVisibility(View.VISIBLE);
                }
//                setUserDetails(userData);
                Toast.makeText(getApplicationContext(),"Updated Successfull",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call<LoginDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Updation Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

