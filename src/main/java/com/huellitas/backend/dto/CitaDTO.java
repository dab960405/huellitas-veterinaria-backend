// dto/CitaDTO.java
package com.huellitas.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para transferir datos de Cita al frontend.
 * Incluye información de la mascota y su dueño.
 */
public class CitaDTO {

    private Long idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;
    private Long idMascota;
    private String nombreMascota;
    private String nombreDueno;

    // ===== CONSTRUCTORES =====

    public CitaDTO() {}

    public CitaDTO(Long idCita, LocalDate fecha, LocalTime hora, String motivo,
                   String estado, Long idMascota, String nombreMascota, String nombreDueno) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.nombreDueno = nombreDueno;
    }

    // ===== GETTERS Y SETTERS =====

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Long idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }
}