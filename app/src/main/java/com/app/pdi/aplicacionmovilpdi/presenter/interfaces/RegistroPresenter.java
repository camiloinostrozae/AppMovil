package com.app.pdi.aplicacionmovilpdi.presenter.interfaces;

import android.widget.Spinner;

public interface RegistroPresenter {

    void validarRegistroPresenter(String nombre, String apellido, String telefono, String email, String contrasena,
                                  String fechaNacimiento, String sexo);
}
