package com.app.pdi.aplicacionmovilpdi.model.interactor;

import android.content.Context;
import android.util.Log;

import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.CampanaContract;
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

public class getCampanasInteractorImpl implements CampanaContract.getCampanasInteractor {

    Retrofit retrofit;
    ObtenerDatosService service;


    @Override
    public void getCampanaArrayList(final OnFinishedListener onFinishedListener) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://arrau.chillan.ubiobio.cl:8075/jdoming/ProyectoTitulo/web/services/service-campana/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ObtenerDatosService.class);

        Call<List<Campana>> call = service.obtenerCampanas();
        call.enqueue(new Callback<List<Campana>>() {
            @Override
            public void onResponse(Call<List<Campana>> call, Response<List<Campana>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                }else{
                    Log.e("darp","error");
                }

            }

            @Override
            public void onFailure(Call<List<Campana>> call, Throwable t) {
                  onFinishedListener.onFailure(t);
            }
        });
    }
}
