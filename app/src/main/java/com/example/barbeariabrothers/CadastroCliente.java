package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.barbeariabrothers.databinding.ActivityCadastroClienteBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroCliente extends AppCompatActivity {

    private ActivityCadastroClienteBinding binding;

    EditText signupName, signupUsername, signupPassword;
    TextView loginRedirect;
    AppCompatButton signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroClienteBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupName = binding.signupNameC;
        signupUsername = binding.signupEmailC;
        signupPassword = binding.signupPasswordC;
        signupButton = binding.signupButtonC;
        loginRedirect = binding.loginRedirectC;

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                HelperClass helperClass = new HelperClass(name, username, password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(CadastroCliente.this, "Cadastro Realizado!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CadastroCliente.this, LoginCliente.class);
                startActivity(intent);
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroCliente.this, LoginCliente.class);
                startActivity(intent);
            }
        });
    }
}