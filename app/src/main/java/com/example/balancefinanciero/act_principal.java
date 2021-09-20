package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.balancefinanciero.Modelo.AdaptadorMovimientos;
import com.example.balancefinanciero.Modelo.Movimiento;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class act_principal extends AppCompatActivity {
    ArrayList<Movimiento> listaMovimientos;
    RecyclerView recyclerMomivientos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_principal);

        //codigo de prueba para usar recicler personalizado
        listaMovimientos=new ArrayList<>();
        recyclerMomivientos= (RecyclerView) findViewById(R.id.recyclerMovimiento);
        recyclerMomivientos.setLayoutManager(new LinearLayoutManager(this));
        llenarMovientos();
        AdaptadorMovimientos adapter=new AdaptadorMovimientos(listaMovimientos);
        recyclerMomivientos.setAdapter(adapter);
        //fin codigo prueba

    }

    private void llenarMovientos() {
        listaMovimientos.add(new Movimiento("ahora", "pollo",1000, false));
        listaMovimientos.add(new Movimiento("11-12-2021", "sinpe",3000, false));
    }
}