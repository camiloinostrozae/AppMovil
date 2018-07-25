package com.app.pdi.aplicacionmovilpdi.view.interfaces;

public interface RegistroView {

    void showProgress(boolean progress);
    void registroSuccess(String mensaje);
    void registroFailed(String mensaje);
    void setErrorNombre();
    void setNombreNoValido();
    void setErrorApellido();
    void setErrorApellidoNoValido();
    void setErrorRut();
    void setErrorEstruturaRut();
    void setErrorRutExiste();
    void setErrorEmail();
    void setErrorEstructuraEmail();
    void setEmailExiste();
    void setErrorContrasena();
    void setErrorTelefono();
    void setEstructuraTelefono();
    void setTelefonoExiste();
    void setErrorFechaNacimiento();
    void setErrorSexo();
    void setErrorRegion();
    void setErrorComuna();

}
