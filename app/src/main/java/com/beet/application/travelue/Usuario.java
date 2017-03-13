package com.beet.application.travelue;

/**
 * Created by Adri on 16/01/2017.
 */

public class Usuario {
    String nombre, apellido, email, residencia, nacionalidad, idiomas, urlFoto;

    public Usuario() {

    }

    public Usuario(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

    }

    public Usuario(String nombre, String apellido, String email, String residencia, String nacionalidad, String idiomas, String urlFoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.residencia = residencia;
        this.nacionalidad = nacionalidad;
        this.idiomas = idiomas;
        this.urlFoto = urlFoto;
    }

    public Usuario(String residencia, String nacionalidad, String idiomas, String urlFoto) {
        this.residencia = residencia;
        this.nacionalidad = nacionalidad;
        this.idiomas = idiomas;
        this.urlFoto = urlFoto;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getIdiomas() {
        return idiomas;
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

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
