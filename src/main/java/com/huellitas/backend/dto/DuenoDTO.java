// dto/DuenoDTO.java
package com.huellitas.backend.dto;

import java.time.LocalDateTime;

/**
 * DTO para transferir datos de Dueño al frontend.
 * Evita exponer la entidad JPA directamente y
 * previene errores de lazy loading con la lista de mascotas.
 */
public class DuenoDTO {

    private Long idDueno;
    private String nombre;
    private String apellido;
    private String documento;
    private String telefono;
    private String email;
    private String direccion;
    private int totalMascotas;

    // ===== CONSTRUCTORES =====

    public DuenoDTO() {}

    public DuenoDTO(Long idDueno, String nombre, String apellido, String documento,
                    String telefono, String email, String direccion, int totalMascotas) {
        this.idDueno = idDueno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.totalMascotas = totalMascotas;
    }

    // ===== GETTERS Y SETTERS =====

    public Long getIdDueno() { return idDueno; }
    public void setIdDueno(Long idDueno) { this.idDueno = idDueno; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getTotalMascotas() { return totalMascotas; }
    public void setTotalMascotas(int totalMascotas) { this.totalMascotas = totalMascotas; }
}