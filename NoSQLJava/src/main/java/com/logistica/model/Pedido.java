package com.logistica.model;

/**
 * Clase que representa un pedido embebido dentro de un Cliente.
 * Demuestra la flexibilidad de NoSQL para anidar documentos.
 */
public class Pedido {
    
    private String producto;
    private Double precio;

    // Constructores
    public Pedido() {
    }

    public Pedido(String producto, Double precio) {
        this.producto = producto;
        this.precio = precio;
    }

    // Getters y Setters
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "producto='" + producto + '\'' +
                ", precio=" + precio +
                '}';
    }
}
