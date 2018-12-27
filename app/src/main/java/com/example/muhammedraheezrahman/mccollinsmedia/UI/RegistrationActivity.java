package com.example.muhammedraheezrahman.mccollinsmedia.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.muhammedraheezrahman.mccollinsmedia.Model.RegistrationResponse;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiClient;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiInterface;
import com.example.muhammedraheezrahman.mccollinsmedia.PreferanceManager.PrefManager;
import com.example.muhammedraheezrahman.mccollinsmedia.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity  extends RootActivity implements View.OnClickListener {


    private TextInputEditText firstNameEt,lastNameEt,emailEt,passwordEt,mobileEt,confirmPasswordEt;
    Spinner genderSpinner;
    CalendarView calendarView;
    String firstName,lastName,email,mobile,password,confirmPassword,dob;
    String gender = "Male";
    Button signupButton;
    List<String> genderlist;
    boolean validEmail = false;
    ApiInterface apiInterface;
    RegistrationResponse registrationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firstNameEt = (TextInputEditText) findViewById(R.id.firstnameEt);
        lastNameEt = (TextInputEditText) findViewById(R.id.lastnameET);
        emailEt = (TextInputEditText) findViewById(R.id.emailET);
        passwordEt = (TextInputEditText) findViewById(R.id.passwordeEt);
        confirmPasswordEt = (TextInputEditText) findViewById(R.id.confirmpassEt);
        mobileEt = (TextInputEditText) findViewById(R.id.mobileEt);

        genderSpinner = (Spinner) findViewById(R.id.genderspinner);
        calendarView = (CalendarView) findViewById(R.id.calander);
        signupButton = (Button) findViewById(R.id.RegisterButton);

        genderlist = new ArrayList<>();
        genderlist.add("Male");
        genderlist.add("Female");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,genderlist);
        genderSpinner.setAdapter(genderAdapter);
        signupButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


        firstName = firstNameEt.getText().toString();
        lastName = lastNameEt.getText().toString();
        email = emailEt.getText().toString();
        mobile = mobileEt.getText().toString();
        password = passwordEt.getText().toString();
        confirmPassword = confirmPasswordEt.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
        dob = sdf.format(new Date(calendarView.getDate()));

        if (!password.equals(confirmPassword)){
            confirmPasswordEt.setError("Password Not Matching");
        }

        validEmail = isValidEmail(email);
        if (!firstName.isEmpty() && !lastName.isEmpty() && validEmail && password.equals(confirmPassword) && !gender.isEmpty() && !dob.isEmpty() ){

            apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Map<String, String> jsonParams = new ArrayMap<>();

            ((ArrayMap<String, String>) jsonParams).put("fname",firstName);
            ((ArrayMap<String, String>) jsonParams).put("lname",lastName);
            ((ArrayMap<String, String>) jsonParams).put("email",email);
            ((ArrayMap<String, String>) jsonParams).put("mobile",mobile);
            ((ArrayMap<String, String>) jsonParams).put("password",password);
            ((ArrayMap<String, String>) jsonParams).put("cpassword",confirmPassword);
            ((ArrayMap<String, String>) jsonParams).put("dob",dob);
            ((ArrayMap<String, String>) jsonParams).put("gender",gender);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

            retrofit2.Call<RegistrationResponse> call = apiInterface.registerUser(body);
            call.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                    registrationResponse = response.body();

                    if (registrationResponse.getMessage().equals("Registration successful..")){
                        Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_SHORT).show();



//                        new PrefManager(getApplicationContext()).saveEmail(email);

                        Bundle bundle= new Bundle();
                        bundle.putString("Email", email);
                        bundle.putString("Password",confirmPassword);

                        Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                    Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();

                }
            });


        }
        else {
            Toast.makeText(getApplicationContext(),"Error in form filling",Toast.LENGTH_SHORT).show();
        }



    }
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
