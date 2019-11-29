package com.example.misdatos.Utils;

import com.example.misdatos.models.Empleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReporteService {
     //https://isantosp.com/CursoAndroid/getEmpleado.php?id=5
    @GET("getEmpleado.php")
    Call<Empleado> getEmpleadoUnico(@Query("id") int idEmpleado);

    //https://isantosp.com/CursoAndroid/getAll.php
    @GET("getAll.php")
    Call<List<Empleado>> getDatos();
}
