package com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;


import retrofit2.Call;

public interface LoginInteractor {

    Call<InicioSesion> login(String email, String contrasena, OnLoginFinishListener onLoginFinishListener);
}
