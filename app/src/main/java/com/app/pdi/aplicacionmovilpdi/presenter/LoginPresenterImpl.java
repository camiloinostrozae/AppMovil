package com.app.pdi.aplicacionmovilpdi.presenter;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.interactor.LoginInteractorImpl;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.LoginInteractor;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.LoginPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.LoginView;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.OnLoginFinishListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishListener {
    //Se declaran las interfaces de vista e interactor
    private LoginView view;
    private LoginInteractor interactor;
    String resultado;

    public LoginPresenterImpl(LoginView view){
        this.view = view;
        //Se inicia con las clase de impl
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void showErrorPresenter(String error) {

    }

    @Override
    public void showResultPresenter(String result) {
        if(view != null)
        {
            view.showProgress(false);
        }
    }

    @Override
    public void validarUsuario(String email, String contrasena) {
        if(view!=null){
            view.showProgress(true);

            if(email.equals("")){
                view.setErrorUser();
                view.showProgress(false);
            }else{
                if(contrasena.equals("")){
                   view.setErrorPassword();
                   view.showProgress(false);
                }else{
                    interactor.login(email,contrasena,this).enqueue(new Callback<InicioSesion>() {
                        @Override
                        public void onResponse(Call<InicioSesion> call, Response<InicioSesion> response) {
                            if(response.isSuccessful()){
                                InicioSesion inicio = response.body();
                                if(inicio.isEstado()){
                                    view.loginSuccess();
                                    view.showProgress(false);
                                }else{
                                    view.loginFailed("Datos no válidos");
                                    view.showProgress(false);
                                }

                            }else{
                                view.loginFailed("Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<InicioSesion> call, Throwable t) {
                            t.printStackTrace();

                        }
                    });
                }
            }

        }

    }


    @Override
    public void setErrorUser() {
        if(view!=null){
            view.showProgress(false);
            view.setErrorUser();
        }

    }

    @Override
    public void setErrorPass() {

        if(view!=null){
           view.showProgress(false);
            view.setErrorPassword();
        }

    }

    @Override
    public void exitoOperacion() {

        if(view!=null){
            view.showProgress(true);

        }

    }


}
