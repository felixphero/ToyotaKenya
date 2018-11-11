package com.parse.motors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    public void register(View view) {
        EditText firstName=(EditText)findViewById(R.id.firstName);
        EditText email=(EditText)findViewById(R.id.email);
        EditText password=(EditText)findViewById(R.id.password);
        EditText lastname=(EditText)findViewById(R.id.lastName);
        if(firstName.getText().toString().matches("")||email.getText().toString().matches("")
                ||password.getText().toString().matches("")||lastname.getText().toString().matches("")){
            Toast.makeText(SignUp.this,"All Fields Must be entered",Toast.LENGTH_SHORT).show();
        }
        else {
            ParseUser user=new ParseUser();
            user.setUsername(firstName.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.put("lastname",lastname.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Log.i("singup","Successfull");
                        Toast.makeText(SignUp.this,"signup Successfull",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SignUp.this,MainActivity.class);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
