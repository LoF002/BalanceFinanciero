package com.example.balancefinanciero.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.balancefinanciero.R;
import com.example.balancefinanciero.modelo.AdaptadorMonedero;
import com.example.balancefinanciero.modelo.Monedero;

import java.util.ArrayList;


public class MonederoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView txt_totalMonedero;
    ImageButton btn_RegistrarMonedero;
    RecyclerView recyclerMonedero;

    ArrayList<Monedero> listMonedero;


    public MonederoFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_monedero,container,false);

        txt_totalMonedero = vista.findViewById(R.id.txt_totalMonedero);

        listMonedero = new ArrayList<>();
        recyclerMonedero = (RecyclerView) vista.findViewById(R.id.recyclerMonedero);
        recyclerMonedero.setLayoutManager(new LinearLayoutManager(getContext()));
        AdaptadorMonedero adaptador = new AdaptadorMonedero(listMonedero);
        recyclerMonedero.setAdapter(adaptador);

        btn_RegistrarMonedero = (ImageButton) vista.findViewById(R.id.btn_registrarMonedero);
        btn_RegistrarMonedero.setOnClickListener((View)->{showDialog();});


        return vista;
    }

    private void showDialog(){

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}