package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class act_cuenta extends AppCompatActivity {

    ImageButton btn_vistaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_cuenta);

        btn_vistaRegistro = findViewById(R.id.btn_vistaRegistro2);

        btn_vistaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_cuenta.this, act_principal.class);
                startActivity(intent);
            }
        });

    }
}