package com.parse.motors;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class addcar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);


    }

    public void addCar(View view) {


        final EditText make=(EditText)findViewById(R.id.make);
        final EditText model=(EditText)findViewById(R.id.model);
        final EditText engine=(EditText)findViewById(R.id.engine);
        final EditText plate=(EditText)findViewById(R.id.plate);
        final CircleImageView imageView=(CircleImageView)findViewById(R.id.imageView);
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[]byteArray=stream.toByteArray();
        ParseFile file=new ParseFile("image.png",byteArray);

        ParseObject object=new ParseObject("CarImages");
        object.put("image",file);
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.put("make",make.getText().toString());
        object.put("model",model.getText().toString());
        object.put("engine",engine.getText().toString());
        object.put("registeration",plate.getText().toString());


        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(make.getText().toString().matches("")||model.getText().toString().matches("")
                        ||engine.getText().toString().matches("")||plate.getText().toString().matches("")){
                    Toast.makeText(addcar.this,"All fields are required",Toast.LENGTH_SHORT).show();

                }
                else if (e==null){
                    Toast.makeText(addcar.this,"saved Successfully",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(addcar.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getphoto(){
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getphoto();
            }
        }
    }

    public void addphoto(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else{
                getphoto();
            }
        }else
        {
            getphoto();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK && data!=null){
            Uri selectedImage=data.getData();
            try {
                Bitmap bitmap1=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                CircleImageView imageView=(CircleImageView)findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap1);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
