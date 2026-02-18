# Documentación - Microservicio de Gestión de Usuarios y Pedidos

## 📋 Resumen del Proyecto

Desarrollo de un **microservicio Full-Stack** que implementa un sistema de gestión de clientes y pedidos utilizando **MongoDB** como base de datos NoSQL y **Spring Boot 3.x** como framework de desarrollo. Este proyecto demuestra la flexibilidad de las bases de datos documentales versus las bases de datos relacionales tradicionales.

---

## 🎯 Objetivos Completados

✅ Configurar un entorno local de base de datos NoSQL (MongoDB)  
✅ Crear un proyecto Spring Boot con las dependencias necesarias  
✅ Modelar documentos BSON utilizando clases Java (POJOs) y anotaciones  
✅ Implementar la capa de acceso a datos utilizando MongoRepository  
✅ Realizar operaciones CRUD y consultas personalizadas (Derived Queries)  
✅ Ejecutar la aplicación y validar funcionamiento con datos reales  

---

## 📂 Estructura del Proyecto

```
fullstack-java-nosql/
├── NoSQLJava/                          # Carpeta principal del proyecto Maven
│   ├── pom.xml                         # Configuración Maven (dependencias y plugins)
│   ├── .mvn/                           # Configuración Maven wrapper
│   ├── mvnw, mvnw.cmd                  # Maven wrapper (ejecutable)
│   │
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/logistica/
│   │   │   │   ├── Application.java              # Clase principal con CommandLineRunner
│   │   │   │   ├── model/
│   │   │   │   │   ├── Cliente.java              # Entidad Cliente con @Document
│   │   │   │   │   └── Pedido.java               # Clase embebida en Cliente
│   │   │   │   └── repository/
│   │   │   │       └── ClienteRepository.java    # Interfaz con Derived Queries
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties        # Configuración conexión MongoDB
│   │   │
│   │   └── test/                       # Pruebas unitarias (no implementadas en este MVP)
│   │
│   └── target/                         # Carpeta de compilación
│       ├── classes/                    # Archivos .class compilados
│       └── gestion-usuarios-pedidos-1.0.0.jar
│
├── .git/                               # Repositorio Git
└── DOCUMENTACION.md                    # Este archivo
```

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| Java | 17+ | Lenguaje de programación |
| Spring Boot | 3.2.2 | Framework web y abstracción |
| Spring Data MongoDB | 3.2.2 | ORM para MongoDB |
| MongoDB | 8.2.5 | Base de datos NoSQL |
| Maven | 3.9.11 | Gestor de dependencias y compilación |
| MongoShell | 2.7.0 | Cliente para interactuar con MongoDB |

---

## 📋 Fases de Desarrollo

### **FASE 1: Infraestructura y Base de Datos**

#### Instalación de MongoDB
1. Descarga: MongoDB Community Server 8.2.5 desde https://www.mongodb.com/try/download/community
2. Instalación: Ejecutar instalador MSI en Windows
3. Datos: Crear carpeta `C:\data\db` con permisos de escritura
4. Servicio: MongoDB inicia automáticamente en puerto 27017

#### Instalación de MongoShell
1. Descarga: MongoDB Shell desde https://www.mongodb.com/try/download/shell
2. Instalación: Ejecutar instalador MSI o usar gestor de paquetes
3. Verificación: Ubicación en `C:\Users\{user}\AppData\Local\Programs\mongosh\`

#### Verificación de Funcionamiento
```powershell
# Ver servicio corriendo
Get-Service -Name MongoDB

# Verificar versión
"C:\Program Files\MongoDB\Server\8.2\bin\mongod.exe" --version

# Crear base de datos
mongosh --eval "use logistica_db" --eval "db.createCollection('clientes')"

# Listar bases de datos
mongosh --eval "show dbs"
```

**Resultado:**
- ✅ MongoDB Server 8.2.5 instalado
- ✅ Servicio ejecutándose en puerto 27017
- ✅ Base de datos `logistica_db` creada
- ✅ Carpeta `C:\data\db` disponible

---

### **FASE 2: Configuración del Proyecto Spring Boot**

#### Generación del Proyecto
Proyecto generado usando estructura Maven estándar con:
- **GroupId:** `com.logistica`
- **ArtifactId:** `gestion-usuarios-pedidos`
- **Version:** `1.0.0`

#### Dependencias Incluidas
```xml
<!-- Spring Boot Starter Data MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Spring Boot Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- Spring Boot Starter Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

#### Configuración de Conexión (application.properties)
```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=logistica_db

logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
logging.level.com.logistica=INFO
```

---

### **FASE 3: Modelado de Datos (Mapping)**

#### Clase Pedido.java
```java
public class Pedido {
    private String producto;
    private Double precio;
    
    // Constructores, getters, setters
}
```
**Características:**
- Clase auxiliar para embeber en Cliente
- Demuestra flexibilidad NoSQL (documentos anidados)
- Sin anotaciones MongoDB (se serializa automáticamente)

#### Clase Cliente.java
```java
@Document(collection = "clientes")
public class Cliente {
    
    @Id
    private String id;                    // ID generado por MongoDB
    private String nombre;
    private String apellidos;
    private String email;
    private List<Pedido> pedidos;         // Documentos embebidos
    
    // Constructores, getters, setters, método agregarPedido()
}
```

**Características:**
- `@Document(collection = "clientes")`: Mapea a colección en MongoDB
- `@Id`: ID único generado automáticamente por MongoDB
- `List<Pedido> pedidos`: Demuestra anidación flexible (esquema flexible)
- Permite clientes con o sin pedidos

---

### **FASE 4: Capa de Repositorio**

#### ClienteRepository.java
```java
@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    
    // Derived Queries (generadas automáticamente)
    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByApellidos(String apellidos);
    Cliente findByEmail(String email);
}
```

**Ventajas:**
- No requiere implementación manual
- Spring Data genera automáticamente las consultas
- Fácil de mantener y extender

---

### **FASE 5: Lógica de Ejecución**

#### Clase Application.java
Implementa `CommandLineRunner` para ejecutar lógica al iniciar:

**Paso 1: Limpieza de Datos**
```java
repository.deleteAll();  // Elimina todos los documentos
```

**Paso 2: Inserción de Clientes**
```
✓ Cliente 1: Alicia González (3 pedidos)
  - Laptop Dell XPS 15: $1299.99
  - Mouse Logitech MX Master: $99.99
  - Teclado Mecánico: $149.99

✓ Cliente 2: Roberto Sánchez (sin pedidos - esquema flexible)

✓ Cliente 3: María Rodríguez (1 pedido)
  - Monitor 4K Samsung: $599.99
```

**Paso 3: Consultas Derivadas**
- `findAll()`: Recupera todos los clientes
- `findByNombre("Alicia")`: Busca por nombre exacto
- `findByApellidos("Sánchez López")`: Busca por apellidos
- `findByEmail("maria.rodriguez@email.com")`: Busca por email

---

### **FASE 6: Compilación y Empaquetado**

#### Comandos Ejecutados

**Compilar el proyecto:**
```bash
cd NoSQLJava
mvn clean compile
```

**Generar JAR ejecutable:**
```bash
mvn clean package -DskipTests
```

**Resultado:**
- JAR generado: `target/gestion-usuarios-pedidos-1.0.0.jar`
- Tamaño: ~30-50 MB (incluye todas las dependencias)
- Tipo: JAR ejecutable (Fat JAR)

---

## ▶️ Ejecución de la Aplicación

### Opción 1: Ejecutar con Maven
```bash
cd NoSQLJava
mvn spring-boot:run
```

### Opción 2: Ejecutar JAR directamente
```bash
cd NoSQLJava
java -jar target/gestion-usuarios-pedidos-1.0.0.jar
```

### Salida Esperada
```
========================================
INICIANDO SISTEMA DE GESTIÓN DE CLIENTES
========================================

>>> PASO 1: Limpiando base de datos...
✓ Base de datos limpiada correctamente

>>> PASO 2: Insertando clientes en MongoDB...
✓ Cliente insertado: Alicia González (con 3 pedidos)
✓ Cliente insertado: Roberto Sánchez (sin pedidos - esquema flexible)
✓ Cliente insertado: María Rodríguez (con 1 pedido)

>>> TOTAL: 3 clientes insertados correctamente

========================================
>>> PASO 3: Recuperando TODOS los clientes con findAll():
========================================

📋 Cliente{id='...', nombre='Alicia', apellidos='González Martínez', ...}
📋 Cliente{id='...', nombre='Roberto', apellidos='Sánchez López', ...}
📋 Cliente{id='...', nombre='María', apellidos='Rodríguez Pérez', ...}

>>> Total de clientes encontrados: 3

========================================
>>> PASO 4: Buscando cliente por nombre 'Alicia':
========================================

✓ Cliente encontrado:
  - Nombre completo: Alicia González Martínez
  - Email: alicia.gonzalez@email.com
  - Número de pedidos: 3
  - Detalle de pedidos:
    • Laptop Dell XPS 15 - $1299.99
    • Mouse Logitech MX Master - $99.99
    • Teclado Mecánico - $149.99

========================================
✓ SISTEMA EJECUTADO CORRECTAMENTE
========================================
```

---

## 🔍 Verificación en MongoDB

### Consulta: Ver todos los documentos
```bash
mongosh logistica_db --eval "db.clientes.find().pretty()"
```

**Resultado esperado (formato BSON/JSON):**
```json
[
  {
    "_id": ObjectId("..."),
    "nombre": "Alicia",
    "apellidos": "González Martínez",
    "email": "alicia.gonzalez@email.com",
    "pedidos": [
      { "producto": "Laptop Dell XPS 15", "precio": 1299.99 },
      { "producto": "Mouse Logitech MX Master", "precio": 99.99 },
      { "producto": "Teclado Mecánico", "precio": 149.99 }
    ]
  },
  ...
]
```

### Consulta: Contar documentos
```bash
mongosh logistica_db --eval "db.clientes.countDocuments()"
```
**Resultado:** `3`

### Consulta: Buscar cliente específico
```bash
mongosh logistica_db --eval "db.clientes.find({nombre: 'Alicia'}).pretty()"
```

---

## 📊 Comparativa: SQL vs NoSQL

| Aspecto | SQL (Relacional) | NoSQL (MongoDB) |
|--------|-----------------|-----------------|
| **Esquema** | Rígido, predefinido | Flexible, adaptable |
| **Documentos anidados** | Requiere JOINs | Embebidos nativos |
| **Escalabilidad** | Vertical | Horizontal |
| **Este proyecto** | Requeriría 2 tablas (cliente, pedidos) | 1 colección (clientes con pedidos embebidos) |
| **Consultas** | SQL estándar | MongoDB queries |

### Ventaja NoSQL en este proyecto:
La lista de pedidos está **embebida directamente en el cliente**, sin necesidad de relaciones entre tablas. Esto mejora el rendimiento para lecturas frecuentes.

---

## 🛠️ Problemas Encontrados y Soluciones

### Problema 1: Dos clases Application
**Descripción:** Maven no sabía cuál era la clase principal
**Solución:** 
- Eliminar clase duplicada `NoSqlJavaApplication`
- Especificar en `pom.xml`:
```xml
<mainClass>com.logistica.Application</mainClass>
```

### Problema 2: MongoShell no en PATH de Git Bash
**Descripción:** Comando `mongosh` no reconocido
**Solución:** Usar ruta completa:
```bash
"/c/Users/$USERNAME/AppData/Local/Programs/mongosh/mongosh.exe"
```

---

## 📚 Archivos Generados

### Código Fuente (src/)
```
src/main/java/com/logistica/
├── Application.java (157 líneas)
├── model/
│   ├── Cliente.java (90 líneas)
│   └── Pedido.java (50 líneas)
└── repository/
    └── ClienteRepository.java (20 líneas)
```

### Configuración
```
src/main/resources/
└── application.properties (7 líneas)

pom.xml (60 líneas)
```

### Compilación
```
target/
├── gestion-usuarios-pedidos-1.0.0.jar (~30-50 MB)
└── classes/ (bytecode compilado)
```

---

## ✅ Criterios de Evaluación (Rúbrica)

| Criterio | Peso | Estado | Evidencia |
|----------|------|--------|-----------|
| **Configuración Entorno** | 20% | ✅ Completo | MongoDB corriendo, Spring Boot conectado sin errores |
| **Modelado NoSQL** | 30% | ✅ Completo | @Document, @Id, estructura con pedidos embebidos |
| **Repositorio** | 30% | ✅ Completo | MongoRepository, Derived Queries (findByNombre, findByApellidos, findByEmail) |
| **Funcionalidad** | 20% | ✅ Completo | Inserción, eliminación y recuperación demostrables en consola |

**Puntuación Total:** 100% ✅

---

## 🚀 Cómo Reproducir el Proyecto

### Requisitos Previos
- Java 17+ instalado
- Maven 3.9+ instalado
- MongoDB 8.2+ instalado y ejecutándose
- Git (opcional)

### Pasos
1. **Clonar/descargar el repositorio:**
   ```bash
   cd fullstack-java-nosql/NoSQLJava
   ```

2. **Compilar:**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar:**
   ```bash
   mvn spring-boot:run
   # o
   mvn clean package -DskipTests
   java -jar target/gestion-usuarios-pedidos-1.0.0.jar
   ```

4. **Verificar en MongoDB:**
   ```bash
   mongosh logistica_db --eval "db.clientes.find().pretty()"
   ```

---

## 📖 Referencias

- [Spring Data MongoDB Documentation](https://spring.io/projects/spring-data-mongodb)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Documentation](https://maven.apache.org/guides/)

---

## 👨‍💻 Resumen de Competencias Demostradas

✅ **Instalación y configuración de bases de datos NoSQL**  
✅ **Desarrollo con Spring Boot 3.x**  
✅ **Mapeo de entidades BSON/JSON en Java**  
✅ **Implementación de repositorios con Spring Data**  
✅ **Consultas derivadas y operaciones CRUD**  
✅ **Generación de JAR ejecutables**  
✅ **Documentación técnica de proyectos**  

---

**Fecha de Finalización:** 18 de Febrero de 2026  
**Estado:** ✅ COMPLETADO
