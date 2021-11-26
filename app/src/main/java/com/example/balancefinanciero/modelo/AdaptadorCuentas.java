package com.example.balancefinanciero.modelo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balancefinanciero.R;

import java.util.ArrayList;

public class AdaptadorCuentas extends RecyclerView.Adapter<AdaptadorCuentas.ViewHolderCuentas>{

    ArrayList<Cuenta> listaCuentas;

    public AdaptadorCuentas(ArrayList<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }//Fin constructor con parametros por recibir

    //Asigna como se va a ver la lista(De acuerdo al lyt item_list_mov, para este caso este item nos sirve)
    @NonNull
    @Override
    public AdaptadorCuentas.ViewHolderCuentas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mov, null, false);
        return new ViewHolderCuentas(vista);
    }//Fin del onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCuentas holder, int position) {
        holder.entidad.setText(listaCuentas.get(position).getNombre());
        holder.monto.setText(String.valueOf(listaCuentas.get(position).getMonto()));

        //Asigna un color al monto dependiendo de su valor
        if(listaCuentas.get(position).getMonto()<=0){
            holder.monto.setTextColor(Color.parseColor("#DB1319"));
        }else{
            holder.monto.setTextColor(Color.parseColor("#218F3E"));
        }//Fin del else
    }//Fin onBindViewHolder

    //Cuenta los items dentro de la lista
    @Override
    public int getItemCount() {
        return listaCuentas.size();
    }//Fin getItemCount

    public class ViewHolderCuentas extends RecyclerView.ViewHolder{

        TextView entidad, monto;
        ImageView icono;

        public ViewHolderCuentas(@NonNull View itemView) {
            super(itemView);
            entidad = (TextView) itemView.findViewById(R.id.txt_descripcionId);
            monto= (TextView) itemView.findViewById(R.id.txt_fechaId);
            icono= (ImageView) itemView.findViewById(R.id.img_imagenId);
        }//Fin ViewHolderCuentas
    }//Fin clase ViewHolderCuentas

}//Fin clase AdaptadorCuentas
