package com.app.pdi.aplicacionmovilpdi.model.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Persona {

    @SerializedName("id_persona")
    @Expose
    private int idPersona;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("rut")
    @Expose
    private String rut;
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("sexo")
    @Expose
    private String sexo;
    @SerializedName("contrasena")
    @Expose
    private String contrasena;
    @SerializedName("auth_key")
    @Expose
    private String authKey;
    @SerializedName("comuna_id_comuna")
    @Expose
    private int comunaIdComuna;
    @SerializedName("rol_id_rol")
    @Expose
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

    public Persona(String nombre, String apellido,  String telefono,  String email,String contrasena, String fechaNacimiento, String sexo){
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.sexo = sexo;
        this.contrasena = contrasena;


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