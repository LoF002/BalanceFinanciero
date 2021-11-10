package com.example.balancefinanciero.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balancefinanciero.Modelo.AdaptadorMovimientos;
import com.example.balancefinanciero.Modelo.Movimiento;
import com.example.balancefinanciero.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class RegistroFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerDias, spinnerMeses;
    TextView ingresosTotales, gastosTotales;

    ArrayList<Movimiento> listaMovimientos;

    RecyclerView recyclerMomivientos;

    ImageButton btn_registrarMovimiento;

    public RegistroFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_registro, container, false);

        //Spinner dias
        spinnerDias = vista.findViewById(R.id.spinnerDias);
        ArrayAdapter<CharSequence> adapterSpinnerDias = ArrayAdapter.createFromResource(getContext(),
                R.array.dias, android.R.layout.simple_spinner_item);
        adapterSpinnerDias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDias.setAdapter(adapterSpinnerDias);
        spinnerDias.setOnItemSelectedListener(this);
        //Fin Spinner dias

        ingresosTotales = (TextView) vista.findViewById(R.id.txt_totalIngresos);
        gastosTotales = (TextView) vista.findViewById(R.id.txt_totalGastos);

        //Spinner meses
        spinnerMeses = vista.findViewById(R.id.spinnerMeses);
        ArrayAdapter<CharSequence> adapterSpinnerMeses = ArrayAdapter.createFromResource(getContext(),
                R.array.meses, android.R.layout.simple_spinner_item);
        adapterSpinnerMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(adapterSpinnerMeses);
        spinnerMeses.setOnItemSelectedListener(this);
        //Fin Spinner meses

        //codigo para usar recycler personalizado
        listaMovimientos=new ArrayList<>();
        recyclerMomivientos = (RecyclerView) vista.findViewById(R.id.recyclerMovimientos);
        recyclerMomivientos.setLayoutManager(new LinearLayoutManager(getContext()));
        AdaptadorMovimientos adapter=new AdaptadorMovimientos(listaMovimientos);
        recyclerMomivientos.setAdapter(adapter);
        //fin codigo recycler

        btn_registrarMovimiento = vista.findViewById(R.id.btn_registrarMovimiento);
        btn_registrarMovimiento.setOnClickListener((View)->{showDialog();});

        return vista;
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.transaction_dialog);

        //Initializing the views of the dialog.
        final EditText detalle = dialog.findViewById(R.id.et_detalleId);
        final EditText monto = dialog.findViewById(R.id.et_montoId);
        final RadioButton ingreso= dialog.findViewById(R.id.btn_rIngreso);
        final RadioButton  gasto= dialog.findViewById(R.id.btn_rGasto);
        Button guardar = dialog.findViewById(R.id.btn_guardar);
        Button cancelar = dialog.findViewById(R.id.btn_cancelar);
        //obtener una instancia del tiempo "ahora"
        Calendar calendario= Calendar.getInstance();
        // obtener el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //convertir
        String dia = dateFormat.format(calendario.getTime());

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();
                Boolean hasAccepted = termsCb.isChecked();
                populateInfoTv(name,age,hasAccepted);*/
                String detalleTransacion=detalle.getText().toString();
                double montoTransaccion;

                try {
                    montoTransaccion = Double.parseDouble(monto.getText().toString());
                }catch (Exception e){
                    montoTransaccion=0;
                }
                boolean valorIngreso=false;
                //se corrigen valores negativos
                if (montoTransaccion<0){
                    montoTransaccion=montoTransaccion*-1;
                }
                if(ingreso.isChecked()){
                    valorIngreso=true;
                }
                if(gasto.isChecked()){
                    valorIngreso=false;
                    montoTransaccion=montoTransaccion*-1;//convierte en negativo el valor si se trata de un gasto
                }

                if((ingreso.isChecked()||gasto.isChecked())&&!detalleTransacion.isEmpty()&&montoTransaccion!=0) {
                    Movimiento nuevoMovimiento = new Movimiento(dia, detalleTransacion, montoTransaccion, valorIngreso);
                    llenarMovientos(nuevoMovimiento);
                    actualizarBalance(montoTransaccion);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Faltan datos", Toast.LENGTH_LONG).show();
                }
            }//Fin del onClick
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }//Fin del dialog

    private void llenarMovientos(Movimiento movimiento) {
        listaMovimientos.add(movimiento);
    }

    //Actualiza el monto actual de la cuenta
    private void actualizarBalance(double monto){
        double ingresosActuales = Double.parseDouble(ingresosTotales.getText().toString());
        double gastosActuales = Double.parseDouble(gastosTotales.getText().toString());

        if(monto<0){
            gastosTotales.setText(String.valueOf(gastosActuales+monto));
        }//Fin else
        else{
            ingresosTotales.setText(String.valueOf(ingresosActuales+monto));
        }//Fin else
    }//Fin metodo

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}