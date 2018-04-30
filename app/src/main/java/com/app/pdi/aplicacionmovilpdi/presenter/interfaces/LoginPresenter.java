package com.app.pdi.aplicacionmovilpdi.presenter.interfaces;

public interface LoginPresenter {

   void showErrorPresenter(String error);
   void showResultPresenter(String result);
    void validarUsuario(String email, String pass);
}
