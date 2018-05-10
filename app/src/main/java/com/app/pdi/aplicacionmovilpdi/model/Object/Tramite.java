package com.app.pdi.aplicacionmovilpdi.model.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tramite {
    @SerializedName("id_tramite")
    @Expose
    private int idTramite;
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
    @SerializedName("id_tipo_tramite")
    @Expose
    private int idTipoTramite;

    /**
     * No args constructor for use in serialization
     *
     */
    public Tramite() {
    }

    /**
     *
     * @param idTipoTramite
     * @param fechaPublicacion
     * @param titulo
     * @param estado
     * @param fechaVencimiento
     * @param contenido
     * @param idTramite
     */
    public Tramite(int idTramite, String titulo, String contenido, String fechaPublicacion, String fechaVencimiento, String estado, int idTipoTramite) {
        super();
        this.idTramite = idTramite;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.idTipoTramite = idTipoTramite;
    }

    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
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

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

}
