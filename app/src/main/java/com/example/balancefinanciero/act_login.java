package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class act_login extends AppCompatActivity {

    EditText txtUsuario, txtContraseña;
    Button btnIngresar, btnRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login);

        txtUsuario = findViewById(R.id.txtUsuarioLogin);
        txtContraseña = findViewById(R.id.txtContrasenaLogin);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrate = findViewById(R.id.btnRegistrate);

        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_login.this, act_registro.class);
                startActivity(intent);
            }//Fin Onclick
        });//Fin btnRegistrate

    }//Fin onCreate

}//Fin clase