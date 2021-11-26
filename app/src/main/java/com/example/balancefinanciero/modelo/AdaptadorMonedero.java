package com.example.balancefinanciero.modelo;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorMonedero extends RecyclerView.Adapter<AdaptadorMonedero.ViewHolderMonedero> {

    ArrayList<Monedero> listaMonedero;

    public AdaptadorMonedero(ArrayList<Monedero> listaMonedero) {
        this.listaMonedero = listaMonedero;
    }

    @NonNull
    @Override
    public ViewHolderMonedero onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMonedero holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderMonedero extends RecyclerView.ViewHolder {
        public ViewHolderMonedero(@NonNull View itemView) {
            super(itemView);
        }
    }
}
