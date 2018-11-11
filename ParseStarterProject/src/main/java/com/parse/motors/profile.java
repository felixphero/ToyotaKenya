package com.parse.motors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class profile extends AppCompatActivity {
    String currentuser=ParseUser.getCurrentUser().getUsername();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(getString(R.string.profile_title));
        getUserDestails();


    }

    public void getUserDestails(){
        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null && objects.size()>0){
                    final TextView fullname=(TextView)findViewById(R.id.userFullName);
                    final TextView first=(TextView)findViewById(R.id.userFirstName);
                    final TextView last=(TextView)findViewById(R.id.userSecName);
                    final TextView email=(TextView)findViewById(R.id.userEmail);

                    String currentuser=ParseUser.getCurrentUser().getUsername();
                    String name=objects.get(0).getString("username");
                    String firstname=objects.get(0).getString("username");
                    String emailadd=objects.get(0).getString("email");
                    String lastName=objects.get(0).getString("lastname");

                    fullname.setText(name +" "+ lastName);
                    first.setText(name);
                    email.setText(emailadd);
                    last.setText(lastName);

                }else{
                    Log.i("info","error");
                }

            }
        });
    }
}
