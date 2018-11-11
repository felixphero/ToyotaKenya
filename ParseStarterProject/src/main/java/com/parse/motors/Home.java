package com.parse.motors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;

public class Home extends AppCompatActivity {



    String currentuser=ParseUser.getCurrentUser().getUsername();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.out){
            ParseUser.logOut();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(getString(R.string.home_title)+" "+ currentuser);


    }

    public void fuel(View view) {
        Intent intent=new Intent(this,fuel.class);
        startActivity(intent);
    }

    public void map(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void mycar(View view) {
        Intent intent=new Intent(this,MyCars_Recycler_Activity.class);
        startActivity(intent);
    }

    public void book(View view) {
        Intent intent=new Intent(this,booking.class);
        startActivity(intent);
    }


    public void userprofile(MenuItem item) {
        Intent intent=new Intent(this,profile.class);
        startActivity(intent);
    }

    public void sparePart(View view) {
        Intent intent=new Intent(this,SparePart_Recycler.class);
        startActivity(intent);
    }

    public void history(View view) {
        Intent intent=new Intent(this,history.class);
        startActivity(intent);
    }
}
