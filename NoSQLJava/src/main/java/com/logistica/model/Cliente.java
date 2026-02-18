package com.logistica.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Cliente en la base de datos MongoDB.
 * Demuestra el uso de documentos embebidos (pedidos) característico de NoSQL.
 */
@Document(collection = "clientes")
public class Cliente {
    
    @Id
    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    
    // Lista embebida de pedidos - característica NoSQL
    private List<Pedido> pedidos;

    // Constructores
    public Cliente() {
        this.pedidos = new ArrayList<>();
    }

    public Cliente(String nombre, String apellidos, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.pedidos = new ArrayList<>();
    }

    public Cliente(String nombre, String apellidos, String email, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.pedidos = pedidos != null ? pedidos : new ArrayList<>();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    // Método auxiliar para agregar pedidos
    public void agregarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", pedidos=" + pedidos +
                '}';
    }
}
