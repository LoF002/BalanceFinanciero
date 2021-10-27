package com.example.balancefinanciero.Modelo;

import java.util.ArrayList;

public class Cuenta {
    String idCuenta,idUsuario, nombre, tipo;
    boolean esEncolones;
    ArrayList<Movimiento> listaMovientos;

    public Cuenta(String idCuenta, String idUsuario, String nombre, String tipo, boolean esEncolones) {
        this.idCuenta = idCuenta;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.tipo = tipo;
        this.esEncolones = esEncolones;
    }

    public Cuenta() {
        this.idCuenta = "";
        this.idUsuario = "";
        this.nombre = "";
        this.tipo = "";
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEsEncolones() {
        return esEncolones;
    }

    public void setEsEncolones(boolean esEncolones) {
        this.esEncolones = esEncolones;
    }

    public ArrayList<Movimiento> getListaMovientos() {
        return listaMovientos;
    }

    public void setListaMovientos(ArrayList<Movimiento> listaMovientos) {
        this.listaMovientos = listaMovientos;
    }
}
