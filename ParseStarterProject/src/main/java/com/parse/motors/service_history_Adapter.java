package com.parse.motors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class service_history_Adapter extends RecyclerView.Adapter<service_history_Adapter.ServiceViewHolder>{

public Context mCtx;
private List<service_data> serviceDataList;

public service_history_Adapter(Context mCtx, List<service_data> serviceDataList) {
        this.mCtx = mCtx;
        this.serviceDataList = serviceDataList;
        }

@NonNull
@Override
public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.service_list_layout,null);

        ServiceViewHolder holder=new ServiceViewHolder(view);
        return holder;
        }

@Override
public void onBindViewHolder(@NonNull final ServiceViewHolder serviceViewHolder, int position) {
        service_data serviceData = serviceDataList.get(position);

        // fuelViewHolder.date.setText(fuelData.getDate());
        serviceViewHolder.registration.setText(serviceData.getRegist());
        serviceViewHolder.branch.setText(serviceData.getBranch());
        serviceViewHolder.date.setText(serviceData.getDate());

        }



@Override
public int getItemCount() {
        return serviceDataList.size();
        }

class ServiceViewHolder extends RecyclerView.ViewHolder{

    TextView date,branch,registration;

    ServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        date= (TextView) itemView.findViewById(R.id.service_date);
        branch= (TextView) itemView.findViewById(R.id.service_branch);
        registration= (TextView) itemView.findViewById(R.id.service_registration);


    }
}
}



