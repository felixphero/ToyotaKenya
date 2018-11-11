package com.parse.motors;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class car_Adapter extends RecyclerView.Adapter<car_Adapter.ProductViewHolder>{

    public Context mCtx;
    private List<car> carList;

    public car_Adapter(Context mCtx, List<car> carList) {
        this.mCtx = mCtx;
        this.carList = carList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.car_list_layout,null);

        ProductViewHolder holder=new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int position) {
        car car = carList.get(position);

        productViewHolder.make.setText(car.getMake());
        productViewHolder.model.setText(car.getModel());
        productViewHolder.engine.setText(String.valueOf(car.getEngine()));
        productViewHolder.plate.setText(String.valueOf(car.getPlate()));
        String activeUser=ParseUser.getCurrentUser().getUsername();
/*
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("CarImages");
        query.whereEqualTo("username",activeUser );
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if(objects.size()>0){
                        for(ParseObject object:objects){
                            ParseFile file=(ParseFile)object.get("image");
                            String imageUrl=file.getUrl();
                            Uri imageUri=Uri.parse(imageUrl);

                            Picasso.with(mCtx).load(imageUri.toString()).into(productViewHolder.imageView);

                    }
                }
            }
            }
        });*/
        String activeUsername= ParseUser.getCurrentUser().getUsername();
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("CarImages");
        query.orderByAscending("createdAt");
        query.whereEqualTo("username",activeUsername);
       // query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if(objects.size()>0){
                        for(ParseObject object:objects){
                            ParseFile file=(ParseFile)object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e==null&&data!=null){
                                        Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                      //  CircleImageView imageView=new CircleImageView((getApplicationContext()));

                                        productViewHolder.imageView.setImageBitmap(bitmap);

                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return carList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        CircleImageView  imageView;
        TextView make,model,engine,plate;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
            make= (TextView) itemView.findViewById(R.id.make);
            model= (TextView) itemView.findViewById(R.id.model);
            engine= (TextView) itemView.findViewById(R.id.engine);
            plate= (TextView) itemView.findViewById(R.id.plate);
        }
    }
}
