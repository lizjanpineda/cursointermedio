package com.example.misdatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misdatos.Utils.Globals;
import com.example.misdatos.Utils.ReporteService;
import com.example.misdatos.models.CallResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporteActivity extends AppCompatActivity {


    private SharedPreferences infodatos;
    private ReporteService service;
    private EditText headerNombre,headerMail,headerTelefono,headerReporte,headergeo;


     private FusedLocationProviderClient provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);



        //recuperar share preferences
        infodatos =getSharedPreferences("misDatos", Context.MODE_PRIVATE);

        String pNombre= infodatos.getString("nombre2","");
        String pemail= infodatos.getString("correo2","");
        String ptelefono= infodatos.getString("telefono2","");




        headerNombre=findViewById(R.id.rptnombre);
        headerMail=findViewById(R.id.rptcorreo);
        headerTelefono=findViewById(R.id.rpttelefono);
        headerReporte=findViewById(R.id.rpthechos);
        headergeo=findViewById(R.id.rptgeo);



        headerNombre.setText(pNombre);
        headerMail.setText(pemail);;
        headerTelefono.setText(ptelefono);


        service = Globals.getApi().create(ReporteService.class);

        Button btnsend = findViewById(R.id.button4);
                btnsend.setOnClickListener(new View.OnClickListener(){
                    @Override

                    public void onClick (View view){

                        guardarDatos();
                    }

            });


                provider =new FusedLocationProviderClient(this);
                getPermisos();

        

    }

    private void getUbicacion(){

        provider.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                          if (location!=null){
                              headergeo.setText(location.getLatitude()+","+location.getLongitude());

                          }

                    }
                });
    }


    private void guardarDatos(){
        Call <CallResult> llamadaGuardar =service.agregarReporte(
               headerNombre.getText().toString(),headerMail.getText().toString(),
                headerTelefono.getText().toString(),
                headerReporte.getText().toString());

        llamadaGuardar.enqueue(new Callback<CallResult>() {
            @Override
            public void onResponse(Call<CallResult> call, Response<CallResult> response) {
                if(response.isSuccessful()){
                    CallResult resultado=response.body();

                    if(!resultado.isError()){

                        Toast.makeText(getApplicationContext(),"Se agreso el reporte No."+resultado.getId(),Toast.LENGTH_LONG).show();

                        finish();

                    }



                }


            }

            @Override
            public void onFailure(Call<CallResult> call, Throwable t) {

            }
        });



    }


    private final int PERMISO_USUARIO_LOCALIZACION =1;

    private void getPermisos(){
       if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
               != PackageManager.PERMISSION_GRANTED){

           String[] permisos = {Manifest.permission.ACCESS_COARSE_LOCATION};

           ActivityCompat.requestPermissions(this,permisos,PERMISO_USUARIO_LOCALIZACION);

       }else{

          // headergeo.setText("ya tienes permiso");
           getUbicacion();

       }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults ){

        switch (requestCode){
            case PERMISO_USUARIO_LOCALIZACION:{
                if(grantResults.length > 0  &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //headergeo.setText("SI DIO PERMISO");
                    getUbicacion();
                }else{
                    headergeo.setText("NO DIO PERMISO");
                }
            }
        }
    }
}
