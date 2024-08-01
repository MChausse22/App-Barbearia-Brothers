package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbeariabrothers.databinding.ActivityAgendamentoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Agendamento extends AppCompatActivity {

    private ActivityAgendamentoBinding binding;
    private FirebaseAuth mAuth;
    private String currentUserID;
    List<BarberClass> barberList = new ArrayList<>();
    FloatingActionButton fab;
    RecyclerView recyclerBarbers;
    RecyclerView recyclerSchedule;
    CalendarView calendarView;
    Calendar calendar;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    List<String> scheduleList = new ArrayList<>();

    String daySelected, yearSelected, monthSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgendamentoBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerBarbers = binding.rcBarbers;
        recyclerSchedule = binding.rcSchedule;
        fab = binding.fab;
        calendarView = binding.calendar;

        calendar = Calendar.getInstance();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        long startDate = System.currentTimeMillis();

        calendarView.setMinDate(startDate);
        calendar.getTime().getTime();
        getDate();

        displayBarbers();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                daySelected = String.format("%02d", dayOfMonth);
                monthSelected =  String.format("%02d", month + 1);
                yearSelected =  String.format("%d", year);
                displaySchedule();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addAppointment();
                Toast.makeText(Agendamento.this, BarberAdapter.ID, Toast.LENGTH_SHORT).show();
                Toast.makeText(Agendamento.this, ScheduleAdapter.TIME, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDate(){
        daySelected = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        monthSelected = String.format("%02d", calendar.get(Calendar.MONTH));
        yearSelected = String.format("%d", calendar.get(Calendar.YEAR));
    }
    private void displayBarbers(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Agendamento.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerBarbers.setLayoutManager(linearLayoutManager);

        BarberAdapter adapter = new BarberAdapter(Agendamento.this, barberList);
        recyclerBarbers.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("barbers");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                barberList.clear();
                String name, id;

                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    id = itemSnapshot.getKey();
                    name = itemSnapshot.child("name").getValue().toString();

                    String serviceID = getServiceID();

                    barberList.add(new BarberClass(name, id, serviceID));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        displaySchedule();
    }

    private String dateFormat(){
        return yearSelected + "-" + monthSelected + "-" + daySelected;
    }
    public void displaySchedule(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Agendamento.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSchedule.setLayoutManager(linearLayoutManager);

        ScheduleAdapter adapter = new ScheduleAdapter(Agendamento.this, scheduleList);
        recyclerSchedule.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("barbers");

        eventListener = databaseReference.child(BarberAdapter.ID).child("schedules").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduleList.clear();
                if (!snapshot.hasChild(dateFormat())){
                    for (DataSnapshot itemSnapshot: snapshot.child("default").getChildren()){
                        scheduleList.add(itemSnapshot.getKey());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    for (DataSnapshot itemSnapshot: snapshot.child(dateFormat()).getChildren()){
                        if (itemSnapshot.getValue(Boolean.class)){
                            scheduleList.add(itemSnapshot.getKey());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addAppointment() {
        
        if (!ScheduleAdapter.TIME.equals("null") && !BarberAdapter.ID.equals("null")) {

            //Child appointments
            databaseReference = FirebaseDatabase.getInstance().getReference("appointments");

            String key = databaseReference.push().getKey();
            String serviceID = getServiceID();

            AgendamentoClass agendamentoClass = new AgendamentoClass(daySelected, monthSelected, yearSelected,
                    key, currentUserID, BarberAdapter.ID, serviceID, ScheduleAdapter.TIME, "Confirmado", 1);
            databaseReference.child(key).setValue(agendamentoClass);

            //Child clients
            databaseReference = FirebaseDatabase.getInstance().getReference("clients");
            int year, month, day;
            year = Integer.parseInt(yearSelected);
            month = Integer.parseInt(monthSelected);
            day = Integer.parseInt(daySelected);
            databaseReference.child(currentUserID).child("appointments").child(key).child("year").setValue(year);
            databaseReference.child(currentUserID).child("appointments").child(key).child("month").setValue(month);
            databaseReference.child(currentUserID).child("appointments").child(key).child("day").setValue(day);
            databaseReference.child(currentUserID).child("appointments").child(key).child("hour").setValue(ScheduleAdapter.TIME);
            databaseReference.child(currentUserID).child("appointments").child(key).child("date").setValue(dateFormat() + "-" + ScheduleAdapter.TIME);

            //Child barbers
            databaseReference = FirebaseDatabase.getInstance().getReference("barbers");
            //databaseReference.child(SELECTED_BARBER).child("appointments").child(key).setValue("true");
            databaseReference.child(BarberAdapter.ID).child("appointments").child(key).child("date").setValue(dateFormat());

            //Update barber schedule
            eventListener = databaseReference.child(BarberAdapter.ID).child("schedules").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.hasChild(dateFormat())) {
                        for (DataSnapshot itemSnapshot : snapshot.child("default").getChildren()) {
                            String time = itemSnapshot.getKey();
                            if (!time.equals(ScheduleAdapter.TIME))
                                databaseReference.child(BarberAdapter.ID).child("schedules").child(dateFormat()).child(time).setValue(true);
                            else
                                databaseReference.child(BarberAdapter.ID).child("schedules").child(dateFormat()).child(time).setValue(false);
                        }
                    } else {
                        databaseReference.child(BarberAdapter.ID).child("schedules").child(dateFormat()).child(ScheduleAdapter.TIME).setValue(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(Agendamento.this, SplashAgendado.class);
            startActivity(intent);
            finish();
        }
        else if (BarberAdapter.ID.equals("null")) {
            Toast.makeText(this, "Selecione um barbeiro", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Selecione um horário", Toast.LENGTH_SHORT).show();
        }
    }

    private String getServiceID(){
        Intent intent = getIntent();
        return intent.getStringExtra("ServiceID");
    }
    private String getBarberID(){
        Intent intent = getIntent();
        return intent.getStringExtra("BarberID");
    }
}