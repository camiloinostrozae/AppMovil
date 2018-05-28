package com.app.pdi.aplicacionmovilpdi.model.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInteraccion {

    @SerializedName("codigo")
    @Expose
    private int codigo;
    @SerializedName("estado")
    @Expose()
    private boolean estado;

    public ResponseInteraccion(){

    }

    public ResponseInteraccion(int codigo, boolean estado){
        super();
        this.codigo = codigo;
        this.estado = estado;

    }

    public int getCodigo(){
        return codigo;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public boolean isEstado(){
        return estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }
}
