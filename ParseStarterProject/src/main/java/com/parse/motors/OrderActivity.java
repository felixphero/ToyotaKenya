package com.parse.motors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG_ACTIVITY=OrderActivity.class.getSimpleName();

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }
    public void onRadioButtonClicked(View view) {
        boolean checked=((RadioButton) view).isChecked();
        ParseObject query=new ParseObject("Order");
        query.put("username", ParseUser.getCurrentUser().getUsername());
        switch (view.getId()){
            case R.id.sameday:
                if(checked)
                    query.put("Delivery","sameday");
                break;
            case R.id.nextday:
                if(checked)
                    query.put("Delivery","nextday");
                break;

            case R.id.pickup:
                if(checked)
                    query.put("Delivery","pick up");
                break;

            default:
                Log.d(TAG_ACTIVITY,getString(R.string.nothing_clicked));
                break;
        }
        query.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(OrderActivity.this,"Order Placed Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(OrderActivity.this,Home.class);
                    startActivity(intent);
                }
            }
        });
    }
}
