package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbeariabrothers.databinding.ActivityAgendamentoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Agendamento extends AppCompatActivity {

    private ActivityAgendamentoBinding binding;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    CalendarView calendarView;
    Calendar calendar;
    List<AgendamentoClass> agendamentoList = new ArrayList<>();
    AgendamentoClass agendamentoClassID;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    int i = 1;
    long l;


    int daySelected, yearSelected, monthSelected;

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

        fab = binding.fab;
        calendarView = binding.calendar;
        calendar = Calendar.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                daySelected = dayOfMonth;
                monthSelected = month + 1;
                yearSelected = year;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addAgendamentoUser();
               addAgendamentoBarber();
               //Toast.makeText(Agendamento.this, "Agendamento concluído!", Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(Agendamento.this, SplashAgendado.class);
               startActivity(intent);
            }
        });
    }

    private void addAgendamentoUser(){

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String username = getUser();
        String barber = getBarber();
        String service = getService();

        AgendamentoClass agendamentoClass = new AgendamentoClass(Integer.toString(daySelected), Integer.toString(monthSelected), barber, service);
        databaseReference.child(username).child("agendamentos")
                .child(Integer.toString(daySelected)+Integer.toString(monthSelected)+Integer.toString(yearSelected))
                .setValue(agendamentoClass);


    }

    private void addAgendamentoBarber(){

        databaseReference = FirebaseDatabase.getInstance().getReference("barbers");

        AgendamentoClass agendamentoClass = new AgendamentoClass(Integer.toString(daySelected), Integer.toString(monthSelected), Integer.toString(yearSelected), getUser(), getService());
        databaseReference.child(getUserBarber()).child("agendamentos")
                .child(Integer.toString(daySelected)+Integer.toString(monthSelected)+Integer.toString(yearSelected))
                .setValue(agendamentoClass);
    }

    private String getUser(){
        Intent intent = getIntent();
        return intent.getStringExtra("Username");
    }
    private String getService(){
        Intent intent = getIntent();
        return intent.getStringExtra("Service");
    }
    private String getBarber(){
        Intent intent = getIntent();
        return intent.getStringExtra("Barber");
    }
    private String getUserBarber(){
        Intent intent = getIntent();
        return intent.getStringExtra("UserBarber");
    }

}