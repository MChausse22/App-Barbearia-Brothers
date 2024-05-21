package com.example.barbeariabrothers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AgendamentoBarberAdapter extends RecyclerView.Adapter<AgendamentoBarberHolder> {

    private Context context;
    private List<AgendamentoClass> agendamentoList;

    public AgendamentoBarberAdapter(Context context, List<AgendamentoClass> agendamentoList) {
        this.context = context;
        this.agendamentoList = agendamentoList;
    }

    @NonNull
    @Override
    public AgendamentoBarberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_agendamento_barber, parent, false);
        return new AgendamentoBarberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendamentoBarberHolder holder, int position) {
        holder.rcDate.setText(agendamentoList.get(position).getDay() + "/\n" + agendamentoList.get(position).getMonth());
        holder.rcService.setText(agendamentoList.get(position).getService());
        holder.rcClient.setText(agendamentoList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return agendamentoList.size();
    }
}

class AgendamentoBarberHolder extends RecyclerView.ViewHolder{
    TextView rcDate, rcService, rcClient;
    public AgendamentoBarberHolder(@NonNull View itemView) {
        super(itemView);
        rcDate = itemView.findViewById(R.id.rcb_date);
        rcClient = itemView.findViewById(R.id.rcb_client);
        rcService = itemView.findViewById(R.id.rcb_service);
    }
}
