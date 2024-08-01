package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.auth.FirebaseAuth;
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
   // public static final String CURRENT_USER_ID = "currentUserID";
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference clientsRef;
    private ValueEventListener eventListener;
    private String currentUserID;
    private String filterStatus = "all";
    List<AgendamentoClass> agendamentoList = new ArrayList<>();
    TextView homeName;
    AppCompatButton btnNew, btnLogout;
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    RadioButton filterAll, filterConfirmed, filterCanceled, filterFinished;
    String appointmentID, barberID, serviceID, day, month, year, hour, status;
    int statusID;
    String serviceName, barberName;


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

        //homeName = binding.homeName;
        btnNew = binding.btnNew;
        recyclerView = binding.rcAgendamentos;
        radioGroup = binding.radioGroup;
        filterAll = binding.filterBtnAll;
        filterCanceled = binding.filterBtnCanceled;
        filterConfirmed = binding.filterBtnConfirmed;
        filterFinished = binding.filterBtnFinished;
        btnLogout = binding.btnLogout;

        filterAll.setChecked(true);
        filterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterStatus = "all";
                displayAgendamentos();
            }
        });
        filterConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterStatus = "Confirmed";
                displayAgendamentos();
            }
        });
        filterFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterStatus = "Finished";
                displayAgendamentos();
            }
        });
        filterCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterStatus = "Canceled";
                displayAgendamentos();
            }
        });

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        //showHomeName();
        setBtnNewOnClickListener();
        displayAgendamentos();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginOption.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void displayAgendamentos(){
        agendamentoList.clear();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AgendamentoClienteAdapter adapter = new AgendamentoClienteAdapter(MainActivity.this, agendamentoList);
        recyclerView.setAdapter(adapter);

        clientsRef = database.getReference("clients");
        DatabaseReference barbersRef = database.getReference("barbers");
        DatabaseReference appointmentsRef = database.getReference("appointments");
        DatabaseReference servicesRef = database.getReference("services");
        ValueEventListener clientListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                agendamentoList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    AgendamentoClass agendamentoClass = new AgendamentoClass();

                    appointmentID = itemSnapshot.getKey();
                    agendamentoClass.setId(appointmentID);

                    ValueEventListener appointmentListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            barberID = snapshot.child("barberID").getValue(String.class);
                            serviceID = snapshot.child("serviceID").getValue(String.class);
                            day = snapshot.child("day").getValue(String.class);
                            month = snapshot.child("month").getValue(String.class);
                            year = snapshot.child("year").getValue(String.class);
                            hour = snapshot.child("hour").getValue(String.class);
                            status = snapshot.child("status").getValue(String.class);

                            statusID = snapshot.child("statusID").getValue(Integer.class);

                            agendamentoClass.setDay(day);
                            agendamentoClass.setMonth(month);
                            agendamentoClass.setYear(year);
                            agendamentoClass.setHour(hour);
                            agendamentoClass.setStatus(status);
                            agendamentoClass.setStatusID(statusID);
                            ValueEventListener serviceListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    serviceName = snapshot.child("name").getValue(String.class);

                                    agendamentoClass.setServiceID(serviceName);

                                    ValueEventListener barberListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            barberName = snapshot.child("name").getValue(String.class);

                                            //AgendamentoClass agendamentoClass = new AgendamentoClass(day, month, year, appointmentID, currentUserID, barberName, serviceName);
                                            agendamentoClass.setBarberID(barberName);

                                            if (filterStatus.equals("all")) {
                                                agendamentoList.add(agendamentoClass);
                                            }
                                            else if (filterStatus.equals("Confirmed")) {
                                                if (agendamentoClass.getStatusID() == 1)
                                                    agendamentoList.add(agendamentoClass);
                                            }
                                            else if (filterStatus.equals("Finished")) {
                                                if (agendamentoClass.getStatusID() == 2){
                                                    agendamentoList.add(agendamentoClass);
                                                }
                                            }
                                            else if (filterStatus.equals("Canceled")) {
                                                if (agendamentoClass.getStatusID() == 3){
                                                    agendamentoList.add(agendamentoClass);
                                                }
                                            }
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    };
                                    barbersRef.child(barberID).addListenerForSingleValueEvent(barberListener);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            };
                            servicesRef.child(serviceID).addValueEventListener(serviceListener);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    appointmentsRef.child(appointmentID).addListenerForSingleValueEvent(appointmentListener);
                    /*
                    Query appointmentFilter = appointmentsRef.child(appointmentID).orderByChild("statusID").equalTo(1);
                    appointmentFilter.addListenerForSingleValueEvent(appointmentListener);

                    else if (filterStatus == 1) {
                        Query appointmentFilter = appointmentsRef.child(appointmentID).orderByChild("/statusID").equalTo(1);
                        appointmentFilter.addListenerForSingleValueEvent(appointmentListener);
                    }

                     */
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        Query query = clientsRef.child(currentUserID).child("appointments")
                .orderByChild("/date");
        query.addListenerForSingleValueEvent(clientListener);
    }

/*
    private void showHomeName(){
        clientsRef = database.getReference("clients");

        Query checkClientsDatabase = clientsRef.child(currentUserID);

        checkClientsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homeName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

 */

    private void setBtnNewOnClickListener(){
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Servicos.class);
                startActivity(intent);
            }
        });
    }
}