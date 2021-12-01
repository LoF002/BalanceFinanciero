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

public class AdaptadorMonedero extends RecyclerView.Adapter<AdaptadorMonedero.ViewHolderMonedero> {

    ArrayList<Monedero> listaMonedero;

    public AdaptadorMonedero(ArrayList<Monedero> listaMonedero) {
        this.listaMonedero = listaMonedero;
    }//Fin de AdaptadorMonederos con parametros por recibir

    //Asigna como se va a ver la lista(De acuerdo al lyt item_list_mov)
    @NonNull
    @Override
    public ViewHolderMonedero onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mov,null,false);
        return new ViewHolderMonedero(vista);
    }//Fib de onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMonedero holder, int position) {
        holder.detalle.setText(listaMonedero.get(position).getDetalle());
        holder.fecha.setText(listaMonedero.get(position).getFecha());
        holder.monto.setText(String.valueOf(listaMonedero.get(position).getMonto()));

        //Asigna un color al monto dependiendo de su valor
        if (listaMonedero.get(position).getMonto()>=0){
            holder.monto.setTextColor(Color.parseColor("#218F3E"));
        }else{
            holder.monto.setTextColor(Color.parseColor("#DB1319"));
        }//Fin del else
    }//Fin de onBinViewHolder

    //Cuenta los items dentro de la lista
    @Override
    public int getItemCount() {
        return listaMonedero.size();
    }

    public class ViewHolderMonedero extends RecyclerView.ViewHolder {
        TextView detalle,fecha,monto;


        //Convierte la informacion a Strings para insertarlos en la vista
        public ViewHolderMonedero(@NonNull View itemView) {
            super(itemView);

            detalle = (TextView) itemView.findViewById(R.id.txt_descripcionId);
            fecha = (TextView) itemView.findViewById(R.id.txt_fechaId);
            monto = (TextView) itemView.findViewById(R.id.txt_montoId);

        }//Fin de metodo ViewHolderMonedero
    }//Fin de Clase ViewHolderMonedero
}//Fin de Clase
