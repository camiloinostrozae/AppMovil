package com.app.pdi.aplicacionmovilpdi.view.interfaces;

public interface LoginView {

    void showProgress(boolean progreso);
    void setErrorUser();
    void setErrorPassword();
    void loginSuccess();
    void loginFailed(String mensaje);
    void setRutError();
    void setPassNoExist();
    void setErrorRutNoExiste();
    void setErrorConexion(String mensaje);
}
