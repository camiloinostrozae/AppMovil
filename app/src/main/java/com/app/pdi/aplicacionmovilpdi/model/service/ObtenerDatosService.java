package com.app.pdi.aplicacionmovilpdi.model.service;

import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;
import com.app.pdi.aplicacionmovilpdi.model.Object.Comuna;
import com.app.pdi.aplicacionmovilpdi.model.Object.Region;
import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseInteraccion;
import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ObtenerDatosService {

    //llamada para obtener todas las regiones.
    @GET("get-regiones")
    Call<List<Region>>obtenerRegion ();

    //llamada para obtener las comunas en base al id de la region
    @GET("get-comuna/{region_id_region}")
    Call<List<Comuna>>obtenerComuna(@Path("region_id_region") int regionIdRegion);

    //llamada para listar las campañas educativas
    @GET("listar-campanas")
    Call<List<Campana>>obtenerCampanas();

    //llamada para listar todos los tramites
    @GET("listar-tramites")
    Call<List<Tramite>>obtenerTramites();

    //llamada para registrar la interacción con una campaña
    @FormUrlEncoded
    @POST("registrar-interaccion-campana")
    Call<ResponseInteraccion>registrarInteraccionCampana(@Field("auth_key")String auth_key,
                                                  @Field("campana_id_campana") int campana_id_campana);

    //llamada para registrar la interacción con un tramite
    @FormUrlEncoded
    @POST("registrar-interaccion-tramite")
    Call<ResponseInteraccion>registrarInteraccionTramite(@Field("auth_key") String auth_key,
                                                  @Field("tramite_id_tramite") int tramite_id_tramite);


    //llamada para registrar una llamada
    @FormUrlEncoded
    @POST("guardar-llamada")
    Call<ResponseInteraccion>guardarLlamada(@Field("auth_key") String auth_key,
                                        @Field("latitud") double latitud,
                                        @Field("longitud") double longitud);
}
