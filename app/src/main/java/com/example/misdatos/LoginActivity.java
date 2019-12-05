package com.example.misdatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.misdatos.Utils.Globals;
import com.example.misdatos.Utils.ReporteService;
import com.example.misdatos.models.CallResult;
import com.example.misdatos.models.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ReporteService service;
    private EditText iniciousuario,inicioclave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciousuario=findViewById(R.id.nlogin);
        inicioclave=findViewById(R.id.nclave);

        service = Globals.getApi().create(ReporteService.class);

        Button btnsend = findViewById(R.id.iniciarsesion);
        btnsend.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick (View view){

                Login();
            }

        });
    }


    private void Login(){
        Call<LoginResult> llamarinicio =service.LoginResult (
                iniciousuario.getText().toString(),inicioclave.getText().toString());

        llamarinicio.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if(response.isSuccessful()){
                    LoginResult resultado=response.body();
                    if(!resultado.isError()){

                        Toast.makeText(getApplicationContext(),"si entra",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Datos Incorrectos, verifica",Toast.LENGTH_LONG).show();

                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
    }



}
