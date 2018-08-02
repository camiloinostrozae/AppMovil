package com.app.pdi.aplicacionmovilpdi.model.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comuna {

    @SerializedName("id_comuna")
    @Expose
    private int idComuna;
    @SerializedName("nombreComuna")
    @Expose
    private String nombre;
    @SerializedName("region_id_region")
    @Expose
    private int regionIdRegion;

    /**
     * No args constructor for use in serialization
     *
     */
    public Comuna() {
    }

    /**
     *
     * @param nombre
     * @param regionIdRegion
     * @param idComuna
     */
    public Comuna(int idComuna, String nombre, int regionIdRegion) {
        super();
        this.idComuna = idComuna;
        this.nombre = nombre;
        this.regionIdRegion = regionIdRegion;
    }

    public Comuna(int idComuna, String nombre){
        this.idComuna = idComuna;
        this.nombre = nombre;
    }

    public int getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(int idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRegionIdRegion() {
        return regionIdRegion;
    }

    public void setRegionIdRegion(int regionIdRegion) {
        this.regionIdRegion = regionIdRegion;
    }

}