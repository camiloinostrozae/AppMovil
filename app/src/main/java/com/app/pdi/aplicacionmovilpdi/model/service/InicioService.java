package com.app.pdi.aplicacionmovilpdi.model.service;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InicioService {

    @GET("login/{email}/{contrasena}")
    Call<InicioSesion> login(@Path("email")String email, @Path("contrasena")String contrasena);
}
