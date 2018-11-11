package com.parse.motors;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SparePart_Recycler extends AppCompatActivity {
    String currentuser= ParseUser.getCurrentUser().getUsername();
    RecyclerView recyclerView;
    sparepart_Adapter adapter;


    List<sparepart> spareList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_part__recycler);

        setTitle(getString(R.string.sparepart_title));

        spareList =new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadRecyclerViewData();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    private void loadRecyclerViewData(){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");


        ParseQuery<ParseObject> query= ParseQuery.getQuery("SpareParts");
      //  query.whereEqualTo("username",currentuser);
        query.orderByAscending("createdAt");
        progressDialog.dismiss();
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){

                    for (int i=0;i<objects.size();i++){
                        ParseObject o=objects.get(i);
                        sparepart item=new sparepart(
                                o.getString("price"),
                                o.getString("description"),
                                o.getString("stock")

                        );

                        spareList.add(item);
                    }

                    adapter=new sparepart_Adapter(getApplicationContext(), spareList);
                    recyclerView.setAdapter(adapter);


                }else
                    Log.i("error","eror"+ e.getMessage());
            }
        });


    }


}


