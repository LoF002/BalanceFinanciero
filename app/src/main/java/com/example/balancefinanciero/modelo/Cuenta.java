package com.example.balancefinanciero.modelo;

import java.util.ArrayList;

public class Cuenta {

    //clase del objeto cuenta que sera usado para registrar las cuentas del usuario

    String idCuenta,idUsuario, nombre, detalle;
    boolean esEncolones;
    double monto;
    ArrayList<Movimiento> listaMovientos;

    public Cuenta(String idCuenta, String idUsuario, String nombre,String detalle, boolean esEncolones, double monto) {
        this.idCuenta = idCuenta;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.detalle=detalle;
        this.esEncolones = esEncolones;
        this.monto = monto;
    }

    public Cuenta() {
        this.idCuenta = "";
        this.idUsuario = "";
        this.nombre = "";
        this.monto = 0;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
