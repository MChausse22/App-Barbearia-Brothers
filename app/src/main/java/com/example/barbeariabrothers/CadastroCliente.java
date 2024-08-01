package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import com.example.barbeariabrothers.databinding.ActivityCadastroClienteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroCliente extends AppCompatActivity {

    private ActivityCadastroClienteBinding binding;
    private FirebaseAuth auth;

    EditText signupName, signupEmail, signupPassword;
    TextView loginRedirect;
    AppCompatButton signupButton;
    DatabaseReference clientRef;

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

        auth = FirebaseAuth.getInstance();
        auth.signOut();
        clientRef = FirebaseDatabase.getInstance().getReference("clients");

        signupName = binding.signupNameC;
        signupEmail = binding.signupEmailC;
        signupPassword = binding.signupPasswordC;
        signupButton = binding.signupButtonC;
        loginRedirect = binding.loginRedirectC;

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroCliente.this, LoginCliente.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLoggedInState();
    }

    public void signUpUser(){

        String name = signupName.getText().toString();
        String email = signupEmail.getText().toString();
        String psswd = signupPassword.getText().toString();

        if (!name.isEmpty()){
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if (psswd.length() > 5){
                    auth.createUserWithEmailAndPassword(email, psswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String userID = auth.getCurrentUser().getUid();
                                HelperClass helperClass = new HelperClass(name, email, psswd);
                                clientRef.child(userID).setValue(helperClass);

                                Toast.makeText(CadastroCliente.this, "Cadastro Realizado!", Toast.LENGTH_SHORT).show();
                                checkLoggedInState();
                        /*
                        Intent intent = new Intent(CadastroCliente.this, LoginCliente.class);
                        startActivity(intent);
                        finish();
                         */
                            } else {
                                Toast.makeText(CadastroCliente.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    signupPassword.setError("Senha deve ter no mínimo 6 caracteres");
                    signupPassword.requestFocus();
                }
            }
            else if (email.isEmpty()){
                signupEmail.setError("Campo obrigatório");
                signupEmail.requestFocus();
            }
            else {
                signupEmail.setError("Insira um e-mail válido");
                signupEmail.requestFocus();
            }
        }
        else {
            signupName.setError("Campo obrigatório!");
            signupName.requestFocus();
        }
    }

    private void checkLoggedInState(){
        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(CadastroCliente.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

     /*

                AUTH COM REALTIME

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

                 */

}

