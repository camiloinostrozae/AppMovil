package com.app.pdi.aplicacionmovilpdi.model.interactor;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.LoginInteractor;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.OnLoginFinishListener;
import com.app.pdi.aplicacionmovilpdi.model.service.InicioService;
import com.app.pdi.aplicacionmovilpdi.model.service.PersonaService;
import com.app.pdi.aplicacionmovilpdi.model.utils.SharedPreferencesSesion;
import com.app.pdi.aplicacionmovilpdi.model.utils.Urls;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.LoginPresenter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private Retrofit retrofit;
    private InicioService personaService;
    public LoginInteractorImpl(LoginPresenter presenter){
        this.presenter = presenter;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2/proyectotitulo/web/service/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        personaService =retrofit.create(InicioService.class);
    }

    @Override
        public Call<InicioSesion>login(String email, String contrasena, OnLoginFinishListener onLoginFinishListener) {
        return personaService.login(email, contrasena);
    }
}
