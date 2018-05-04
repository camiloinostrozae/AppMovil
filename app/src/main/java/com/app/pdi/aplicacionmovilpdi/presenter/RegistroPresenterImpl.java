package com.app.pdi.aplicacionmovilpdi.presenter;

import android.nfc.Tag;
import android.util.Log;
import android.widget.Spinner;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseRegistro;
import com.app.pdi.aplicacionmovilpdi.model.interactor.RegistroInteractorImpl;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RegistroInteractor;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.RegistroPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.RegistroView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroPresenterImpl implements RegistroPresenter {

    RegistroView view;
    RegistroInteractor interactor;

    public RegistroPresenterImpl(RegistroView view){
        this.view = view;
        interactor = new RegistroInteractorImpl(this);
    }
    @Override
    public void validarRegistroPresenter(String nombre, String apellido, String telefono, String email, String contrasena, String fechaNacimiento, String sexo) {
        Log.e("Dato", nombre);
        Log.e("Dato", apellido);
        Log.e("Dato", telefono);
        Log.e("Dato", email);
        Log.e("Dato", contrasena);
        Log.e("Dato", fechaNacimiento);
        Log.e("Dato", sexo);
        if(view!=null){
            view.showProgress(true);

            if(email.equals("")){
                view.setErrorEmail();
                view.showProgress(false);
            }else{
                if(contrasena.equals("")){
                    view.setErrorContrasena();
                    view.showProgress(false);
                }else{
                    //Se manda a llamar el metodo en registroInteractor
                    interactor.createPersona(nombre,apellido,telefono,email,contrasena,fechaNacimiento,sexo).enqueue(new Callback<ResponseRegistro>() {
                        @Override
                        public void onResponse(Call<ResponseRegistro> call, Response<ResponseRegistro> response) {
                            if(response.isSuccessful()){
                                ResponseRegistro inicio = response.body();
                                if(inicio.isEstado()){
                                    view.registroSuccess();
                                    view.showProgress(false);
                                }else{
                                    view.registroFailed("Datos no válidos");
                                    view.showProgress(false);

                                }

                            }else{
                                view.registroFailed("Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegistro> call, Throwable t) {
                            t.printStackTrace();


                        }
                    });
                }
            }

        }

    }
}
