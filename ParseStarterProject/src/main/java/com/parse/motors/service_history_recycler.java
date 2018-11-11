package com.parse.motors;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class service_history_recycler extends AppCompatActivity {
    String currentuser= ParseUser.getCurrentUser().getUsername();
    RecyclerView recyclerView;
    service_history_Adapter adapter;


    List<service_data> serviceDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history_recycler);
        setTitle(currentuser+getString(R.string.service_title));

        serviceDataList =new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadRecyclerViewData();
    }
    private void loadRecyclerViewData(){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");


        ParseQuery<ParseObject> query= ParseQuery.getQuery("Appointment");
        query.whereEqualTo("username",currentuser);
        query.orderByDescending("createdAt");
        progressDialog.dismiss();
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    // Log.i("cars","received "+objects.size()+"score");
                    for (int i=0;i<objects.size();i++){
                        ParseObject o=objects.get(i);
                        service_data item=new service_data(
                                o.getString("registeration"),
                                o.getString("day"),
                                o.getString("branch")



                        );

                      serviceDataList.add(item);
                    }

                   adapter=new service_history_Adapter(getApplicationContext(), serviceDataList);
                    recyclerView.setAdapter(adapter);
                }else
                    Log.i("error","eror"+ e.getMessage());
            }
        });


    }

}
