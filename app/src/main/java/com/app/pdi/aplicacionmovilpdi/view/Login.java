package com.app.pdi.aplicacionmovilpdi.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.LoginPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.LoginView;
import com.app.pdi.aplicacionmovilpdi.presenter.LoginPresenterImpl;

public class Login extends AppCompatActivity implements LoginView {

    private EditText email, password;
    private ProgressBar progressBar;

    //Para mandar llamar al LoginPresenter , la interface
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
         //presenter implementado
        presenter = new LoginPresenterImpl(this);
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
        email.setError("Campo obligatorio");
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

    }


    //En caso que el inicio de sesion falle mandamos un mensaje
    @Override
    public void loginFailed(String mensaje) {
        Toast.makeText(Login.this,mensaje,Toast.LENGTH_SHORT).show();

    }

    //botón "iniciar sesion del el login"
    public void validacion(View view){
     presenter.validarUsuario(email.getText().toString(),password.getText().toString());
    }

    public void irRegistro(View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }


}
