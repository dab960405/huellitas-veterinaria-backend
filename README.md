# 🐾 Clínica Veterinaria Huellitas — Backend API REST

API REST desarrollada con **Java + Spring Boot** como parte del Test Práctico de Certificación del programa Técnico Laboral en Desarrollo de Software (CESDE / AHK Colombia).

---

## 🗂️ Tabla de contenidos

- [Descripción](#descripción)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Arquitectura del proyecto](#arquitectura-del-proyecto)
- [Estructura de carpetas](#estructura-de-carpetas)
- [Requisitos previos](#requisitos-previos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Base de datos](#base-de-datos)
- [Endpoints de la API](#endpoints-de-la-api)
- [Validaciones implementadas](#validaciones-implementadas)
- [Ejemplos de peticiones](#ejemplos-de-peticiones)

---

## Descripción

El backend de Huellitas expone una **API REST** que gestiona la información clínica de la veterinaria: dueños, mascotas y citas. La arquitectura sigue el patrón **Cliente-Servidor desacoplado**, donde este backend provee datos en formato JSON para ser consumidos por cualquier cliente web o móvil.

---

## Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 4.0.3 | Framework principal |
| Spring Data JPA | — | Persistencia y acceso a datos |
| Spring Validation | — | Validaciones de entrada (`@NotBlank`, `@NotNull`) |
| H2 Database | — | Base de datos en memoria (desarrollo/evaluación) |
| MySQL Connector | — | Base de datos relacional (producción) |
| Maven | 3.9.12 | Gestión de dependencias y build |

---

## Arquitectura del proyecto

El proyecto sigue una arquitectura en capas con separación clara de responsabilidades:

```
HTTP Request
     │
     ▼
┌─────────────┐
│  Controller │  ← Recibe peticiones HTTP, retorna JSON
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │  ← Lógica de negocio, validaciones, conversión DTO
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Repository  │  ← Acceso a datos (Spring Data JPA)
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Model    │  ← Entidades JPA mapeadas a tablas
└─────────────┘
```

**Patrón DTO:** Cada entidad tiene un DTO de salida (para respuestas JSON) y un RequestDTO de entrada (para recibir datos del cliente). Esto desacopla la entidad JPA de la API y evita problemas de serialización con relaciones lazy.

---

## Estructura de carpetas

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/huellitas/backend/
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.java          # Permite peticiones desde el frontend
│   │   │   │   └── DataLoader.java          # Mensaje de inicio en consola
│   │   │   ├── controller/
│   │   │   │   ├── CitaController.java      # Endpoints /api/citas
│   │   │   │   ├── DuenoController.java     # Endpoints /api/duenos
│   │   │   │   ├── HealthController.java    # Endpoint /api/health
│   │   │   │   └── MascotaController.java   # Endpoints /api/mascotas
│   │   │   ├── dto/
│   │   │   │   ├── CitaDTO.java
│   │   │   │   ├── CitaRequestDTO.java
│   │   │   │   ├── DuenoDTO.java
│   │   │   │   ├── DuenoRequestDTO.java
│   │   │   │   ├── MascotaDTO.java
│   │   │   │   └── MascotaRequestDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java   # Manejo global de errores
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── model/
│   │   │   │   ├── Cita.java
│   │   │   │   ├── Dueno.java
│   │   │   │   └── Mascota.java
│   │   │   ├── repository/
│   │   │   │   ├── CitaRepository.java
│   │   │   │   ├── DuenoRepository.java
│   │   │   │   └── MascotaRepository.java
│   │   │   ├── service/
│   │   │   │   ├── CitaService.java
│   │   │   │   ├── DuenoService.java
│   │   │   │   └── MascotaService.java
│   │   │   └── BackendApplication.java
│   │   └── resources/
│   │       └── application.properties       # Configuración del servidor y BD
│   └── test/
├── pom.xml
└── README.md
```

---

## Requisitos previos

- **Java 17** o superior instalado
- **Maven 3.6+** (o usar el wrapper `./mvnw` incluido en el proyecto)
- No requiere instalar ninguna base de datos para ejecutar en modo desarrollo (usa H2 en memoria)

Verificar instalación:
```bash
java -version
mvn -version
```

---

## Instalación y ejecución

### 1. Clonar o descomprimir el proyecto

```bash
# Si se clona desde Git:
git clone <url-del-repositorio>
cd backend

# Si se descomprime desde ZIP:
cd CodigoFuente/Backend
```

### 2. Ejecutar el proyecto

**En Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**En Windows:**
```bash
mvnw.cmd spring-boot:run
```

**O con Maven instalado globalmente:**
```bash
mvn spring-boot:run
```

### 3. Verificar que el servidor está activo

Abrir en el navegador o en Postman:
```
GET http://localhost:8080/api/health
```
Respuesta esperada:
```
Backend Huellitas funcionando correctamente
```

### 4. Acceder a la consola H2 (base de datos)

```
http://localhost:8080/h2-console
```

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:huellitas` |
| User Name | `sa` |
| Password | *(dejar vacío)* |

---

## Base de datos

El proyecto usa **H2 en memoria** por defecto para facilitar la evaluación sin instalaciones adicionales. Las tablas se crean automáticamente al iniciar el servidor mediante Hibernate (`ddl-auto=update`).

### Modelo relacional

```
duenos (1) ──────── (N) mascotas (1) ──────── (N) citas
   │                        │                      │
id_dueno (PK)          id_mascota (PK)         id_cita (PK)
nombre                 nombre                  fecha
apellido               especie                 hora
documento (UNIQUE)     raza                    motivo
telefono               fecha_nacimiento        estado
email                  id_dueno (FK)           id_mascota (FK)
direccion
```

Las relaciones están implementadas con **llaves foráneas (Foreign Keys)** y el modelo cumple con la **Tercera Forma Normal (3FN)**: no hay datos redundantes, cada tabla tiene una responsabilidad única y todos los campos dependen únicamente de la llave primaria.

### Configuración para MySQL (producción)

Para conectar con MySQL en lugar de H2, modificar `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/huellitas_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

## Endpoints de la API

Todos los endpoints retornan JSON con la estructura:
```json
{ "success": true, "data": { ... } }
```
En caso de error:
```json
{ "success": false, "error": "Descripción del error" }
```

### 🧑 Dueños — `/api/duenos`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/duenos` | Obtener todos los dueños |
| GET | `/api/duenos/{id}` | Obtener un dueño por ID |
| POST | `/api/duenos` | Crear un nuevo dueño |
| PUT | `/api/duenos/{id}` | Actualizar un dueño |
| DELETE | `/api/duenos/{id}` | Eliminar un dueño |
| GET | `/api/duenos/buscar?termino=xxx` | Buscar por nombre, apellido o documento |

### 🐶 Mascotas — `/api/mascotas`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/mascotas` | Obtener todas las mascotas |
| GET | `/api/mascotas/{id}` | Obtener una mascota por ID |
| POST | `/api/mascotas` | Crear una nueva mascota |
| PUT | `/api/mascotas/{id}` | Actualizar una mascota |
| DELETE | `/api/mascotas/{id}` | Eliminar una mascota |
| GET | `/api/mascotas/buscar?termino=xxx` | Buscar por nombre o documento del dueño |
| GET | `/api/mascotas/dueno/{idDueno}` | Obtener mascotas de un dueño específico |

### 📅 Citas — `/api/citas`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/citas` | Obtener todas las citas |
| GET | `/api/citas/{id}` | Obtener una cita por ID |
| POST | `/api/citas` | Crear una nueva cita |
| PUT | `/api/citas/{id}` | Actualizar una cita |
| DELETE | `/api/citas/{id}` | Eliminar una cita |
| GET | `/api/citas/mascota/{idMascota}` | Obtener citas de una mascota específica |

### 🔁 Health Check

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/health` | Verificar que el servidor está activo |

---

## Validaciones implementadas

| Regla | Ubicación | Descripción |
|---|---|---|
| Campos obligatorios | `*RequestDTO.java` | `@NotBlank` y `@NotNull` en todos los campos requeridos |
| No citas en fechas pasadas | `CitaService.java` | Valida `fecha.isBefore(LocalDate.now())` al crear |
| Actualización inteligente de fecha | `CitaService.java` | Solo valida fecha pasada si la fecha realmente cambió |
| Documento único por dueño | `Dueno.java` | `@Column(unique = true)` en el campo documento |
| Edad calculada automáticamente | `Mascota.java` | Método `getEdad()` con `@Transient` usando `Period.between()` |
| Errores uniformes en JSON | `GlobalExceptionHandler.java` | Captura excepciones globalmente y retorna JSON estándar |

---

## Ejemplos de peticiones

### Crear un dueño
```http
POST /api/duenos
Content-Type: application/json

{
  "nombre": "Carlos",
  "apellido": "Ramírez",
  "documento": "1234567890",
  "telefono": "3001234567",
  "email": "carlos@email.com",
  "direccion": "Calle 10 # 20-30"
}
```

### Crear una mascota
```http
POST /api/mascotas
Content-Type: application/json

{
  "nombre": "Firulais",
  "especie": "Perro",
  "raza": "Labrador",
  "fechaNacimiento": "2021-03-15",
  "idDueno": 1
}
```

### Crear una cita
```http
POST /api/citas
Content-Type: application/json

{
  "fecha": "2026-03-10",
  "hora": "10:30:00",
  "motivo": "Vacunación anual",
  "idMascota": 1
}
```

### Buscar mascotas
```http
GET /api/mascotas/buscar?termino=Firulais
GET /api/mascotas/buscar?termino=1234567890
```

---

## Autor

Desarrollado como Test Práctico Final de Certificación — AHK Colombia / CESDE  
Contacto evaluador: andres.valencia@ahk-colombia.com
