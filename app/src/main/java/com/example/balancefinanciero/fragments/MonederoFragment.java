package com.example.balancefinanciero.fragments;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balancefinanciero.R;
import com.example.balancefinanciero.modelo.AdaptadorMonedero;
import com.example.balancefinanciero.modelo.Monedero;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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

        txt_totalMonedero = (TextView) vista.findViewById(R.id.txt_totalMonedero);

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
        final Dialog dialog = new Dialog(getContext());//se declara
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.monedero_dialog);

        //Initializing the views of the dialog.
        final EditText detalle = dialog.findViewById(R.id.et_detalleCuenta);
        final EditText monto = dialog.findViewById(R.id.et_montoId);//se iguala
        final EditText fecha = dialog.findViewById(R.id.et_fechaM);
        Button guardar = dialog.findViewById(R.id.btn_guardarCuenta);
        Button cancelar = dialog.findViewById(R.id.btn_cancelarAC);

        //obtener una instancia del tiempo "ahora"
        Calendar calendario= Calendar.getInstance();// se declara la variable del calendario
        // obtener el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //convertir
        String dia = dateFormat.format(calendario.getTime());

        guardar.setOnClickListener(new View.OnClickListener() {//se le da funcionabilidad al boton
            @Override
            public void onClick(View v) {
                /*String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();
                Boolean hasAccepted = termsCb.isChecked();
                populateInfoTv(name,age,hasAccepted);*/
                String detalleTransacion=detalle.getText().toString();
                double montoTransaccion;

                try {
                    montoTransaccion = Double.parseDouble(monto.getText().toString());//se obtiene el monton para igualarlo a una variable
                }catch (Exception e){
                    montoTransaccion=0;//se iguala a 0 en caso de que el monto sea null o algo diferente a lo esperado
                }
                boolean valorIngreso=false;
                //se corrigen valores negativos

                if (montoTransaccion<0){
                    montoTransaccion=montoTransaccion*-1;
                }

                /*
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
                }*/

                valorIngreso=true;
                if(valorIngreso && !detalleTransacion.isEmpty() && montoTransaccion !=0) {
                    Monedero nuevoMonedero = new Monedero(montoTransaccion, detalleTransacion, "10 / sep / 21");
                    llenarMonedero(nuevoMonedero);
                    actualizarBalance(montoTransaccion);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Faltan datos", Toast.LENGTH_LONG).show();
                }

            }//Fin del onClick
        });//fin del set onclick listener
        cancelar.setOnClickListener(new View.OnClickListener() {//funcion del boton de cancelar
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }//fin del onClick
        });//fin del set onclick listener

        dialog.show();

    }//Fin del metodo

    private void llenarMonedero(Monedero monedero) {
        listMonedero.add(monedero);
    }

    //Actualiza el monto actual de la cuenta
    private void actualizarBalance(double monto){
        double ingresosActuales = Double.parseDouble(txt_totalMonedero.getText().toString());

        txt_totalMonedero.setText(String.valueOf(ingresosActuales+monto));

    }//Fin metodo

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}