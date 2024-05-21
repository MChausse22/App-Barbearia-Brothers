package com.example.barbeariabrothers;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

import com.example.barbeariabrothers.databinding.ActivityServicosBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Servicos extends AppCompatActivity {

    private ActivityServicosBinding binding;
    RecyclerView recyclerView;
    List<DataClass> dataList = new ArrayList<>();
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServicosBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = binding.rcView;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Servicos.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        MyAdapter adapter = new MyAdapter(Servicos.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("servicos");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                String name, time, price, username;
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    //DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    name = itemSnapshot.child("name").getValue().toString();
                    price = itemSnapshot.child("price").getValue().toString();
                    time = itemSnapshot.child("time").getValue().toString();

                    username = getUsername();

                    dataList.add(new DataClass(name, time, price, username));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getUsername(){
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        return username;
    }

}