package com.logistica;

import com.logistica.model.Cliente;
import com.logistica.model.Pedido;
import com.logistica.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

/**
 * Clase principal de la aplicación Spring Boot.
 * Implementa CommandLineRunner para ejecutar la lógica al inicio.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(ClienteRepository repository) {
        return args -> {
            System.out.println("\n========================================");
            System.out.println("INICIANDO SISTEMA DE GESTIÓN DE CLIENTES");
            System.out.println("========================================\n");

            // PASO 1: Limpieza de datos existentes
            System.out.println(">>> PASO 1: Limpiando base de datos...");
            repository.deleteAll();
            System.out.println("✓ Base de datos limpiada correctamente\n");

            // PASO 2: Inserción de clientes con diferentes configuraciones
            System.out.println(">>> PASO 2: Insertando clientes en MongoDB...\n");

            // Cliente 1: Alicia con varios pedidos
            Cliente cliente1 = new Cliente("Alicia", "González Martínez", "alicia.gonzalez@email.com");
            cliente1.agregarPedido(new Pedido("Laptop Dell XPS 15", 1299.99));
            cliente1.agregarPedido(new Pedido("Mouse Logitech MX Master", 99.99));
            cliente1.agregarPedido(new Pedido("Teclado Mecánico", 149.99));
            repository.save(cliente1);
            System.out.println("✓ Cliente insertado: " + cliente1.getNombre() + " " + cliente1.getApellidos() +
                    " (con " + cliente1.getPedidos().size() + " pedidos)");

            // Cliente 2: Roberto sin pedidos (demuestra flexibilidad del esquema)
            Cliente cliente2 = new Cliente("Roberto", "Sánchez López", "roberto.sanchez@email.com");
            repository.save(cliente2);
            System.out.println("✓ Cliente insertado: " + cliente2.getNombre() + " " + cliente2.getApellidos() +
                    " (sin pedidos - esquema flexible)");

            // Cliente 3: María con un pedido
            Cliente cliente3 = new Cliente("María", "Rodríguez Pérez", "maria.rodriguez@email.com",
                    Arrays.asList(new Pedido("Monitor 4K Samsung", 599.99)));
            repository.save(cliente3);
            System.out.println("✓ Cliente insertado: " + cliente3.getNombre() + " " + cliente3.getApellidos() +
                    " (con " + cliente3.getPedidos().size() + " pedido)");

            System.out.println("\n>>> TOTAL: 3 clientes insertados correctamente\n");

            // PASO 3: Consulta con findAll()
            System.out.println("========================================");
            System.out.println(">>> PASO 3: Recuperando TODOS los clientes con findAll():");
            System.out.println("========================================\n");
            
            List<Cliente> todosLosClientes = repository.findAll();
            for (Cliente c : todosLosClientes) {
                System.out.println("📋 " + c);
            }
            System.out.println("\n>>> Total de clientes encontrados: " + todosLosClientes.size() + "\n");

            // PASO 4: Consulta por nombre (Derived Query)
            System.out.println("========================================");
            System.out.println(">>> PASO 4: Buscando cliente por nombre 'Alicia':");
            System.out.println("========================================\n");
            
            List<Cliente> clientesAlicia = repository.findByNombre("Alicia");
            if (!clientesAlicia.isEmpty()) {
                Cliente alicia = clientesAlicia.get(0);
                System.out.println("✓ Cliente encontrado:");
                System.out.println("  - ID: " + alicia.getId());
                System.out.println("  - Nombre completo: " + alicia.getNombre() + " " + alicia.getApellidos());
                System.out.println("  - Email: " + alicia.getEmail());
                System.out.println("  - Número de pedidos: " + alicia.getPedidos().size());
                System.out.println("  - Detalle de pedidos:");
                for (Pedido p : alicia.getPedidos()) {
                    System.out.println("    • " + p.getProducto() + " - $" + p.getPrecio());
                }
            } else {
                System.out.println("✗ No se encontró cliente con nombre 'Alicia'");
            }

            // PASO 5: Consulta por apellidos (Derived Query)
            System.out.println("\n========================================");
            System.out.println(">>> PASO 5: Buscando cliente por apellidos 'Sánchez López':");
            System.out.println("========================================\n");
            
            List<Cliente> clientesSanchez = repository.findByApellidos("Sánchez López");
            if (!clientesSanchez.isEmpty()) {
                Cliente roberto = clientesSanchez.get(0);
                System.out.println("✓ Cliente encontrado:");
                System.out.println("  - Nombre completo: " + roberto.getNombre() + " " + roberto.getApellidos());
                System.out.println("  - Email: " + roberto.getEmail());
                System.out.println("  - Pedidos: " + (roberto.getPedidos().isEmpty() ? "Sin pedidos" : roberto.getPedidos().size()));
            }

            // PASO 6: Consulta por email (Derived Query)
            System.out.println("\n========================================");
            System.out.println(">>> PASO 6: Buscando cliente por email 'maria.rodriguez@email.com':");
            System.out.println("========================================\n");
            
            Cliente maria = repository.findByEmail("maria.rodriguez@email.com");
            if (maria != null) {
                System.out.println("✓ Cliente encontrado:");
                System.out.println("  - Nombre completo: " + maria.getNombre() + " " + maria.getApellidos());
                System.out.println("  - Pedidos: " + maria.getPedidos());
            }

            System.out.println("\n========================================");
            System.out.println("✓ SISTEMA EJECUTADO CORRECTAMENTE");
            System.out.println("========================================\n");
        };
    }
}
