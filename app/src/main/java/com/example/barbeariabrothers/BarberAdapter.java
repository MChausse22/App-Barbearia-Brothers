package com.example.barbeariabrothers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarberAdapter extends RecyclerView.Adapter<BarbeiroHolder> {

    private Context context;
    private List<BarberClass> barberList;

    public BarberAdapter(Context context, List<BarberClass> barberList) {
        this.context = context;
        this.barberList = barberList;
    }

    @NonNull
    @Override
    public BarbeiroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_barber, parent, false);
        return new BarbeiroHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarbeiroHolder holder, int position) {

        holder.rcName.setText(barberList.get(position).getName());
        holder.rcAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Agendamento.class);
                intent.putExtra("Username", barberList.get(position).getUsername());
                intent.putExtra("Service", barberList.get(position).getService());
                intent.putExtra("Barber", barberList.get(position).getName());
                intent.putExtra("UserBarber", barberList.get(position).getUserBarber());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }
}

class BarbeiroHolder extends RecyclerView.ViewHolder {
    TextView rcName;
    AppCompatImageView rcAddBtn;
    public BarbeiroHolder(@NonNull View itemView) {
        super(itemView);

        rcName = itemView.findViewById(R.id.rc_name);
        rcAddBtn = itemView.findViewById(R.id.rcAddBarber);
    }
}
