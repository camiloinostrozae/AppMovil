package com.app.pdi.aplicacionmovilpdi.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.utils.NetworkState;
import com.app.pdi.aplicacionmovilpdi.model.utils.SharedPreferencesSesion;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.LoginPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.LoginView;
import com.app.pdi.aplicacionmovilpdi.presenter.LoginPresenterImpl;

public class Login extends AppCompatActivity implements LoginView {

    private EditText rut, password;
    private ProgressBar progressBar;
    ActionBar actionBar;
    //Para mandar llamar al LoginPresenter , la interface
    private LoginPresenter presenter;
    //variable del tipo SharedPreferencesSesion que servirá mantener la sesión activa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Comprobamos si el usuario ya se encuentra logueado en la app
        * si es verdadero, lo mandamos directamente a la actividad principal
        * sino es así, el login se carga normalmente.*/
        if(SharedPreferencesSesion.get(this).isLogged()){
          Intent intent = new Intent(this, PrincipalActivity.class);
          startActivity(intent);
          /*finish es para que una vez en PrincipalActivity, no pueda volver al login si
            es que llegua a presionar el boton  de volver atrás*/
          finish();
        }
        setContentView(R.layout.login);
        rut=(EditText)findViewById(R.id.lrut);
        rut.addTextChangedListener(cambiarFormatoRut());
        password=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        String mensaje = intent.getStringExtra("REGISTRO_EXITOSO");
        if(mensaje!=null){
            registroSuccess(mensaje);
        }
         //presenter implementado
        presenter = new LoginPresenterImpl(this);
        //isAvailable();
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
    public void registroSuccess(String mensaje) {
        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
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

    //botón "iniciar sesion del login"
    public void validacion(View view){
     presenter.validarUsuario(rut.getText().toString(),password.getText().toString());
    }

    public void irRegistro(View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public  void isAvailable(){
        if(!NetworkState.getInstance().estadoConexion(this)){
            Log.e("Hola","HOLAAAAAAAAAAAAAAAAAAAAAAAAAA");
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Aviso");
            dialogo.setMessage("Conexión a internet no disponible, asegúrese de estar conectado a internet");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    finish();
                }
            });

        }
    }


    public String formatearRut(String rut){
        int cont = 0;
        String formato;
        //Si se ingresa un punto se cambia por una cadena vacía
        rut = rut.replace(".","");
        //Si ingresa el guión se cambia por una cadena vacía
        rut = rut.replace("-","");
        //Se agrega el "-" al String formato
        formato = "-" + rut.substring(rut.length()-1);
        for(int i = rut.length()-2; i>=0;i--){
            formato = rut.substring(i, i + 1) + formato;
            cont++;
            if (cont == 3 && i != 0) {
                formato = "." + formato;
                cont = 0;
            }
        }
        return formato;
    }

    //Método utilizado para ir cambiando en tiempo real el formato el rut
    private TextWatcher cambiarFormatoRut(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rut.removeTextChangedListener(this);
                try{
                    if(rut.getText().length()>1){
                        String rutFormateado = formatearRut(rut.getText().toString());
                        rut.getText().replace(0,rut.getText().length(),rutFormateado,0,rutFormateado.length());

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                rut.addTextChangedListener(this);

            }

        };

    }

}
