// model/Dueno.java
package com.huellitas.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa a un dueño/propietario de mascotas.
 * Mapea la tabla 'duenos' de la base de datos.
 */
@Entity
@Table(name = "duenos")
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dueno")
    private Long idDueno;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50)
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20)
    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20)
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Size(max = 200)
    @Column(name = "direccion")
    private String direccion;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** Relación 1:N con mascotas */
    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Mascota> mascotas;

    // ===== CONSTRUCTORES =====

    public Dueno() {}

    public Dueno(String nombre, String apellido, String documento, String telefono, String email, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    // ===== GETTERS Y SETTERS =====

    public Long getIdDueno() {
        return idDueno;
    }

    public void setIdDueno(Long idDueno) {
        this.idDueno = idDueno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}