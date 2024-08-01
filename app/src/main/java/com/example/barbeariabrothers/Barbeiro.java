package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbeariabrothers.databinding.ActivityBarbeiroBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Barbeiro extends AppCompatActivity {

    private ActivityBarbeiroBinding binding;
    RecyclerView recyclerView;
    List<BarberClass> barberList = new ArrayList<>();
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarbeiroBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = binding.rcView;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Barbeiro.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        BarberAdapter adapter = new BarberAdapter(Barbeiro.this, barberList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("barbers");
        Toast.makeText(this, "ServiceID = " + getServiceID(), Toast.LENGTH_SHORT).show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //barberList.clear();
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
    }

    public String getServiceID(){
        Intent intent = getIntent();
        String serviceID;

        serviceID = intent.getStringExtra("ServiceID");
        return serviceID;
    }

}