package com.app.pdi.aplicacionmovilpdi.presenter.interfaces;

import android.widget.Spinner;

public interface RegistroPresenter {

    void validarRegistroPresenter(String nombre, String apellido,String rut, String telefono, String email, String contrasena,
                                  String fechaNacimiento, String sexo, String comuna);

    boolean validarDatosCorrectos(String nombre, String apellido, String rut, String telefono, String email, String contrasena,
                                   String fechaNacimiento, String sexo, String comuna);




}
