// model/Mascota.java
package com.huellitas.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

/**
 * Entidad que representa una mascota/paciente.
 * Tiene relación N:1 con Dueño y 1:N con Citas.
 */
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long idMascota;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 30)
    @Column(name = "especie", nullable = false)
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    @Size(max = 50)
    @Column(name = "raza", nullable = false)
    private String raza;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    /** Relación N:1 con Dueño */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dueno", nullable = false)
    @JsonBackReference
    private Dueno dueno;

    /** Relación 1:N con Citas */
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cita> citas;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== CONSTRUCTORES =====

    public Mascota() {}

    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento, Dueno dueno) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.dueno = dueno;
    }

    // ===== MÉTODO CALCULADO: EDAD =====

    /**
     * Calcula la edad de la mascota automáticamente
     * basándose en la fecha de nacimiento.
     * @return String con la edad formateada (ej: "3 años, 5 meses")
     */
    @Transient
    public String getEdad() {
        if (fechaNacimiento == null) return "Desconocida";
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());
        int years = periodo.getYears();
        int months = periodo.getMonths();

        if (years == 0) {
            return months + " mes(es)";
        }
        return years + " año(s), " + months + " mes(es)";
    }

    // ===== GETTERS Y SETTERS =====

    public Long getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Long idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}