package com.parse.motors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class fuel_Adapter extends RecyclerView.Adapter<fuel_Adapter.FuelViewHolder>{

public Context mCtx;
private List<fuel_data> fuelDataList;

public fuel_Adapter(Context mCtx, List<fuel_data> fuelDataList) {
        this.mCtx = mCtx;
        this.fuelDataList = fuelDataList;
        }

@NonNull
@Override
public FuelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.fuel_list_layout,null);

        FuelViewHolder holder=new FuelViewHolder(view);
        return holder;
        }

@Override
public void onBindViewHolder(@NonNull final FuelViewHolder fuelViewHolder, int position) {
        fuel_data fuelData = fuelDataList.get(position);

       // fuelViewHolder.date.setText(fuelData.getDate());
        fuelViewHolder.registration.setText(fuelData.getRegist());
        fuelViewHolder.price.setText(fuelData.getPrice());
        fuelViewHolder.fuel.setText(fuelData.getFuel());
}



@Override
public int getItemCount() {
        return fuelDataList.size();
        }

class FuelViewHolder extends RecyclerView.ViewHolder{

    TextView date,price,fuel,registration;

    FuelViewHolder(@NonNull View itemView) {
        super(itemView);

        //date= (TextView) itemView.findViewById(R.id.fuel_date);
        price= (TextView) itemView.findViewById(R.id.fuel_price);
        fuel= (TextView) itemView.findViewById(R.id.fuelAmount);
        registration= (TextView) itemView.findViewById(R.id.fuel_registration);


    }
}
}


