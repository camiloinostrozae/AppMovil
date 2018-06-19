package com.app.pdi.aplicacionmovilpdi.model.interactor;

import com.app.pdi.aplicacionmovilpdi.model.service.ObtenerDatosService;
import com.app.pdi.aplicacionmovilpdi.model.utils.Urls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestInteraccionTramite {

    private static ObtenerDatosService service;
    public static ObtenerDatosService registrarInteraccionTramite(){
        Retrofit retrofit;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://"+Urls.direccionJuan+"/web/services/service-interactuar-tramite/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ObtenerDatosService.class);
        return service;

    }
}
