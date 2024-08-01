package com.example.barbeariabrothers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        holder.rcDay.setText(agendamentoList.get(position).getDay());
        holder.rcMonth.setText(stringMonth(agendamentoList.get(position).getMonth()));
        holder.rcHour.setText(agendamentoList.get(position).getHour());

        String status = agendamentoList.get(position).getStatus();
        holder.rcStatus.setText(status);
        if (status.equals("Confirmado")){
            holder.rcStatus.setBackgroundResource(R.drawable.status_confirmed);
            holder.rcStatus.setTextColor(Color.parseColor("#256828"));
            holder.rcCheckBtn.setVisibility(View.VISIBLE);
        }

        holder.rcService.setText(agendamentoList.get(position).getServiceID());
        holder.rcClient.setText(agendamentoList.get(position).getClientID());

        //Pop-up atendimento finalizado
        Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.finish_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialog.setCancelable(false);

        AppCompatButton dialog_finishBtn = dialog.findViewById(R.id.dialog_finish_btn);
        AppCompatButton dialog_closeBtn = dialog.findViewById(R.id.dialog_close_finish_btn);

        holder.rcCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        dialog_finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointments");
                String appointmentID = agendamentoList.get(position).getId();
                databaseReference.child(appointmentID).child("status").setValue("Finalizado");
                databaseReference.child(appointmentID).child("statusID").setValue(2);

                dialog.dismiss();
                Toast.makeText(context, "Atualize a página!", Toast.LENGTH_LONG).show();
            }
        });
        dialog_closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return agendamentoList.size();
    }

    private String stringMonth(String month){
        String nameOfMonth = "";
        switch (month){
            case "01":
                nameOfMonth = "Jan";
                break;
            case "02":
                nameOfMonth = "Fev";
                break;
            case "03":
                nameOfMonth = "Mar";
                break;
            case "04":
                nameOfMonth = "Abr";
                break;
            case "05":
                nameOfMonth = "Maio";
                break;
            case "06":
                nameOfMonth = "Jun";
                break;
            case "07":
                nameOfMonth = "Jul";
                break;
            case "08":
                nameOfMonth = "Ago";
                break;
            case "09":
                nameOfMonth = "Set";
                break;
            case "10":
                nameOfMonth = "Out";
                break;
            case "11":
                nameOfMonth = "Nov";
                break;
            case "12":
                nameOfMonth = "Dez";
                break;
        }
        return nameOfMonth;
    }
}

class AgendamentoBarberHolder extends RecyclerView.ViewHolder{
    TextView rcDay, rcService, rcClient, rcMonth, rcHour, rcStatus;
    AppCompatButton rcCheckBtn;
    public AgendamentoBarberHolder(@NonNull View itemView) {
        super(itemView);
        rcDay = itemView.findViewById(R.id.rcb_day);
        rcMonth = itemView.findViewById(R.id.rcb_month);
        rcHour = itemView.findViewById(R.id.rcb_hour);
        rcStatus = itemView.findViewById(R.id.rcb_status);
        rcService = itemView.findViewById(R.id.rcb_service);
        rcClient = itemView.findViewById(R.id.rcb_client);
        rcCheckBtn = itemView.findViewById(R.id.rcb_checkBtn);
    }
}
