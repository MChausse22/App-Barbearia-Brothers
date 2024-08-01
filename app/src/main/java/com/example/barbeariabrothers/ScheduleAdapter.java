package com.example.barbeariabrothers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {
    Context context;
    List<String> scheduleList;
    public static String TIME = "null";
    private int lastCheckedPosition = -1;

    public ScheduleAdapter(Context context, List<String> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_schedule, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.scheduleBtn.setText(scheduleList.get(position));
        holder.scheduleBtn.setChecked(position == lastCheckedPosition);
        holder.scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int copyOfLastCheckedPosition = lastCheckedPosition;
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemChanged(copyOfLastCheckedPosition);
                notifyItemChanged(lastCheckedPosition);

                TIME = scheduleList.get(position);
                //Agendamento.setSelectedTime(scheduleList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {
        AppCompatRadioButton scheduleBtn;
        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            scheduleBtn = itemView.findViewById(R.id.rc_scheduleBtn);
        }
    }
}

