package com.app.pdi.aplicacionmovilpdi.presenter;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.interactor.LoginInteractorImpl;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.LoginInteractor;
import com.app.pdi.aplicacionmovilpdi.model.utils.NetworkState;
import com.app.pdi.aplicacionmovilpdi.model.utils.SharedPreferencesSesion;
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
    private SharedPreferencesSesion preferencesSesion;
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
    public void validarUsuario(String rut, String contrasena) {
        if(view!=null) {
            view.showProgress(true);

                if (validarDatosCorrectos(rut, contrasena)) {
                    interactor.login(rut, contrasena, this).enqueue(new Callback<InicioSesion>() {
                        @Override
                        public void onResponse(Call<InicioSesion> call, Response<InicioSesion> response) {
                            if (response.isSuccessful()) {
                                InicioSesion inicio = response.body();
                                if (inicio.isEstado()) {
                                    SharedPreferencesSesion.get(view.getContext()).guardarPersona(response.body());
                                    view.loginSuccess();
                                    view.showProgress(false);
                                } else if (inicio.getCodigo() == 1) {
                                    view.setRutError();
                                    view.showProgress(false);
                                } else if (inicio.getCodigo() == 401) {
                                    view.setPassNoExist();
                                    view.showProgress(false);
                                }

                            } else {
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

    //Función que valida que los datos ingresados sean correctos
    @Override
    public boolean validarDatosCorrectos(String rut, String contrasena) {
        if(rut.equals("")) {
            view.setErrorUser();
            view.showProgress(false);
            return false;
        }

        if(!validarRut(rut)){
            view.setErrorRutNoExiste();
            view.showProgress(false);
            return false;
        }

        if(contrasena.equals("")){
            view.setErrorPassword();
            view.showProgress(false);
            return false;

        }
        return true;
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

    //Función que valida que el rut ingresado exista.
    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
            char dv = rut.charAt(rut.length() - 1);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }


}
