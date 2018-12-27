package com.example.muhammedraheezrahman.mccollinsmedia.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.muhammedraheezrahman.mccollinsmedia.Adapter.RecyclerAdapter;
import com.example.muhammedraheezrahman.mccollinsmedia.Model.Destination;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiClient;
import com.example.muhammedraheezrahman.mccollinsmedia.Networking.ApiInterface;
import com.example.muhammedraheezrahman.mccollinsmedia.PreferanceManager.PrefManager;
import com.example.muhammedraheezrahman.mccollinsmedia.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends RootActivity {


    public RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayoutManager llm;
    public RecyclerAdapter adapter;
    private ArrayList<Destination.DestinationDetail> list = new ArrayList<>();
    public ApiInterface apiInterface;
    public String email;
    public PrefManager preferenceManager;
    public FloatingActionButton profileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_destination);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmerlayout);
        llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

        profileButton = (FloatingActionButton) findViewById(R.id.profileFab);
        recyclerView.setLayoutManager(llm);

        preferenceManager = new PrefManager(MainActivity.this);
        email = preferenceManager.getEmail();


        getDestination(email);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(i);
            }
        });


    }

    private void getDestination(String email) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> jsonParams = new ArrayMap<>();
        ((ArrayMap<String, String>) jsonParams).put("email",email);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        retrofit2.Call<Destination> call = apiInterface.getdestinationdetails(body);
        call.enqueue(new Callback<Destination>() {
            @Override
            public void onResponse(Call<Destination> call, Response<Destination> response) {
                Destination destination = response.body();
                for (Destination.DestinationDetail detail : destination.getData()){
                    list.add(detail);

                }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                List<Destination.DestinationDetail> l = new ArrayList<>();
                l = list;
                adapter = new RecyclerAdapter(getApplicationContext(),l);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<Destination> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Not Available",Toast.LENGTH_SHORT).show();
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
    }
}
