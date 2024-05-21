package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.barbeariabrothers.databinding.ActivityLoginClienteBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginCliente extends AppCompatActivity {

    private ActivityLoginClienteBinding binding;

    EditText loginUsername, loginPassword;
    AppCompatButton loginButton;
    TextView signupRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginClienteBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginUsername = binding.loginEmailC;
        loginButton = binding.loginButtonC;
        loginPassword = binding.loginPasswordC;
        signupRedirect = binding.signupRedirectC;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() || !validatePassword()){}
                else {
                    checkUser();
                }
            }
        });

        signupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginCliente.this, CadastroCliente.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername(){
        String email = loginUsername.getText().toString();
        if (email.isEmpty()){
            loginUsername.setError("campo obrigatório");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String password = loginPassword.getText().toString();
        if (password.isEmpty()){
            loginPassword.setError("campo obrigatório");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUsersDatabase = reference.child(userUsername);
        checkUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)){

                        loginUsername.setError(null);

                        String usernameFromDB = snapshot.child("username").getValue(String.class);

                        Intent intent = new Intent(LoginCliente.this, MainActivity.class);
                        intent.putExtra(MainActivity.USERNAME, usernameFromDB);
                        startActivity(intent);

                    } else {

                        loginPassword.setError("Senha incorreta");
                        loginPassword.requestFocus();
                    }

                } else {

                    loginUsername.setError("Username incorreto");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}