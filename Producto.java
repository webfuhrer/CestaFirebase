package com.example.luis.pruebasfirebase;

/**
 * Created by luis on 09/01/2018.
 */

public class Producto {
    private String nombre, cantidad, observaciones, ruta_imagen;

    public Producto(String nombre, String cantidad, String observaciones, String ruta_imagen) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.ruta_imagen = ruta_imagen;
    }

    public Producto() {

    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }
}
