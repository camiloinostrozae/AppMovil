package com.app.pdi.aplicacionmovilpdi.model.Object;

public class Persona {

    private int idPersona;
    private String nombre;
    private String apellido;
    private String rut;
    private String fechaNacimiento;
    private String email;
    private String telefono;
    private String sexo;
    private String contrasena;
    private String authKey;
    private int comunaIdComuna;
    private int rolIdRol;

    /**
     * No args constructor for use in serialization
     *
     */
    public Persona() {
    }

    /**
     *
     * @param apellido
     * @param nombre
     * @param comunaIdComuna
     * @param authKey
     * @param rolIdRol
     * @param email
     * @param rut
     * @param telefono
     * @param sexo
     * @param contrasena
     * @param idPersona
     * @param fechaNacimiento
     */
    public Persona(int idPersona, String nombre, String apellido, String rut, String fechaNacimiento, String email, String telefono, String sexo, String contrasena, String authKey, int comunaIdComuna, int rolIdRol) {
        super();
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.sexo = sexo;
        this.contrasena = contrasena;
        this.authKey = authKey;
        this.comunaIdComuna = comunaIdComuna;
        this.rolIdRol = rolIdRol;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
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

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public int getComunaIdComuna() {
        return comunaIdComuna;
    }

    public void setComunaIdComuna(int comunaIdComuna) {
        this.comunaIdComuna = comunaIdComuna;
    }

    public int getRolIdRol() {
        return rolIdRol;
    }

    public void setRolIdRol(int rolIdRol) {
        this.rolIdRol = rolIdRol;
    }

}