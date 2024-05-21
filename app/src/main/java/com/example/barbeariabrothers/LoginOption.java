package com.example.barbeariabrothers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginOption extends AppCompatActivity {

    AppCompatButton btnBarbeiro;
    AppCompatButton btnCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_option);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBarbeiro = findViewById(R.id.btnBarbeiro);
        btnCliente = findViewById(R.id.btnCliente);

        setBtnBarbeiroOnClick();
        setBtnClienteOnClick();
    }

    private void setBtnBarbeiroOnClick(){
        btnBarbeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOption.this, LoginBarbeiro.class);
                startActivity(intent);
            }
        });
    }

    private void setBtnClienteOnClick(){
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOption.this, LoginCliente.class);
                startActivity(intent);
            }
        });
    }
}