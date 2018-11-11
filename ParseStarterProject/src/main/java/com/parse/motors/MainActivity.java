/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.motors;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    //EditText username=(EditText) findViewById(R.id.userName);
   // EditText password=(EditText) findViewById(R.id.password);
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    loadLocale();

    android.support.v7.app.ActionBar actionBar=getSupportActionBar();
    actionBar.setTitle(getResources().getString(R.string.app_name));

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


  public void login(View view) {
   ParseUser user=new ParseUser();
    EditText username=(EditText) findViewById(R.id.userName);
    EditText password=(EditText) findViewById(R.id.password);

    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if (e==null){
         // Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

      }
    });


  }

    public void launchSignup(View view) {
    Intent intent=new Intent(this,SignUp.class);
    startActivity(intent);
    }

    public void changeLanguage(View view) {
      changeLanguageDialog();
    }

    private void changeLanguageDialog() {
      final String[] languages={"English","Kiswahili"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
        mBuilder.setTitle("Choose your prefered language");
        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    setLocale("en");
                    recreate();
                }else if(i==1){
                    setLocale("sw");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }
    private  void setLocale(String lang){

        Locale locale=new Locale(lang);
        locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }
    public  void loadLocale(){
      SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
      String language=prefs.getString("My_Lang","");
      setLocale(language);
    }
}