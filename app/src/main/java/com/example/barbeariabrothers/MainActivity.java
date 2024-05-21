package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbeariabrothers.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME = "user";
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ValueEventListener eventListener;
    List<AgendamentoClass> agendamentoList = new ArrayList<>();
    TextView homeName;
    AppCompatButton btnNew;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeName = binding.homeName;
        btnNew = binding.btnNew;
        recyclerView = binding.rcAgendamentos;

        database = FirebaseDatabase.getInstance();

        showHomeName();
        setBtnNewOnClickListener();
        displayAgendamentos();
    }

    public String getUsername(){
        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME);

        return username;
    }

    private void displayAgendamentos(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AgendamentoClienteAdapter adapter = new AgendamentoClienteAdapter(MainActivity.this, agendamentoList);
        recyclerView.setAdapter(adapter);

        reference = database.getReference("users").child(getUsername()).child("agendamentos");

        eventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                agendamentoList.clear();

                String day, month, service, barber;

                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    day = itemSnapshot.child("day").getValue().toString();
                    month = itemSnapshot.child("month").getValue().toString();
                    service = itemSnapshot.child("service").getValue().toString();
                    barber = itemSnapshot.child("barber").getValue().toString();

                    agendamentoList.add(new AgendamentoClass(day, month, barber, service));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       // Toast.makeText(MainActivity.this, agendamentoList.size(), Toast.LENGTH_SHORT).show();
    }

    private void showHomeName(){
        reference = database.getReference("users");

        Query checkUsersDatabase = reference.child(getUsername());

        checkUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homeName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setBtnNewOnClickListener(){
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Servicos.class);
                intent.putExtra("Username", getUsername());
                startActivity(intent);
            }
        });
    }
}