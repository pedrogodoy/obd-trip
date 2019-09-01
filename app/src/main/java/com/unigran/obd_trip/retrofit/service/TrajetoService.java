package com.unigran.obd_trip.retrofit.service;

import com.unigran.obd_trip.model.Trajeto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TrajetoService {
    @POST("trajeto/criar")
    Call<Trajeto> enviaTrajeto(@Body Trajeto trajeto);
}
