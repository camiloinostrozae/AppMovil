package com.app.pdi.aplicacionmovilpdi.model.interactor;

import com.app.pdi.aplicacionmovilpdi.model.service.ObtenerDatosService;
import com.app.pdi.aplicacionmovilpdi.model.utils.Urls;

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
<<<<<<< HEAD
                    .baseUrl("http://192.168.1.33/proyectotitulo/web/services/service-region/")
=======
                    .baseUrl("http://"+ Urls.direccionJuan+"/web/services/service-region/")
>>>>>>> 65e790dba5704d53957a549f066e5fc44685d5bf
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            servicio =retrofit.create(ObtenerDatosService.class);

        }
           return servicio;
    }
}
