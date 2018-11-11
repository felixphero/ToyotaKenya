package com.parse.motors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle(getString(R.string.history_title));
    }

    public void fuel_history(View view) {
        Intent intent=new Intent(this,fuel_history_recycler.class);
        startActivity(intent);
    }

    public void serviceHistry(View view) {
        Intent intent=new Intent(this,service_history_recycler.class);
        startActivity(intent);
    }
}
