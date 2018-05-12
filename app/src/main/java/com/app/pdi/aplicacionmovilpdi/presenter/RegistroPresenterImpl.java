package com.app.pdi.aplicacionmovilpdi.presenter;

import android.util.Log;
import android.util.Patterns;

import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseRegistro;
import com.app.pdi.aplicacionmovilpdi.model.interactor.RegistroInteractorImpl;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RegistroInteractor;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.RegistroPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.RegistroView;

import java.nio.file.Path;

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
    public void validarRegistroPresenter(String nombre, String apellido, String rut, String telefono, String email, String contrasena, String fechaNacimiento, String sexo,String comuna) {
        Log.e("Dato", nombre);
        Log.e("Dato", apellido);
        Log.e("Dato", telefono);
        Log.e("Dato", email);
        Log.e("Dato", contrasena);
        Log.e("Dato", fechaNacimiento);
        Log.e("Dato", sexo);
        Log.e("Dato", rut);
        Log.e("Dato", ""+comuna);
        if(view!=null){
            view.showProgress(true);
            if(validarDatosCorrectos(nombre, apellido, rut, telefono, email, contrasena, fechaNacimiento, sexo,comuna)){
                    //Se manda a llamar el metodo en registroInteractor
                    interactor.createPersona(nombre,apellido, rut,telefono,email,contrasena,fechaNacimiento,sexo,comuna).enqueue(new Callback<ResponseRegistro>() {
                        @Override
                        public void onResponse(Call<ResponseRegistro> call, Response<ResponseRegistro> response) {
                            if(response.isSuccessful()){
                                ResponseRegistro inicio = response.body();
                                if(inicio.isEstado()){
                                    view.registroSuccess();
                                    view.showProgress(false);
                                }else if(inicio.getCodigo()==1){
                                    view.setErrorRutExiste();
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

    @Override
    public boolean validarDatosCorrectos(String nombre, String apellido, String rut, String telefono, String email, String contrasena, String fechaNacimiento, String sexo,String comuna) {

        if(nombre.isEmpty()) {
            view.setErrorNombre();
            view.showProgress(false);
            return false;
        }
        if(!validarNombre(nombre)){
            view.setNombreNoValido();
            view.showProgress(false);
            return false;
        }
        if(apellido.isEmpty()) {
            view.setErrorApellido();
            view.showProgress(false);
            return false;
        }

        if(!validarApellido(apellido)){
            view.setErrorApellidoNoValido();
            view.showProgress(false);
            return false;
        }
        if(rut.equals("")){
            view.setErrorRut();
            view.showProgress(false);
            return false;
        }

        if(!validarRut(rut)){
            view.setErrorEstruturaRut();
            view.showProgress(false);
            return false;
        }

        if(telefono.isEmpty()) {
            view.setErrorTelefono();
            view.showProgress(false);
            return false;
        }

        if(telefono.length()>9 || telefono.length()<9){
            view.setEstructuraTelefono();
            view.showProgress(false);
            return false;
        }


        if(email.equals("")) {
            view.setErrorEmail();
            view.showProgress(false);
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            view.setErrorEstruturaRut();
            view.showProgress(false);
            return false;
        }

        if(contrasena.equals("")) {
            view.setErrorContrasena();
            view.showProgress(false);
            return false;
        }

        if(fechaNacimiento.equals("")) {
            view.setErrorFechaNacimiento();
            view.showProgress(false);
            return false;
        }

        if(sexo.equals("")) {
            view.setErrorSexo();
            view.showProgress(false);
            return false;
        }

        if(sexo.equals("Seleccione una opción")){
            view.setErrorSexo();
            view.showProgress(false);
            return false;
        }

        if(comuna.equals("Seleccione una comuna")){
            view.setErrorComuna();
            view.showProgress(false);
            return false;
        }



        return true;
    }



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

    public boolean validarNombre(String nombre){
        return nombre.matches("[A-Z][a-zA-Z]*");
    }

    public boolean validarApellido(String apellido){
        return apellido.matches("[a-zA-Z]+([ '-][a-zA-Z]+)*");
    }
}
