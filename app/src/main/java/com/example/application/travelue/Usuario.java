package com.example.application.travelue;

/**
 * Created by Adri on 16/01/2017.
 */

public class Usuario {
    String nombre, apellido, email, residencia, nacionalidad, idiomas;

    public Usuario() {

    }

    public Usuario(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }
}
