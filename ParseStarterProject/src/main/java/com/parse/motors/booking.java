package com.parse.motors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String branch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        Spinner branch=(Spinner) findViewById(R.id.branchSpinner);
        String[] branches=new String[]{"Service Branch","Nairobi","Westlands Toyota","Nairobi(Kirinyaga Road)," +
                "Ngong Road Toyota","Mombasa","Eldoret","Lodwar","Nakuru","Nyeri","Nanyuki","Kericho","Kisumu"};


        ArrayAdapter<String> branchAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,branches);
        branch.setAdapter(branchAdapter);
        branch.setOnItemSelectedListener(this);



    }

    public void date(View view) {
        DatePickerFragment newFrag=new DatePickerFragment();
        newFrag.show(getSupportFragmentManager(),getString(R.string.date_picker));


    }

    public void time(View view) {
     TimePickerFragment fragment=new TimePickerFragment();
     fragment.show(getSupportFragmentManager(),
             getString(R.string.time_picker));
    }
    public void processDatePickerResult(int year,int month, int day){

        TextView chosenDay=(TextView)findViewById(R.id.siku);
        String month_string=Integer.toString(month+1);
        String day_string= Integer.toString(day);
        String year_string=Integer.toString(year);

        String dateMessage=(month_string+"/"+day_string+"/"+year_string);
        Toast.makeText(this,"Date"+dateMessage,Toast.LENGTH_SHORT).show();

        chosenDay.setText(dateMessage);
        chosenDay.setVisibility(View.VISIBLE);

    }

    public void processTimePickerResult(int hour,int minute){
        TextView chosenTime=(TextView)findViewById(R.id.saa);
        String hour_string=Integer.toString(hour);
        String minute_string=Integer.toString(minute);


        String timeMessage=(hour_string+":"+minute_string);
        Toast.makeText(this,timeMessage,Toast.LENGTH_SHORT).show();
        chosenTime.setText(timeMessage);
        chosenTime.setVisibility(View.VISIBLE);

    }

    public void submit(View view) {
        final EditText fullName=(EditText)findViewById(R.id.fullname);
        final EditText email=(EditText)findViewById(R.id.car_email);
        final EditText phone=(EditText)findViewById(R.id.Telephone);
        final EditText model=(EditText)findViewById(R.id.car_model);
        final EditText regNo=(EditText)findViewById(R.id.car_RegNo);
        final EditText brand=(EditText)findViewById(R.id.the_car_brand);
        final TextView day=(TextView)findViewById(R.id.siku);
        final TextView time=(TextView)findViewById(R.id.saa);




        ParseObject form=new ParseObject("Appointment");
        form.put("username", ParseUser.getCurrentUser().getUsername());
        form.put("fullName",fullName.getText().toString());
        form.put("email",email.getText().toString());
        form.put("phone",phone.getText().toString());
        form.put("model",model.getText().toString());
        form.put("day",day.getText().toString());
        form.put("time",time.getText().toString());
        form.put("registeration",regNo.getText().toString());
        form.put("brand",brand.getText().toString());
        form.put("branch",branch);


        form.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(fullName.getText().toString().matches("")||email.getText().toString().matches("")||
                        phone.getText().toString().matches("")||model.getText().toString().matches("")
                ||regNo.getText().toString().matches("") ||brand.getText().toString().matches("")){
                    Toast.makeText(booking.this,"Kindly fill the fields",Toast.LENGTH_SHORT).show();
                }
                else if(e==null){
                    Toast.makeText(booking.this,"success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(booking.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

      branch=parent.getItemAtPosition(pos).toString();
    }

    @Override
   public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
