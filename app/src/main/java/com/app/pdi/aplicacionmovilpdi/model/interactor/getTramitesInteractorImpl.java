package com.app.pdi.aplicacionmovilpdi.model.interactor;

import android.content.Context;
import android.util.Log;

import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.TramiteContract;
import com.app.pdi.aplicacionmovilpdi.model.service.ObtenerDatosService;
import com.app.pdi.aplicacionmovilpdi.model.utils.Urls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getTramitesInteractorImpl implements TramiteContract.getTramitesInteractor{

    Retrofit retrofit;
    ObtenerDatosService service;


    @Override
    public void getTramiteArrayList(final OnFinishedListener onFinishedListener) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ Urls.direccionGenimotion+"/web/services/service-tramite/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ObtenerDatosService.class);

        Call<List<Tramite>> call = service.obtenerTramites();
        call.enqueue(new Callback<List<Tramite>>() {
            @Override
            public void onResponse(Call<List<Tramite>> call, Response<List<Tramite>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                }else{
                    Log.e("darp","error");
                }

            }

            @Override
            public void onFailure(Call<List<Tramite>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}

