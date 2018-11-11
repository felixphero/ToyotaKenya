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

public class fuel_history_recycler extends AppCompatActivity {
    String currentuser= ParseUser.getCurrentUser().getUsername();
    RecyclerView recyclerView;
    fuel_Adapter adapter;


    List<fuel_data> fuelDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_history_recycler);

        setTitle(currentuser+getString(R.string.fuel_history_title));

        fuelDataList =new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadRecyclerViewData();
    }
    private void loadRecyclerViewData(){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");


        ParseQuery<ParseObject> query= ParseQuery.getQuery("Fuel");
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
                        fuel_data item=new fuel_data(
                                o.getString("regNo"),
                                o.getString("new"),
                                o.getString("expense")



                        );

                        fuelDataList.add(item);
                    }

                    adapter=new fuel_Adapter(getApplicationContext(), fuelDataList);
                    recyclerView.setAdapter(adapter);
                }else
                    Log.i("error","eror"+ e.getMessage());
            }
        });


    }


}
