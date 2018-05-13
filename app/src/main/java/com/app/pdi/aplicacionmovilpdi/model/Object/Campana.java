package com.app.pdi.aplicacionmovilpdi.model.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campana {

    @SerializedName("id_campana")
    @Expose
    private int idCampana;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("contenido")
    @Expose
    private String contenido;
    @SerializedName("fecha_publicacion")
    @Expose
    private String fechaPublicacion;
    @SerializedName("fecha_vencimiento")
    @Expose
    private String fechaVencimiento;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("id_tipo_campana")
    @Expose
    private int idTipoCampana;
    @SerializedName("tipo")
    @Expose
    private String tipo;

    /**
     * No args constructor for use in serialization
     *
     */
    public Campana() {
    }

    /**
     *
     * @param fechaPublicacion
     * @param titulo
     * @param estado
     * @param idTipoCampana
     * @param fechaVencimiento
     * @param contenido
     * @param idCampana
     */
    public Campana(int idCampana, String titulo, String contenido, String fechaPublicacion, String fechaVencimiento, String estado, int idTipoCampana,String tipo) {
        super();
        this.idCampana = idCampana;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.idTipoCampana = idTipoCampana;
        this.tipo = tipo;
    }

    public int getIdCampana() {
        return idCampana;
    }

    public void setIdCampana(int idCampana) {
        this.idCampana = idCampana;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdTipoCampana() {
        return idTipoCampana;
    }

    public void setIdTipoCampana(int idTipoCampana) {
        this.idTipoCampana = idTipoCampana;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
