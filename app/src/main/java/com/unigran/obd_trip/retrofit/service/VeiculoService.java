package com.unigran.obd_trip.retrofit.service;

import com.unigran.obd_trip.model.Veiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VeiculoService {

    @GET("veiculo")
    Call<List<Veiculo>> buscaTodos();
}
