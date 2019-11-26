package com.example.misdatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    SharedPreferences infodatos;
    String mNombre, mEmail, mTelefono;

    //Variables de los campos de texto
    EditText txtNombre,txtEmail,txtTelefono;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        infodatos =getSharedPreferences("misDatos", Context.MODE_PRIVATE);

        mNombre= infodatos.getString("nombre2","");
        mEmail= infodatos.getString("correo2","");
        mTelefono= infodatos.getString("telefono2","");

        txtNombre=findViewById(R.id.nombre2);
        txtEmail=findViewById(R.id.correo2);
        txtTelefono=findViewById(R.id.telefono2);

        txtNombre.setText(mNombre);
        txtEmail.setText(mEmail);
        txtTelefono.setText(mTelefono);

    }

    public void guardarDatos (View view){

        if(view.getId() == R.id.button2) {
            SharedPreferences.Editor editor=infodatos.edit();
            editor.putString("nombre2",txtNombre.getText().toString());
            editor.putString("correo2",txtEmail.getText().toString());
            editor.putString("telefono2",txtTelefono.getText().toString());
            editor.commit();

            Toast.makeText(this, "Se guardaron los datos", Toast.LENGTH_LONG).show();





        }


    }
}
