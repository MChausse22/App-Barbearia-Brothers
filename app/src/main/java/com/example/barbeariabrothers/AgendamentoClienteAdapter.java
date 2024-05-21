package com.example.barbeariabrothers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class AgendamentoClienteAdapter extends RecyclerView.Adapter<AgendamentoClienteHolder> {

    private Context context;
    private List<AgendamentoClass> agendamentoList;

    public AgendamentoClienteAdapter(Context context, List<AgendamentoClass> agendamentoList) {
        this.context = context;
        this.agendamentoList = agendamentoList;
    }

    @NonNull
    @Override
    public AgendamentoClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_agendamento, parent, false);
        return new AgendamentoClienteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendamentoClienteHolder holder, int position) {
        holder.rcDate.setText(agendamentoList.get(position).getDay() + "/\n" + agendamentoList.get(position).getMonth());
        holder.rcService.setText(agendamentoList.get(position).getService());
        holder.rcBarber.setText(agendamentoList.get(position).getBarber());
    }

    @Override
    public int getItemCount() {
        return agendamentoList.size();
    }
}

class AgendamentoClienteHolder extends RecyclerView.ViewHolder {

    TextView rcDate, rcService, rcBarber;

    public AgendamentoClienteHolder(@NonNull View itemView) {
        super(itemView);
        rcDate = itemView.findViewById(R.id.rc_date);
        rcService = itemView.findViewById(R.id.rc_service);
        rcBarber = itemView.findViewById(R.id.rc_barber);
    }
}
