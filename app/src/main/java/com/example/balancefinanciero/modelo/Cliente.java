package com.example.balancefinanciero.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {

    private String usuario, contrasena, nombres, apellidos;

    public Cliente(String usuario, String contrasena, String nombres, String apellidos) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Cliente() {
        this.usuario = "";
        this.contrasena = "";
        this.nombres = "";
        this.apellidos = "";
    }

    protected Cliente(Parcel in){
        usuario = in.readString();
        contrasena = in.readString();
        nombres = in.readString();
        apellidos = in.readString();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }
        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public String getUsuario() {
        return usuario;
    }//Fin get

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(usuario);
        parcel.writeString(contrasena);
        parcel.writeString(nombres);
        parcel.writeString(apellidos);
    }
}//Fin clase Cliente
