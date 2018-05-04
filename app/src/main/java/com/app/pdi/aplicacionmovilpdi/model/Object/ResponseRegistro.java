package com.app.pdi.aplicacionmovilpdi.model.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegistro {

    @SerializedName("codigo")
    @Expose
    private int codigo;
    @SerializedName("estado")
    @Expose
    private boolean estado;

    @Expose
    private Persona persona;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseRegistro() {
    }

    /**
     *
     * @param codigo
     * @param estado

     */
    public ResponseRegistro(int codigo, boolean estado, Persona persona) {
        super();
        this.codigo = codigo;
        this.estado = estado;

        this.persona = persona;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }



    public Persona getPersona(){ return persona; }
}
