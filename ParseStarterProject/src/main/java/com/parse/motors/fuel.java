package com.parse.motors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class fuel extends AppCompatActivity {
    String currentuser= ParseUser.getCurrentUser().getUsername();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);
        setTitle(getString(R.string.fuel_title));

        final TextView existingRecord=(TextView)findViewById(R.id.existing);

        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("Fuel");
        query.orderByDescending("createdAt");
        query.whereEqualTo("username",currentuser).setLimit(1);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if(objects.size()>0){
                        for(ParseObject object:objects){
                            String record=(String) object.get("new");
                            existingRecord.setText(record);
                        }
                    }
                }
            }
        });


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void updating(View view) {
        EditText newfuel=(EditText)findViewById(R.id.newfuel);
        EditText odo=(EditText)findViewById(R.id.oddreading);
        EditText spent=(EditText)findViewById(R.id.amount);
        final EditText regNo=(EditText)findViewById(R.id.carId);


        final String data=newfuel.getText().toString();
        String odoData=odo.getText().toString();
        String RegNo=regNo.getText().toString();
        final ParseObject fuel=new ParseObject("Fuel");
        fuel.put("username",ParseUser.getCurrentUser().getUsername());
        fuel.put("new",data);
        fuel.put("regNo",RegNo);
        fuel.put("odometer",odoData);
        fuel.put("expense",spent.getText().toString());


        fuel.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(data.matches("")&&regNo.getText().toString().matches("")){
                    Toast.makeText(fuel.this,"Kindly Enter a number!",Toast.LENGTH_SHORT).show();
                }
                else if(e==null){
                    TextView existingRecord=(TextView)findViewById(R.id.existing);
                    Log.i("info","successfully Updated");
                    Toast.makeText(fuel.this,"successfull",Toast.LENGTH_SHORT).show();
                    existingRecord.setText(data);
                }else
                    Log.i("error",e.getMessage());
            }
        });
    }
}
