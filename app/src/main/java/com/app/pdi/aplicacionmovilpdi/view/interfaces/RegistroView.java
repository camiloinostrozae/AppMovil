package com.app.pdi.aplicacionmovilpdi.view.interfaces;

public interface RegistroView {

    void showProgress(boolean progress);
    void registroSuccess();
    void registroFailed(String mensaje);
    void setErrorNombre();
    void setErrorApellido();
    void setErrorEmail();
    void setErrorContrasena();
    void setErrorTelefono();
    void setErrorFechaNacimiento();
    void setErrorSexo();
    void setErrorComuna();
}
