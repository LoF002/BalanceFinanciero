package com.example.balancefinanciero.fragments;

import static android.content.ContentValues.TAG;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.balancefinanciero.R;
import com.example.balancefinanciero.client.RetrofitCliente;
import com.example.balancefinanciero.service.RetrofitAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FechasFragment extends Fragment {

    ListView listaAPI;

    private RetrofitAPIService apiService;

    public FechasFragment() {
        // Required empty public constructor
    }


    //Verifica si hay informacion, si la hay la asigna  las variables
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fechas,container,false);

        listaAPI = (ListView) vista.findViewById(R.id.listaAPI);

        iniciarValores();


        return vista;
    }//Fin onCreateView

    private void iniciarValores() {
        apiService = RetrofitCliente.getApiService();
    }//Fin iniciarValores



}//Fin clase FechasFragment