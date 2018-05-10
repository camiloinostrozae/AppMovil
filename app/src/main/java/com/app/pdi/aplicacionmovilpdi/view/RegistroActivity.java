package com.app.pdi.aplicacionmovilpdi.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
    private EditText nombre, apellido, password,telefono, email,fechaNacimiento,rut;
    //Spinner del sexo
    private Spinner sexo;
    private Spinner region_spinner;
    private ArrayList<String> region_nombre;
    private ArrayList<Integer> region_id;
    private ArrayAdapter<String> regionArrayAdapter;
    private Spinner comuna_spinner;
    private ArrayList<String> comuna_nombre;
    private ArrayList<Integer> comuna_id;
    private ArrayAdapter<String> comunaArrayAdapter;
    //Barra de progreso
    private ProgressBar progressBar;
    private RegistroPresenter presenter;

    //Para mostrar la fecha de nacimiento como calendario
    Calendar calendar;
    int dia, mes, anio;
    private ArrayList<Comuna>comunas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        region_nombre = new ArrayList<>();
        region_id = new ArrayList<>();
        comuna_nombre = new ArrayList<>();
        comuna_id = new ArrayList<>();

        nombre = (EditText) findViewById(R.id.nombre);
        apellido = (EditText) findViewById(R.id.apellido);
        password = (EditText) findViewById(R.id.rcontraseña);
        email = (EditText) findViewById(R.id.remail);
        telefono = (EditText)findViewById(R.id.telefono);
        fechaNacimiento = (EditText)findViewById(R.id.rfechanacimiento);
        rut = (EditText)findViewById(R.id.rut);
        //Spinner para el sexo
        sexo = (Spinner) findViewById(R.id.sexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        sexo.setAdapter(adapter);
        //Spinner para la region
        comuna_spinner = (Spinner)findViewById(R.id.comuna);
        //getComunas();
        region_spinner = (Spinner)findViewById(R.id.region);
        comunas = new ArrayList<>();

        //barra de progreso
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
        //region.setOnItemSelectedListener(region_listener);
        getRegion2();
        setSpinnerItem();
        region_spinner.setOnItemSelectedListener(region_listener);

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
    public void setErrorRut() {
        rut.setError("Campo obligatorio");
    }

    @Override
    public void setErrorEstruturaRut() {
        rut.setError("El rut ingresado es inválido");
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
    public void setErrorContrasena() {
        password.setError("Campo obligatorio");

    }

    @Override
    public void setErrorTelefono() {
        telefono.setError("Campo obligatorio");

    }

    @Override
    public void setEstructuraTelefono() {
        telefono.setError("El télefono debe estar compuesto por 8 dígitos");
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
        presenter.validarRegistroPresenter(nombre.getText().toString(),apellido.getText().toString(),rut.getText().toString(),
                telefono.getText().toString(),email.getText().toString(),password.getText().toString(),
                fechaNacimiento.getText().toString(),sexo.getSelectedItem().toString(),comuna_spinner.getSelectedItemPosition());
    }


        public void getRegiones() {
            Retrofit retrofit;
            ObtenerDatosService service;
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.3.2/ProyectoTitulo/web/services/service-region/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service =retrofit.create(ObtenerDatosService.class);

            Call<List<Region>> call = service.obtenerRegion();
            call.enqueue(new Callback<List<Region>>() {
                @Override
                public void onResponse(Call<List<Region>> call, Response<List<Region>> response) {
                    List<Region> lista = response.body();
                    String [] regiones  =new String[lista.size()];
                    for(int i = 0;i<lista.size();i++){
                        regiones[i] = lista.get(i).getNombre();
                    }

                    region_spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,regiones));
                    //region.setOnItemClickListener();
                }

                @Override
                public void onFailure(Call<List<Region>> call, Throwable t) {
                    t.printStackTrace();

                }
            });

        }

  /*  public void getComunas() {
        Retrofit retrofit;
        ObtenerDatosService service;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2/ProyectoTitulo/web/services/service-comuna/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service =retrofit.create(ObtenerDatosService.class);

        Call<List<Comuna>> call = service.obtenerComuna();
        call.enqueue(new Callback<List<Comuna>>() {
            @Override
            public void onResponse(Call<List<Comuna>> call, Response<List<Comuna>> response) {
                List<Comuna> lista = response.body();
                String [] comunas  =new String[lista.size()];
                for(int i = 0;i<lista.size();i++){
                    comunas[i] = lista.get(i).getNombre();
                }

                comuna_spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,comunas));
                //region.setOnItemClickListener();
            }

            @Override
            public void onFailure(Call<List<Comuna>> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }
    */

        private AdapterView.OnItemSelectedListener region_listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {
                if(posicion>0){
                    getComuna2(region_id.get(posicion));
                }

              //  comunaArrayAdapter = new ArrayAdapter<Comuna>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,tempComuna);
               // comunaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //comuna_spinner.setAdapter(comunaArrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };


    private void setSpinnerItem(){
        regionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.region));
        regionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(regionArrayAdapter);

        comunaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.comuna));
        comunaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comuna_spinner.setAdapter(comunaArrayAdapter);
        comuna_spinner.setEnabled(false);
    }

    public void getRegion2(){
        regionLoad(true);
        RestRegion.getRegion().obtenerRegion().enqueue(new Callback<List<Region>>() {
            @Override
            public void onResponse(Call<List<Region>> call, Response<List<Region>> response) {
                region_nombre.clear();
                region_id.clear();
                region_nombre.add("Seleccione una región");
                region_id.add(0);
               if(response.isSuccessful()) {

                   List<Region> lista = response.body();
                   String[] regiones = new String[lista.size()];
                   for (int i = 0; i < lista.size(); i++) {
                        region_nombre.add(lista.get(i).getNombre());
                        region_id.add(lista.get(i).getIdRegion());
                   }
                   regionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,region_nombre);
                   regionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   region_spinner.setAdapter(regionArrayAdapter);
                   regionLoad(false);
               }else{
                   regionLoad(false);
               }
            }

            @Override
            public void onFailure(Call<List<Region>> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }

    public void regionLoad(boolean estado){
        region_spinner.setEnabled(true);
        comuna_spinner.setEnabled(false);
    }

    public void getComuna2(int id){
        RestComuna.getComuna().obtenerComuna(id).enqueue(new Callback<List<Comuna>>() {
            @Override
            public void onResponse(Call<List<Comuna>> call, Response<List<Comuna>> response) {
                if(response.isSuccessful()){
                    comuna_nombre.clear();
                    comuna_id.clear();
                    comuna_nombre.add("Seleccione una comuna");
                    comuna_id.add(0);
                    List<Comuna> lista = response.body();
                    String[] regiones = new String[lista.size()];
                    for (int i = 0; i < lista.size(); i++) {
                        comuna_nombre.add(lista.get(i).getNombre());
                        comuna_id.add(lista.get(i).getIdComuna());
                    }
                    comunaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,comuna_nombre);
                    comunaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    comuna_spinner.setAdapter(comunaArrayAdapter);
                    comunaLoad(false);
                }else{
                    comunaLoad(false);
                }
            }

            @Override
            public void onFailure(Call<List<Comuna>> call, Throwable t) {

            }
        });

    }

    public void comunaLoad(boolean estado){
        region_spinner.setEnabled(!estado);
        comuna_spinner.setEnabled(!estado);
    }

}
