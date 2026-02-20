package com.huellitas.backend.dto;

import java.time.LocalDate;

/**
 * DTO para transferir datos de Mascota al frontend.
 * Incluye información del dueño y la edad calculada.
 */
public class MascotaDTO {

    private Long idMascota;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private String edad;
    private Long idDueno;
    private String nombreDueno;
    private String documentoDueno;

    // ===== CONSTRUCTORES =====

    public MascotaDTO() {}

    public MascotaDTO(Long idMascota, String nombre, String especie, String raza,
                      LocalDate fechaNacimiento, String edad, Long idDueno,
                      String nombreDueno, String documentoDueno) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.idDueno = idDueno;
        this.nombreDueno = nombreDueno;
        this.documentoDueno = documentoDueno;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Long getIdDueno() {
        return idDueno;
    }

    public void setIdDueno(Long idDueno) {
        this.idDueno = idDueno;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }

    public String getDocumentoDueno() {
        return documentoDueno;
    }

    public void setDocumentoDueno(String documentoDueno) {
        this.documentoDueno = documentoDueno;
    }
}