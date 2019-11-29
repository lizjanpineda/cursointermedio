package com.example.misdatos.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.misdatos.R;
import com.example.misdatos.Utils.Globals;
import com.example.misdatos.Utils.ReporteService;
import com.example.misdatos.models.Empleado;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Gson gson;
    private TextView txtRes;

    private ReporteService service;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        gson = new Gson();
        final Empleado EmpleadoObjeto= new Empleado(1,"Carlos Rivera","Femsa");

        final String jsonObjeto = "{id:4,nombre:\"Pablito Perez\",empresa:\"Pablito Perez\"}";

       txtRes=root.findViewById(R.id.textohome);

        Button toJsonBtn = root.findViewById(R.id.bhome1);
        toJsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                claseAJson(EmpleadoObjeto);

            }
        });

Button fromJsonBtn = root.findViewById(R.id.bhome2);
    fromJsonBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            jsonAClase(jsonObjeto);

        }
    });

        service= Globals.getApi().create(ReporteService.class);

        Button llamaruno=root.findViewById(R.id.llamadauno);
        llamaruno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("llamada","click en boton");
                getEmpleado(5);


            }
        });


        Button llamartodos=root.findViewById(R.id.llamadatodos);
        llamartodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpleado(5);

            }
        });

        return root;
    }

    private Call<Empleado> getEmpleadoCall;

    private void getEmpleado(int id){
        getEmpleadoCall=service.getEmpleadoUnico(id);
        getEmpleadoCall.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                Log.i("llamada",response.toString());
                if(response.isSuccessful()){
                    Empleado empresult=response.body();
                    claseAJson(empresult);
                }

            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.i("error llamada",t.getMessage());
            }
        });

    }

    private void claseAJson(Empleado empleado){
        String resultado= gson.toJson(empleado);
        txtRes.setText(resultado);

    }

    private void jsonAClase(String json){
        Empleado empResult =gson.fromJson(json,Empleado.class);
                String resultado= "id:"+empResult.getId();
        resultado += "\nnombre: "+empResult.getNombre();
        resultado += "\nEmpresa: "+empResult.getEmpresa();
        txtRes.setText(resultado);

    }
}