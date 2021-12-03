package com.learn2crack.swipeview.Modelo;

public class Material {

    private String ID;

    public Material(String ID,String nombre, String informacion, String imagen) {
        this.nombre = nombre;
        this.informacion = informacion;
        this.imagen = imagen;
        this.ID = ID;
    }

    private String nombre;

    public String getId() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    private String informacion;
    private String imagen;
}
