package com.app.pdi.aplicacionmovilpdi.view;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.Comuna;
import com.app.pdi.aplicacionmovilpdi.model.Object.Region;
import com.app.pdi.aplicacionmovilpdi.model.interactor.RestComuna;
import com.app.pdi.aplicacionmovilpdi.model.interactor.RestRegion;
import com.app.pdi.aplicacionmovilpdi.model.service.ObtenerDatosService;
import com.app.pdi.aplicacionmovilpdi.presenter.RegistroPresenterImpl;
import com.app.pdi.aplicacionmovilpdi.presenter.interfaces.RegistroPresenter;
import com.app.pdi.aplicacionmovilpdi.view.interfaces.RegistroView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity implements RegistroView {

    //datos que no requieren de un tratamiento especial
    private EditText nombre, apellido, password, telefono, email, fechaNacimiento, rut;
    //Spinner del sexo
    private Spinner sexo;
    private TextView label_sexo, label_region, label_comuna;
    private Spinner region_spinner;
    private HashMap<Integer,String> spinnerComunaMap;
    private ArrayList<String> region_nombre;
    private ArrayList<Integer> region_id;
    private ArrayAdapter<String> regionArrayAdapter;
    private Spinner comuna_spinner;
    private ArrayList<String> comuna_nombre;
    private ArrayList<Integer> comuna_id;
    private String id;
    private ArrayAdapter<String> comunaArrayAdapter;
    //Barra de progreso
    private ProgressBar progressBar;
    private RegistroPresenter presenter;
    private android.support.v7.app.ActionBar actionBar;

    //Para mostrar la fecha de nacimiento como calendario
    Calendar calendar;
    int dia, mes, anio;
    private ArrayList<Comuna> comunas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        region_nombre = new ArrayList<>();
        region_id = new ArrayList<>();
        comuna_nombre = new ArrayList<>();
        comuna_id = new ArrayList<>();
        nombre = (EditText)findViewById(R.id.nombre);
        apellido = (EditText)findViewById(R.id.apellido);
        telefono = (EditText)findViewById(R.id.telefono);
        email =  (EditText)findViewById(R.id.remail);
        password = (EditText)findViewById(R.id.rcontrasena);
        rut = (EditText)findViewById(R.id.rut);
        rut.addTextChangedListener(cambiarFormatoRut());
        fechaNacimiento = (EditText)findViewById(R.id.rfechanacimiento);
        //Spinner para el sexo
        sexo = (Spinner) findViewById(R.id.sexo);
        String [] listaSexo = new String [] {"Seleccione una opción","Masculino","Femenino"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterSexo = new ArrayAdapter<String>(this, R.layout.spinner_sexo_style,listaSexo);
        adapterSexo.setDropDownViewResource(R.layout.spinner_sexo_style);
        sexo.setAdapter(adapterSexo);
        label_sexo = findViewById(R.id.label_sexo);
        //Spinner para la region
        comuna_spinner = (Spinner) findViewById(R.id.comuna);
        label_comuna = findViewById(R.id.label_comuna);
        //getComunas();
        region_spinner = (Spinner) findViewById(R.id.region);
        label_region = findViewById(R.id.label_region);
        comunas = new ArrayList<>();

        //barra de progreso
        progressBar = (ProgressBar) findViewById(R.id.rProgressBar);

        actionBar  = getSupportActionBar();
        actionBar.hide();

        //Instanciamos el calendario que se mostrará en la fecha de nacimiento
        calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        fechaNacimiento.setText(i2 + "-" + i1 + "-" + i);

                    }
                }, anio, mes, dia);
                dialog.show();
            }
        });

        //Instanciamos el registroPresenterImpl a través de la interface RegistroPresenter
        presenter = new RegistroPresenterImpl(this);
        getRegion();
        setSpinnerItem();
        region_spinner.setOnItemSelectedListener(region_listener);
        comuna_spinner.setOnItemSelectedListener(comuna_listener);

    }

    @Override
    public void showProgress(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void registroSuccess(String mensaje) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("REGISTRO_EXITOSO",mensaje);
        startActivity(intent);
        finish();
    }

    @Override
    public void registroFailed(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorNombre() {
        nombre.setError("Campo obligatorio");

    }

    @Override
    public void setNombreNoValido() {
        nombre.setError("Nombre no válido");
    }

    @Override
    public void setErrorApellido() {
        apellido.setError("Campo obligatorio");

    }

    @Override
    public void setErrorApellidoNoValido() {
        apellido.setError("Apellido no válido");

    }

    @Override
    public void setErrorRut() {
        rut.setError("Campo obligatorio");
    }

    @Override
    public void setErrorEstruturaRut() {
        rut.setError("El rut ingresado es inválido");
    }

    @Override
    public void setErrorRutExiste() {
        rut.setError("El R.U.T ingresado ya se encuentra registrado en la aplicación ");
    }

    @Override
    public void setErrorEmail() {
        email.setError("Campo obligatorio");

    }

    @Override
    public void setEstructuraEmail() {
        email.setError("Email inválido");
    }

    @Override
    public void setEmailExiste() {
        email.setError("El email ingresado ya se encuentra registrado en la aplicación");
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
    public void setEstructuraTelefono() {
        telefono.setError("El télefono debe contener 8 dígitos");
    }

    @Override
    public void setErrorFechaNacimiento() {
        fechaNacimiento.setError("Campo obligatorio");

    }

    @Override
    public void setErrorSexo() {
        label_sexo.setError("Debe seleccionar su sexo");
    }

    @Override
    public void setErrorRegion() {
        label_region.setError("Debe seleccionar una región");
    }

    @Override
    public void setErrorComuna() {
        label_comuna.setError("Debe seleccionar una comuna");
    }

    //Se manda llamar al presenter con los datos que el usuario ingresó
    public void validacionRegistro(View view) {
        presenter.validarRegistroPresenter(nombre.getText().toString(), apellido.getText().toString(), rut.getText().toString(),
                telefono.getText().toString(), email.getText().toString(), password.getText().toString(),
                fechaNacimiento.getText().toString(), sexo.getSelectedItem().toString(),id);
    }

    /*Método que esta al modo escucha cuando el usuario seleccione una región y luego en base a la región seleccionada se
      se muestran las comunas*/
    private AdapterView.OnItemSelectedListener region_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {
            if (posicion > 0) {
                getComuna(region_id.get(posicion));

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    /*Método que esta al modo escucha cuando el usuario seleccione una región y luego en base a la región seleccionada se
      se muestran las comunas*/
    private AdapterView.OnItemSelectedListener comuna_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {
            if (posicion > 0) {
                id = comuna_id.get(posicion).toString();
            }else{
                id = "Seleccione una comuna";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    //función para establecer los spinners mostrar los elementos de de región o comuna.
    private void setSpinnerItem() {
        regionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.region));
        regionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(regionArrayAdapter);

        comunaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.comuna));
        comunaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comuna_spinner.setAdapter(comunaArrayAdapter);
        comuna_spinner.setEnabled(false);
    }

    public void getRegion() {
        regionLoad(true);
        RestRegion.getRegion().obtenerRegion().enqueue(new Callback<List<Region>>() {
            @Override
            public void onResponse(Call<List<Region>> call, Response<List<Region>> response) {
                region_nombre.clear();
                region_id.clear();
                region_nombre.add("Seleccione una región");
                region_id.add(0);
                if (response.isSuccessful()) {

                    List<Region> lista = response.body();
                    String[] regiones = new String[lista.size()];
                    for (int i = 0; i < lista.size(); i++) {
                        region_nombre.add(lista.get(i).getNombre());
                        region_id.add(lista.get(i).getIdRegion());
                    }
                    regionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, region_nombre);
                    regionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    region_spinner.setAdapter(regionArrayAdapter);
                    regionLoad(false);
                } else {
                    regionLoad(false);
                }
            }

            @Override
            public void onFailure(Call<List<Region>> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }

    //Función que habilita la selección para las regiones
    public void regionLoad(boolean estado) {
        region_spinner.setEnabled(true);
        comuna_spinner.setEnabled(false);
    }

    public void getComuna(int id) {
        RestComuna.getComuna().obtenerComuna(id).enqueue(new Callback<List<Comuna>>() {
            @Override
            public void onResponse(Call<List<Comuna>> call, Response<List<Comuna>> response) {
                if (response.isSuccessful()) {
                    comuna_nombre.clear();
                    comuna_id.clear();
                    comuna_nombre.add("Seleccione una comuna");
                    comuna_id.add(0);
                    List<Comuna> lista = response.body();
                    for (int i = 0; i < lista.size(); i++) {
                        comuna_nombre.add(lista.get(i).getNombre());
                        comuna_id.add(lista.get(i).getIdComuna());

                    }
                    comunaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, comuna_nombre);
                    comunaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    comuna_spinner.setAdapter(comunaArrayAdapter);
                    comunaLoad(false);
                } else {
                    comunaLoad(false);
                }
            }

            @Override
            public void onFailure(Call<List<Comuna>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    //Método que habilita la seleccion para las comunas dentro de spinner.
    public void comunaLoad(boolean estado) {
        region_spinner.setEnabled(!estado);
        comuna_spinner.setEnabled(!estado);
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
