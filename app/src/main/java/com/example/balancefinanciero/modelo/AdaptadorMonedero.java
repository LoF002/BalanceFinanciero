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
    }

    @NonNull
    @Override
    public ViewHolderMonedero onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mov,null,false);
        return new ViewHolderMonedero(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMonedero holder, int position) {
        holder.detalle.setText(listaMonedero.get(position).getDetalle());
        holder.fecha.setText(listaMonedero.get(position).getFecha());
        holder.monto.setText(String.valueOf(listaMonedero.get(position).getMonto()));

        if (listaMonedero.get(position).getMonto()>=0){
            holder.monto.setTextColor(Color.parseColor("#218F3E"));
        }else{
            holder.monto.setTextColor(Color.parseColor("#DB1319"));
        }
    }

    @Override
    public int getItemCount() {
        return listaMonedero.size();
    }

    public class ViewHolderMonedero extends RecyclerView.ViewHolder {
        TextView detalle,fecha,monto;
        ImageView icon;

        public ViewHolderMonedero(@NonNull View itemView) {
            super(itemView);

            detalle = (TextView) itemView.findViewById(R.id.txt_descripcionId);
            fecha = (TextView) itemView.findViewById(R.id.txt_fechaId);
            monto = (TextView) itemView.findViewById(R.id.txt_montoId);
            icon = (ImageView) itemView.findViewById(R.id.img_imagenId);
        }
    }
}
