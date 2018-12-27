package com.example.muhammedraheezrahman.mccollinsmedia.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.mccollinsmedia.Model.LoginDetails;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiClient;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiInterface;
import com.example.muhammedraheezrahman.mccollinsmedia.PreferanceManager.PrefManager;
import com.example.muhammedraheezrahman.mccollinsmedia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends RootActivity {

    private TextInputEditText emailEt, passwordEt;
    Button login;
    String email,password;
    ApiInterface apiInterface;
    private LoginDetails.User userData;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private Button signupButton;
    private ImageView logoImage;
    private RelativeLayout relativeLayout;
    Snackbar snackbar;
    boolean validEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvtivity_login);
        emailEt = (TextInputEditText) findViewById(R.id.emailEt);
        passwordEt = (TextInputEditText) findViewById(R.id.passwordEt);
        login = (Button) findViewById(R.id.loginButton);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        signupButton = (Button) findViewById(R.id.signupButton);
        logoImage = (ImageView) findViewById(R.id.logoimage);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);

        Glide.with(LoginActivity.this).load(R.drawable.mccollinsmediadubai).into(logoImage);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();
                validEmail = isValidEmail(email);
                if (!validEmail){
                    emailEt.setError("Email not valid");
                }
                if (!email.isEmpty() && !password.isEmpty()){

                     if(isValidEmail(email))
                    login(email,password);
                }


            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(final String email, String password)  {


        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> jsonParams = new ArrayMap<>();

        ((ArrayMap<String, String>) jsonParams).put("email",email);
        ((ArrayMap<String, String>) jsonParams).put("password",password);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        retrofit2.Call<LoginDetails> call = apiInterface.userLogin(body);
        call.enqueue(new Callback<LoginDetails>() {
            @Override
            public void onResponse(retrofit2.Call<LoginDetails> call, Response<LoginDetails> response) {
                LoginDetails loginResponse = response.body();
                if(loginResponse.getIserror().equals("No")){

                    for (LoginDetails.User user : loginResponse.getData()){
                        userData = user;
                        new PrefManager(LoginActivity.this).saveObjectToSharedPreference(LoginActivity.this,userData);
                        new PrefManager(LoginActivity.this).saveEmail(email);
                        new PrefManager(LoginActivity.this).saveUserId(userData.getUserId().toString());



                    }
                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (loginResponse.getIserror().equals("Yes")){
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<LoginDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();


            }
        });



    }


    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}
