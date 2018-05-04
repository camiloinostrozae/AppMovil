package com.app.pdi.aplicacionmovilpdi.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.presenter.RegistroPresenterImpl;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.RegistroPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.RegistroView;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity implements RegistroView {

    //datos que no requieren de un tratamiento especial
    private EditText nombre, apellido, password,telefono, email,fechaNacimiento;
    //Spinner del sexo
    private Spinner sexo;
    //Barra de progreso
    private ProgressBar progressBar;
    private RegistroPresenter presenter;

    //Para mostrar la fecha de nacimiento como calendario
    Calendar calendar;
    int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre = (EditText) findViewById(R.id.nombre);
        apellido = (EditText) findViewById(R.id.apellido);
        password = (EditText) findViewById(R.id.rcontraseña);
        email = (EditText) findViewById(R.id.remail);
        telefono = (EditText)findViewById(R.id.telefono);
        fechaNacimiento = (EditText)findViewById(R.id.rfechanacimiento);
        //Spinner para el sexo
        sexo = (Spinner) findViewById(R.id.sexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        sexo.setAdapter(adapter);

        progressBar = (ProgressBar)findViewById(R.id.rProgressBar);

        //Instanciamos el calendario que se mostrará en la fecha de nacimiento
        calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);
        fechaNacimiento.setText(dia +"-" + mes +"-"+anio);
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 +1;
                        fechaNacimiento.setText(i2+"-"+i1+"-"+i);

                    }
                }, anio,mes,dia);
                dialog.show();
            }
        });

        //Instanciamos el registroPresenterImpl a través de la interface RegistroPresenter
        presenter  = new RegistroPresenterImpl(this);

    }

    @Override
    public void showProgress(boolean progress) {
        if(progress){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void registroSuccess() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    @Override
    public void registroFailed(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorNombre() {
        nombre.setError("Campo obligatorio");

    }

    @Override
    public void setErrorApellido() {
        apellido.setError("Campo obligatorio");

    }

    @Override
    public void setErrorEmail() {
        email.setError("Campo obligatorio");

    }

    @Override
    public void setErrorContrasena() {
        password.setError("Campo obligatorio");

    }

    @Override
    public void setErrorTelefono() {
        telefono.setError("Campo obligatorio");

    }

    @Override
    public void setErrorFechaNacimiento() {
        fechaNacimiento.setError("Campo obligatorio");

    }

    @Override
    public void setErrorSexo() {

    }

    @Override
    public void setErrorComuna() {

    }

    public void validacionRegistro(View view){
        presenter.validarRegistroPresenter(nombre.getText().toString(),apellido.getText().toString(),
                telefono.getText().toString(),email.getText().toString(),password.getText().toString(),
                fechaNacimiento.getText().toString(),sexo.getSelectedItem().toString());
    }
}
