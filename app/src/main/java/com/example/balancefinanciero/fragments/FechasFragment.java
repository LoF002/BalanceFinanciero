package com.example.balancefinanciero.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.balancefinanciero.R;
import com.example.balancefinanciero.client.RetrofitCliente;
import com.example.balancefinanciero.service.RetrofitAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FechasFragment extends Fragment {

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

        //iniciarValores();


        return vista;
    }//Fin onCreateView

    private void iniciarValores() {
        apiService = RetrofitCliente.getApiService();
    }//Fin iniciarValores



}//Fin clase FechasFragment