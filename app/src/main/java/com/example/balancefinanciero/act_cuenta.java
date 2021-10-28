package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.balancefinanciero.Modelo.AdaptadorCuentas;
import com.example.balancefinanciero.Modelo.AdaptadorMovimientos;
import com.example.balancefinanciero.Modelo.Cuenta;
import com.example.balancefinanciero.Modelo.Movimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class act_cuenta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Cuenta> listaCuentas;

    RecyclerView recyclerCuentas;

    ImageButton btn_registrarCuenta;

    ImageButton btn_vistaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_cuenta);

        //codigo para usar recycler personalizado
        listaCuentas=new ArrayList<>();
        recyclerCuentas= (RecyclerView) findViewById(R.id.recyclerCuentas);
        recyclerCuentas.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorCuentas adapter=new AdaptadorCuentas(listaCuentas);
        recyclerCuentas.setAdapter(adapter);
        //fin codigo recycler

        //Codigo para actuallizar cuentas
        btn_registrarCuenta= (ImageButton) findViewById(R.id.btn_registrarCuenta);
        btn_registrarCuenta.setOnClickListener((View)->{showDialog();});

        btn_vistaRegistro = findViewById(R.id.btn_vistaRegistro2);

        //Listener del boton para pasar a la vista de registro
        btn_vistaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent hacia la clase principal (Registro de movimientos)
                Intent intent = new Intent(act_cuenta.this, act_principal.class);
                startActivity(intent);
            }//Fin onclick
        });//Fin de btn_vistaRegistro

    }//Fin del onCreate

    private void showDialog() {
        final Dialog dialog = new Dialog(act_cuenta.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_account_dialog);

        //Initializing the views of the dialog.
        final EditText entidad = dialog.findViewById(R.id.et_entidadAC);
        final EditText monto = dialog.findViewById(R.id.et_saldoInicialAC);
        final RadioButton debito= dialog.findViewById(R.id.btn_rDebito);
        final RadioButton  credito= dialog.findViewById(R.id.btn_rCredito);
        Button guardar = dialog.findViewById(R.id.btn_guardarCuenta);
        Button cancelar = dialog.findViewById(R.id.btn_cancelarAC);
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
                String entidadCuenta = entidad.getText().toString();
                double saldoCuenta = 0;
                String tipoCuenta = "";

                try {
                    saldoCuenta = Double.parseDouble(monto.getText().toString());
                }catch (Exception e){
                    saldoCuenta=0;
                }
                //se corrigen valores negativos
                if (saldoCuenta<0){
                    saldoCuenta=saldoCuenta*-1;
                }
                if(debito.isChecked()){
                    tipoCuenta="Débito";
                }
                if(credito.isChecked()){
                    tipoCuenta="Crédito";
                }

                if((debito.isChecked()||credito.isChecked())&&!entidadCuenta.isEmpty()&&saldoCuenta!=0) {
                    Cuenta nuevaCuenta = new Cuenta("", "", entidadCuenta, tipoCuenta, true, saldoCuenta);
                    llenarCuentas(nuevaCuenta);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Faltan datos", Toast.LENGTH_LONG).show();
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
    }//Fin metodo showDialog

    private void llenarCuentas(Cuenta cuenta){
        listaCuentas.add(cuenta);
    }//Fin metodo para agrgar cuentas en un array

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}//Fin de la clase