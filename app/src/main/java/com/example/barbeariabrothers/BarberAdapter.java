package com.example.barbeariabrothers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.BarbeiroHolder> {

    private Context context;
    public static String ID = "null";
    private List<BarberClass> barberList;
    private int lastCheckedPosition = -1;
    public BarberAdapter(Context context, List<BarberClass> barberList) {
        this.context = context;
        this.barberList = barberList;
    }

    @NonNull
    @Override
    public BarbeiroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_barber, parent, false);
        //Agendamento.setSelectedBarber(barberList.get(lastCheckedPosition).getId());
        return new BarbeiroHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarbeiroHolder holder, int position) {

        if (barberList.get(position).getId().equals("null")){
            holder.rcCard.setVisibility(View.GONE);
        }
        else {

            //Agendamento.setSelectedBarber(barberList.get(lastCheckedPosition).getId());

            holder.rcName.setText(barberList.get(position).getName());
            holder.rcAddBtn.setChecked(position == lastCheckedPosition);

            holder.rcAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = holder.getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);

                    ID = barberList.get(position).getId();

                    //Agendamento().setSelectedBarber(barberList.get(position).getId());
                    Toast.makeText(context, "Clique na data para atualizar!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }

    public class BarbeiroHolder extends RecyclerView.ViewHolder {
        TextView rcName;
        AppCompatRadioButton rcAddBtn;
        CardView rcCard;
        public BarbeiroHolder(@NonNull View itemView) {
            super(itemView);

            rcCard = itemView.findViewById(R.id.rc_card);
            rcName = itemView.findViewById(R.id.rc_name);
            rcAddBtn = itemView.findViewById(R.id.rcAddBarber);
        }
    }
}

