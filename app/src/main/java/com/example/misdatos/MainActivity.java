package com.example.misdatos;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //otra forma
    public Button miboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ////otra forma//////
        miboton= findViewById(R.id.button3);

        miboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent miIntent =null;
                Toast.makeText(getApplicationContext(),"hola soy el otro boton",Toast.LENGTH_LONG).show();
                //para cambiar de activity
                Intent miIntent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(miIntent);



            }
        });////

    }


    public void pasapagina2(View view){
        Intent miIntent =null;
        switch (view.getId()){
            case R.id.button:
                Toast.makeText(this,"Pasa a pagina 2",Toast.LENGTH_LONG).show();

               //para cambiar de activity
                miIntent=new Intent(MainActivity.this,Main2Activity.class);
                 break;


        }
        if (miIntent!=null){ //nueva linea
            startActivity(miIntent); //nueva linea enciende pantalla activity

        }
}
}
