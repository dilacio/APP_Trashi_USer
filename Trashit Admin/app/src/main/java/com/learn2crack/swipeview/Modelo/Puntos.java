package com.learn2crack.swipeview.Modelo;

public class Puntos {
    private int Id;
    private Double Latitud;
    private Double Longitud;
    private String Direccion;
    private Integer IdBarrio;
    private String Barrio;
    private Integer IdMaterial;
    private String Material;
    public Puntos() {
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public Integer getIdBarrio() {
        return IdBarrio;
    }

    public void setIdBarrio(Integer idBarrio) {
        IdBarrio = idBarrio;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String barrio) {
        Barrio = barrio;
    }

    public Integer getIdMaterial() {
        return IdMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        IdMaterial = idMaterial;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
