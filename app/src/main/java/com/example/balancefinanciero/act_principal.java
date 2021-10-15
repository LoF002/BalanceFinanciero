package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balancefinanciero.Modelo.AdaptadorMovimientos;
import com.example.balancefinanciero.Modelo.Movimiento;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class act_principal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<Movimiento> listaMovimientos;
    RecyclerView recyclerMomivientos;
    ImageButton registrarMovimiento;
    TextView ingresosTotales, gastosTotales;

    Spinner spinnerDias, spinnerMeses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_principal);

        ingresosTotales = (TextView) findViewById(R.id.txt_totalIngresos);
        gastosTotales = (TextView) findViewById(R.id.txt_totalGastos);

        //codigo para usar recycler personalizado
        listaMovimientos=new ArrayList<>();
        recyclerMomivientos= (RecyclerView) findViewById(R.id.recyclerMovimiento);
        recyclerMomivientos.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorMovimientos adapter=new AdaptadorMovimientos(listaMovimientos);
        recyclerMomivientos.setAdapter(adapter);
        //fin codigo recycler

        //Codigo para actuallizar movimientos
        registrarMovimiento= (ImageButton) findViewById(R.id.btn_registrarMov);
        registrarMovimiento.setOnClickListener((View)->{showDialog();});

        //Spinner dias
        spinnerDias = findViewById(R.id.spinnerDias);
        ArrayAdapter<CharSequence> adapterSpinnerDias = ArrayAdapter.createFromResource(this,
                R.array.dias, android.R.layout.simple_spinner_item);
        adapterSpinnerDias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDias.setAdapter(adapterSpinnerDias);
        spinnerDias.setOnItemSelectedListener(this);

        //Spinner meses
        spinnerMeses = findViewById(R.id.spinnerMeses);
        ArrayAdapter<CharSequence> adapterSpinnerMeses = ArrayAdapter.createFromResource(this,
                R.array.meses, android.R.layout.simple_spinner_item);
        adapterSpinnerMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(adapterSpinnerMeses);
        spinnerMeses.setOnItemSelectedListener(this);

    }//Fin onCreate

    private void showDialog() {
        final Dialog dialog = new Dialog(act_principal.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.transaction_dialog);

        //Initializing the views of the dialog.
        final EditText detalle = dialog.findViewById(R.id.et_detalleId);
        final EditText monto = dialog.findViewById(R.id.et_montoId);
        final RadioButton  ingreso= dialog.findViewById(R.id.btn_rIngreso);
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
                    Toast.makeText(getApplicationContext(),"Faltan datos", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void llenarMovientos(Movimiento movimiento) {
        listaMovimientos.add(movimiento);
    }

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}