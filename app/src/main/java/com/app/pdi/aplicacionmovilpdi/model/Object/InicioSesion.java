package com.app.pdi.aplicacionmovilpdi.model.Object;


public class InicioSesion {

    private int codigo;
    private boolean estado;
    private String mensaje;
    private String nombre;
    private String apellido;
    private String email;
    private String authKey;

    /**
     * No args constructor for use in serialization
     *
     */
    public InicioSesion() {
    }

    /**
     *
     * @param apellido
     * @param nombre
     * @param codigo
     * @param estado
     * @param authKey
     * @param mensaje
     */
    public InicioSesion(int codigo, boolean estado, String mensaje, String nombre, String apellido, String email, String authKey) {
        super();
        this.codigo = codigo;
        this.estado = estado;
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.authKey = authKey;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

}