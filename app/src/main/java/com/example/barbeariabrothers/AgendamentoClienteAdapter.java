package com.example.barbeariabrothers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        holder.rcDay.setText(agendamentoList.get(position).getDay());
        holder.rcMonth.setText(stringMonth(agendamentoList.get(position).getMonth()));
        holder.rcHour.setText(agendamentoList.get(position).getHour());

        String status = agendamentoList.get(position).getStatus();
        holder.rcStatus.setText(status);
        if (status.equals("Confirmado")){
            holder.rcStatus.setBackgroundResource(R.drawable.status_confirmed);
            holder.rcStatus.setTextColor(Color.parseColor("#256828"));
            holder.rcCancelBtn.setVisibility(View.VISIBLE);
        }

        holder.rcService.setText(agendamentoList.get(position).getServiceID());
        holder.rcBarber.setText(agendamentoList.get(position).getBarberID());


        Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.cancel_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialog.setCancelable(false);

        AppCompatButton dialog_cancelBtn = dialog.findViewById(R.id.dialog_cancel_btn);
        AppCompatButton dialog_closeBtn = dialog.findViewById(R.id.dialog_close_btn);
        
        holder.rcCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        dialog_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointments");
                String appointmentID = agendamentoList.get(position).getId();
                databaseReference.child(appointmentID).child("status").setValue("Cancelado");
                databaseReference.child(appointmentID).child("statusID").setValue(3);

                ValueEventListener appointmentListener = new ValueEventListener() {
                    String barberID, year, month, day, hour;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        barberID = snapshot.child("barberID").getValue(String.class);
                        year = snapshot.child("year").getValue(String.class);
                        month = snapshot.child("month").getValue(String.class);
                        day = snapshot.child("day").getValue(String.class);
                        hour = snapshot.child("hour").getValue(String.class);

                        DatabaseReference barbersReference = FirebaseDatabase.getInstance().getReference("barbers");
                        //if (!BarberAdapter.ID.equals("null") && !ScheduleAdapter.TIME.equals("null")) {
                            barbersReference.child(barberID).child("schedules")
                                    .child(year + "-" + month + "-" + day)
                                    .child(hour).setValue(true);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                databaseReference.child(appointmentID).addListenerForSingleValueEvent(appointmentListener);

                dialog.dismiss();
                Toast.makeText(context, "Atualize o filtro", Toast.LENGTH_LONG).show();
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

class AgendamentoClienteHolder extends RecyclerView.ViewHolder {

    TextView rcDay, rcService, rcBarber, rcMonth, rcHour, rcStatus;
    AppCompatButton rcCancelBtn;

    public AgendamentoClienteHolder(@NonNull View itemView) {
        super(itemView);
        rcDay = itemView.findViewById(R.id.rc_day);
        rcMonth = itemView.findViewById(R.id.rc_month);
        rcHour = itemView.findViewById(R.id.rc_hour);
        rcStatus = itemView.findViewById(R.id.rc_status);
        rcService = itemView.findViewById(R.id.rc_service);
        rcBarber = itemView.findViewById(R.id.rc_barber);
        rcCancelBtn = itemView.findViewById(R.id.rc_cancelBtn);
    }
}
