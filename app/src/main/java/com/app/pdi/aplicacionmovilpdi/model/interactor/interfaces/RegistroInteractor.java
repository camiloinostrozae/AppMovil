package com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces;

import android.widget.Spinner;

import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;
import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseRegistro;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.RegistroPresenter;

import retrofit2.Call;

public interface RegistroInteractor {


    Call<ResponseRegistro> createPersona(String nombre, String apellido, String rut,String telefono, String email, String contrasena,
                                String fechaNacimiento, String sexo, int comuna);
}
