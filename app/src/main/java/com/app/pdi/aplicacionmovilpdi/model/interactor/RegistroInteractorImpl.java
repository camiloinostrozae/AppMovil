package com.app.pdi.aplicacionmovilpdi.model.interactor;

import android.widget.Spinner;

import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseRegistro;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RegistroInteractor;
import com.app.pdi.aplicacionmovilpdi.model.service.InicioService;
import com.app.pdi.aplicacionmovilpdi.model.service.PersonaService;
import com.app.pdi.aplicacionmovilpdi.model.utils.Urls;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.RegistroPresenter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroInteractorImpl implements RegistroInteractor {

    RegistroPresenter presenter;
    Retrofit retrofit;
    private PersonaService personaService;

    public RegistroInteractorImpl(RegistroPresenter presenter){
        this.presenter = presenter;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
<<<<<<< HEAD
                .baseUrl("http://192.168.1.33/proyectotitulo/web/service/")
=======
                .baseUrl("http://"+ Urls.direccionJuan+"/web/service/")
>>>>>>> 65e790dba5704d53957a549f066e5fc44685d5bf
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        personaService =retrofit.create(PersonaService.class);
    }
    @Override
    public Call<ResponseRegistro> createPersona(String nombre, String apellido, String rut, String telefono, String email,  String contrasena,String fechaNacimiento,String sexo, String comuna) {
        return personaService.registrarUsuario(nombre,apellido, rut, telefono,email, contrasena, fechaNacimiento, sexo, comuna);
    }
}
