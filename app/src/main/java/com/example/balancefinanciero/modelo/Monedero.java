package com.example.balancefinanciero.modelo;


//Clase objeto monedero  para registrar montos en efectivo

public class Monedero {
    private double monto;
    private String detalle;
    private String fecha;

    public Monedero(double monto, String detalle, String fecha) {
        this.monto = monto;
        this.detalle = detalle;
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
