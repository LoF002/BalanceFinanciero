package com.example.balancefinanciero.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.balancefinanciero.R;
import com.example.balancefinanciero.client.RetrofitCliente;
import com.example.balancefinanciero.modelo.AdaptadorFechas;
import com.example.balancefinanciero.service.RetrofitAPIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FechasFragment extends Fragment {

    List<String> actividadesLista;
    List<String> fechasLista;
    Map<String, List<String>> fechasAPI;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

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

        crearListaActividades();
        crearListaFechas();
        expandableListView = vista.findViewById(R.id.elvFechas);
        expandableListAdapter = new AdaptadorFechas(getContext(), actividadesLista, fechasAPI);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int ultimaPosicionExpandida = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (ultimaPosicionExpandida != -1 && groupPosition != ultimaPosicionExpandida){
                    expandableListView.collapseGroup(ultimaPosicionExpandida);
                }//Fin if
                ultimaPosicionExpandida = groupPosition;
            }//Fin onGroupExpand
        });//Fin setOnGroupExpandListener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selected = expandableListAdapter.getChild(groupPosition, childPosition).toString();
                Toast.makeText(getContext(), "Seleccionada: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //iniciarValores();


        return vista;
    }//Fin onCreateView

    private void crearListaActividades() {
        actividadesLista = new ArrayList<>();
        actividadesLista.add("Gastos de carrera y beneficios complementarios");
        actividadesLista.add("Alimentacion");
        actividadesLista.add("Pago de matrícula");
        actividadesLista.add("Matricula");
    }//Fin metodo crearLista

    private void crearListaFechas() {
        String[] gcbc = {"Gastos - Primera entrega", "Gastos - Segunda entrega", "Gastos - Tercera entrega", "Gastos - Cuarta entrega", "Gastos - Quinta entrega", "Gastos - Sexta entrega"};
        String[] alimentacion = {"Alimentacion - Primera entrega", "Alimentacion - Segunda entrega", "Alimentacion - Tercera entrega", "Alimentacion - Cuarta entrega", "Alimentacion - Quinta entrega", "Alimentacion - Sexta entrega"};
        String[] pagoMatricula = {"Pago - Primer cuota", "Pago - Segunda cuota"};
        String[] matricula = {"Matricula - Guia de horarios", "Matricula - Prematricula", "Matricula - Cita de matricula", "Matricula - Matricula"};

        fechasAPI = new HashMap<String, List<String>>();
        for (String actividad : actividadesLista){
            if (actividad.equals("Gastos de carrera y beneficios complementarios")){
                cargarFecha(gcbc);
            }//Fin if
            else if (actividad.equals("Alimentacion")){
                cargarFecha(alimentacion);
            }//Fin else if
            else if (actividad.equals("Pago de matrícula")){
                cargarFecha(pagoMatricula);
            }//Fin else if
            else if (actividad.equals("Matricula")){
                cargarFecha(matricula);
            }//Fin else if
            fechasAPI.put(actividad, fechasLista);
        }//Fin for
    }//Fin metodo crearListaFechas

    private void cargarFecha(String[] matricula) {
        fechasLista = new ArrayList<>();
        for (String fecha : matricula){
            fechasLista.add(fecha);
        }
    }

    private void iniciarValores() {
        apiService = RetrofitCliente.getApiService();
    }//Fin iniciarValores



}//Fin clase FechasFragment