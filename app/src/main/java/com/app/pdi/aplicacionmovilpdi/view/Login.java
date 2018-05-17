package com.app.pdi.aplicacionmovilpdi.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.utils.NetworkState;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.LoginPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.LoginView;
import com.app.pdi.aplicacionmovilpdi.presenter.LoginPresenterImpl;

public class Login extends AppCompatActivity implements LoginView {

    private EditText rut, password;
    private ProgressBar progressBar;
    ActionBar actionBar;
    //Para mandar llamar al LoginPresenter , la interface
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        rut=(EditText)findViewById(R.id.lrut);
        password=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        actionBar = getSupportActionBar();
        actionBar.hide();
         //presenter implementado
        presenter = new LoginPresenterImpl(this);
        isAvailable();
    }

    @Override
    public void showProgress(boolean progreso) {
        if(progreso){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }

    }


    @Override
    public void setErrorUser() {
        rut.setError("Campo obligatorio");
    }

    @Override
    public void setErrorPassword() {
        password.setError("Campo obligatorio");
    }


    //En caso que el inicio de sesión resulte exitoso lo llevamos a la Activity principal
    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this,PrincipalActivity.class);
        startActivity(intent);
        //Para no permitir regresar al login con el boton de vuelta atrás
        finish();
    }


    //En caso que el inicio de sesion falle mandamos un mensaje
    @Override
    public void loginFailed(String mensaje) {
        Toast.makeText(Login.this,mensaje,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setRutError() {
        rut.setError("El Rut ingresado no se encuentra registrado en la aplicación");
    }

    @Override
    public void setPassNoExist() {
        password.setError("Contraseña inválida");
    }

    @Override
    public void setErrorRutNoExiste() {
        rut.setError("El rut ingresado no existe");
    }

    @Override
    public void setErrorConexion(String mensaje) {
        Toast.makeText(Login.this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    //botón "iniciar sesion del el login"
    public void validacion(View view){

     presenter.validarUsuario(rut.getText().toString(),password.getText().toString());
    }

    public void irRegistro(View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public  void isAvailable(){
        if(NetworkState.getInstance().estadoConexion(this)){
            Toast.makeText(Login.this,"SIN CONEXIÓN",Toast.LENGTH_SHORT).show();
        }
    }




}
