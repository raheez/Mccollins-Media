package com.example.muhammedraheezrahman.mccollinsmedia.Networking;

import com.example.muhammedraheezrahman.mccollinsmedia.Model.Destination;
import com.example.muhammedraheezrahman.mccollinsmedia.Model.LoginDetails;
import com.example.muhammedraheezrahman.mccollinsmedia.Model.RegistrationResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("checklogin?")
    Call<LoginDetails> userLogin(@Body RequestBody params);

    @POST("listAttractions?")
    Call<Destination> getdestinationdetails(@Body RequestBody params);

    @POST("registerUser?")
    Call<RegistrationResponse> registerUser(@Body RequestBody params);


    @POST("getUser?")
    Call<LoginDetails> getUser(@Body RequestBody params);

    @POST("updateUser?")
    Call<LoginDetails> updateUser(@Body RequestBody params);




}
