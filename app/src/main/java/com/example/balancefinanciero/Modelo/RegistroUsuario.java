package com.example.balancefinanciero.Modelo;

import java.util.ArrayList;

public class RegistroUsuario {

    ArrayList <Usuario> listaUsuarios;

    public RegistroUsuario() {
        this.listaUsuarios = new ArrayList<Usuario>();
    }//Fin constructor

    public int buscarPosicion(String usuario){
        if(usuario!=null){
            for (int i = 0; i < listaUsuarios.size() ; i++) {
                if (listaUsuarios.get(i).getUsuario().equalsIgnoreCase(usuario)){
                    return i;
                }//fin for
            }//fin for
        }//fin if
        return -1;
    }//Fin buscarPosicion

    public String agregarUsuario(Usuario usuario){
        if(usuario!=null){
            if(buscarPosicion(usuario.getUsuario())==-1){
                listaUsuarios.add(usuario);
                return "Agregado correctamente";
            }//fin if
            else{
                return "Ya existe";
            }//fin else
        }//Fin if
        return "Error al agregar";
    }//Fin agregarUsuario

    public String getInformacionUsuario(int posicion){
        if(posicion!=-1){
            return listaUsuarios.get(posicion).toString();
        }//Fin if
        else{
            return "No existe";
        }//Fin else
    }//Fin metodo getInformacion

    public Usuario devolverUsuario(int posicion){
        if(posicion!=-1){
            return listaUsuarios.get(posicion);
        }//Fin if
        else{
            return null;
        }//Fin else
    }//fin metodo devolver

    public ArrayList<Usuario> devolverLista(){
        return listaUsuarios;
    }//Fin metodo devolverlista

}//Fin clase RegistroUsuario
