package com.parse.motors;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MyCars_Recycler_Activity extends AppCompatActivity {
    String currentuser= ParseUser.getCurrentUser().getUsername();
    RecyclerView recyclerView;
    car_Adapter adapter;


    List<car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_cars__recycler_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(currentuser+" "+getString(R.string.mycars_title));

        carList =new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       loadRecyclerViewData();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyCars_Recycler_Activity.this,addcar.class);
                startActivity(intent);

            }
        });
    }

   private void loadRecyclerViewData(){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");


        ParseQuery<ParseObject> query= ParseQuery.getQuery("CarImages");
        query.whereEqualTo("username",currentuser);
       query.orderByAscending("createdAt");
        progressDialog.dismiss();
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    // Log.i("cars","received "+objects.size()+"score");
                    for (int i=0;i<objects.size();i++){
                        ParseObject o=objects.get(i);
                        car item=new car(

                                o.getString("make"),
                                o.getString("model"),
                                o.getString("engine"),
                                o.getString("registeration")
                        );

                        carList.add(item);
                    }

                    adapter=new car_Adapter(getApplicationContext(), carList);
                    recyclerView.setAdapter(adapter);
                }else
                    Log.i("error","eror"+ e.getMessage());
            }
        });


    }


}
