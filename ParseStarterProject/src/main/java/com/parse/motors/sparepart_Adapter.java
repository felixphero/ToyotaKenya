package com.parse.motors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public class sparepart_Adapter extends RecyclerView.Adapter<sparepart_Adapter.SpareViewHolder>{

public Context mCtx;
private List<sparepart> sparepartList;

public sparepart_Adapter(Context mCtx, List<sparepart> sparepartList) {
        this.mCtx = mCtx;
        this.sparepartList = sparepartList;
        }

@NonNull
@Override
public SpareViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.sparepart_list_layout,null);

       SpareViewHolder holder=new SpareViewHolder(view);
        return holder;
        }

@Override
public void onBindViewHolder(@NonNull final SpareViewHolder spareViewHolder, int position) {
    final String activeUsername= ParseUser.getCurrentUser().getUsername();
        sparepart sparepart = sparepartList.get(position);

      spareViewHolder.desc.setText(sparepart.getDesc());
      spareViewHolder.price.setText(sparepart.getPrice());
      spareViewHolder.stock.setText(sparepart.getStock());

    spareViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(mCtx,OrderActivity.class);
            mCtx.startActivity(intent);
        }
    });



        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("SpareParts");
        query.orderByAscending("createdAt");
       // query.whereEqualTo("username",activeUsername);
        // query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
         @Override
        public void done(List<ParseObject> objects, ParseException e) {
         if (e==null){
            if(objects.size()>0){
                for(ParseObject object:objects){
                     ParseFile file=(ParseFile)object.get("onsale");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                                 public void done(byte[] data, ParseException e) {
                                        if(e==null&&data!=null){
                                            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);


                                            spareViewHolder.imageView.setImageBitmap(bitmap);

                                             }
                                            }
                                        });
                                     }
                                    }
                                 }
                                }});
}



@Override
public int getItemCount() {
        return sparepartList.size();
        }

class SpareViewHolder extends RecyclerView.ViewHolder{
    CircleImageView imageView;
    TextView desc,stock,price;
    RelativeLayout relativeLayout;
    SpareViewHolder(@NonNull View itemView) {
        super(itemView);
        relativeLayout=(RelativeLayout) itemView.findViewById(R.id.spare_RelativeLayout);
        imageView = (CircleImageView) itemView.findViewById(R.id.spare_imageView);
        desc= (TextView) itemView.findViewById(R.id.spare_desc);
        price= (TextView) itemView.findViewById(R.id.spare_price);
        stock= (TextView) itemView.findViewById(R.id.spare_stock);

    }
}
}


