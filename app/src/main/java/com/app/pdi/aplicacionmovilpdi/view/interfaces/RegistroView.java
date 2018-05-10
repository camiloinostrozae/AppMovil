package com.app.pdi.aplicacionmovilpdi.view.interfaces;

public interface RegistroView {

    void showProgress(boolean progress);
    void registroSuccess();
    void registroFailed(String mensaje);
    void setErrorNombre();
    void setErrorApellido();
    void setErrorRut();
    void setErrorEstruturaRut();
    void setErrorEmail();
    void setEstructuraEmail();
    void setErrorContrasena();
    void setErrorTelefono();
    void setEstructuraTelefono();
    void setErrorFechaNacimiento();
    void setErrorSexo();
    void setErrorComuna();

}
