package com.app.pdi.aplicacionmovilpdi.view.interfaces;

import android.content.Context;

public interface LoginView {

    void showProgress(boolean progreso);
    void setErrorUser();
    void setErrorPassword();
    void loginSuccess();
    void loginFailed(String mensaje);
    void registroSuccess(String mensaje);
    void setRutError();
    void setPassNoExist();
    void setErrorRutNoExiste();
    void setErrorConexion(String mensaje);
    Context getContext();
}
