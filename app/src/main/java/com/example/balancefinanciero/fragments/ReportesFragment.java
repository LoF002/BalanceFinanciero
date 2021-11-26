package com.example.balancefinanciero.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.balancefinanciero.R;

public class ReportesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //declaracion de los textview
    TextView txt_numeroMovimientos, txt_numeroCantidadCuentas, txt_numeroGastos, txt_numeroTotalIngresos, txt_numeroDineroEfectivo, txt_numeroTotalDineroActual, txt_numeroTotalCuenta;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReportesFragment newInstance(String param1, String param2) {
        ReportesFragment fragment = new ReportesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }// fin de ReportesFragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }//fin del oncreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_reportes, container, false);//declaracion de la vista
        //inicializacion de los text view
        txt_numeroMovimientos = vista.findViewById(R.id.txt_numeroMovimientos);
        txt_numeroCantidadCuentas = vista.findViewById(R.id.txt_numeroCantidadCuentas);
        txt_numeroGastos = vista.findViewById(R.id.txt_numeroGastos);
        txt_numeroTotalIngresos = vista.findViewById(R.id.txt_numeroTotalIngresos);
        txt_numeroDineroEfectivo = vista.findViewById(R.id.txt_numeroDineroEfectivo);
        txt_numeroTotalDineroActual = vista.findViewById(R.id.txt_numeroTotalDineroActual);
        txt_numeroTotalCuenta = vista.findViewById(R.id.txt_numeroTotalCuenta);


        return vista;//se devuelve la vista a usar
    }//fin del onCreateView
}//fin de la clase