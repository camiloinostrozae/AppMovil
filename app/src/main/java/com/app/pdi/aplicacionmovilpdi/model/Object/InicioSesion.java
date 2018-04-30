package com.app.pdi.aplicacionmovilpdi.model.Object;


public class InicioSesion {

    private boolean mensaje;

    /**
     * No args constructor for use in serialization
     *
     */
    public InicioSesion() {
    }

    /**
     *
     * @param mensaje
     */
    public InicioSesion(boolean mensaje) {
        super();
        this.mensaje = mensaje;
    }

    public boolean isMensaje() {
        return mensaje;
    }

    public void setMensaje(boolean mensaje) {
        this.mensaje = mensaje;
    }

}