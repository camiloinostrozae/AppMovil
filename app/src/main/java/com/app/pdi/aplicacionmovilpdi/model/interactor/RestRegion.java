package com.app.pdi.aplicacionmovilpdi.model.interactor;

import com.app.pdi.aplicacionmovilpdi.model.service.ObtenerDatosService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestRegion {

    private static ObtenerDatosService servicio = null;

    public static ObtenerDatosService getRegion(){
        final Retrofit retrofit;
        if(servicio == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.34/proyectotitulo/web/services/service-region/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            servicio =retrofit.create(ObtenerDatosService.class);

        }
           return servicio;
    }
}
