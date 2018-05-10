package com.app.pdi.aplicacionmovilpdi.model.service;

import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;
import com.app.pdi.aplicacionmovilpdi.model.Object.Comuna;
import com.app.pdi.aplicacionmovilpdi.model.Object.Region;
import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ObtenerDatosService {

    @GET("get-regiones")
    Call<List<Region>>obtenerRegion ();

    @GET("get-comuna/{region_id_region}")
    Call<List<Comuna>>obtenerComuna(@Path("region_id_region") int regionIdRegion);

    @GET("listar-campanas")
    Call<List<Campana>>obtenerCampanas();

    @GET("listar-tramites")
    Call<List<Tramite>>obtenerTramites();
}
