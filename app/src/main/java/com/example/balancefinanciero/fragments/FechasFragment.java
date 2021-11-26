package com.example.balancefinanciero.fragments;

import static android.content.ContentValues.TAG;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.balancefinanciero.R;
import com.example.balancefinanciero.client.RetrofitCliente;
import com.example.balancefinanciero.modelo.Dog;
import com.example.balancefinanciero.service.RetrofitAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FechasFragment extends Fragment {

    ImageView imgDog;
    Button btnGenerar;

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

        imgDog = (ImageView) vista.findViewById(R.id.imgDog);
        btnGenerar = (Button) vista.findViewById(R.id.btnGenerar);

        iniciarValores();

        getDog();

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDog();
            }//Fin onClick
        });//Fin setOnClickListener

        return vista;
    }//Fin onCreateView

    private void iniciarValores() {
        apiService = RetrofitCliente.getApiService();
    }//Fin iniciarValores

    public void getDog(){
        apiService.getDog().enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                Dog dog = response.body();

                Glide.with(getContext())
                        .load(dog.getMessage())
                        .into(imgDog);
            }//Fin onResponse

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }//Fin onFailure
        });
    }//Fin getDog

}//Fin clase FechasFragment