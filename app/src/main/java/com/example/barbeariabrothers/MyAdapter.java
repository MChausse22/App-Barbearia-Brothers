package com.example.barbeariabrothers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rcName.setText(dataList.get(position).getServiceName());
        holder.rcTime.setText(dataList.get(position).getServiceTime() + "min");
        holder.rcPrice.setText("R$" + dataList.get(position).getServicePrice() + ",00");

        holder.rcAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Agendamento.class);
                Bundle bundle = new Bundle();
                bundle.putString("ServiceID", dataList.get(position).getServiceID());
                bundle.putString("BarberID", Integer.toString(1));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView rcName, rcPrice, rcTime;
    AppCompatImageView rcAddBtn;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        rcName = itemView.findViewById(R.id.rc_Name);
        rcTime = itemView.findViewById(R.id.rc_time);
        rcPrice = itemView.findViewById(R.id.rc_price);
        rcAddBtn = itemView.findViewById(R.id.rc_addBtn);
    }

}