package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbeariabrothers.databinding.ActivityMainBarberBinding;
import com.example.barbeariabrothers.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainBarber extends AppCompatActivity {

    public static final String USERNAME = "user";
    private ActivityMainBarberBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ValueEventListener eventListener;
    List<AgendamentoClass> agendamentoList = new ArrayList<>();
    TextView barberName;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBarberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        barberName = binding.barberName;
        recyclerView = binding.rcAgendamentos;

        database = FirebaseDatabase.getInstance();

        showHomeName();
        displayAgendamentos();
    }

    private String getUsername() {
        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME);

        return username;
    }

    private void displayAgendamentos() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainBarber.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AgendamentoBarberAdapter adapter = new AgendamentoBarberAdapter(MainBarber.this, agendamentoList);
        recyclerView.setAdapter(adapter);

        reference = database.getReference("barbers").child(getUsername()).child("agendamentos");

        eventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                agendamentoList.clear();

                String day, month, service, client, year;

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    day = itemSnapshot.child("day").getValue().toString();
                    month = itemSnapshot.child("month").getValue().toString();
                    year = itemSnapshot.child("year").getValue().toString();
                    service = itemSnapshot.child("service").getValue().toString();
                    client = itemSnapshot.child("username").getValue().toString();

                    agendamentoList.add(new AgendamentoClass(day, month, year, client, service));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showHomeName() {
        reference = database.getReference("barbers");

        Query checkUsersDatabase = reference.child(getUsername());

        checkUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                barberName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}