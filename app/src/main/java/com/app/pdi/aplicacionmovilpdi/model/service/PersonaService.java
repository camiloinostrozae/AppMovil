package com.app.pdi.aplicacionmovilpdi.model.service;

import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseRegistro;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PersonaService {
/*
    @GET("/login/{email}/{contrasena}")
    Call<Persona> login(@Path("email")String email, @Path("contrasena")String contrasena); */

    @FormUrlEncoded
    @POST("create-persona")
    Call<ResponseRegistro>registrarUsuario(@Field("nombre") String nombre,
                                           @Field("apellido") String apellido,
                                           @Field("telefono") String telefono,
                                           @Field("email") String email,
                                           @Field("contrasena") String contrasena,
                                           @Field("fecha_nacimiento") String fechaNacimiento,
                                           @Field("sexo") String sexo);

}
