package com.logistica.repository;

import com.logistica.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Cliente.
 * Utiliza Derived Queries de Spring Data MongoDB para generar consultas automáticamente.
 */
@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    
    // Derived Query: busca clientes por nombre exacto
    List<Cliente> findByNombre(String nombre);
    
    // Derived Query: busca clientes por apellidos exactos
    List<Cliente> findByApellidos(String apellidos);
    
    // Derived Query: busca cliente por email
    Cliente findByEmail(String email);
}
